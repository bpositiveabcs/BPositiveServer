package bpos.server;

import bpos.common.model.Institution;
import bpos.common.model.LogInfo;
import bpos.common.model.Person;
import bpos.common.model.Student;
import bpos.server.service.Implementation.LogInfoService;
import bpos.server.service.Implementation.PersonActorService;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private PersonActorService personActorService;
    @Autowired
    private LogInfoService logInfoService;

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("identityCard") MultipartFile identityCard,
            @RequestParam("username") String username) {

        try {
            // Save the identity card
            File identityCardFile = new File("identityCards/" + username + ".pdf");
            identityCard.transferTo(identityCardFile);

            // Trigger UiPath to read the PDF and extract CNP
            studentService.pregatireCitirePdf(identityCardFile.getAbsolutePath());

            // Wait for UiPath to process and generate the verification code
            String extractedCnp = studentService.readVerificationCodeFromFile();
            if (extractedCnp == null) {
                return ResponseEntity.status(500).body("Error extracting CNP");
            }

            // Generate a unique code
            String code = studentService.generateUniqueCode(extractedCnp);

            // Send verification email via UiPath
            String email = studentService.getEmailByCnp(extractedCnp);
            if (email != null) {
                studentService.triggerUiPathSendEmail(email, code);

                // Temporarily store the code and username
                studentService.storePendingVerification(code, username);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(404).body("Email not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the file");
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        String username = studentService.getUsernameByCode(code);

        if (username != null && studentService.checkCode(code)) {
            // Get student details from payload
            String university = payload.get("university");
            String faculty = payload.get("faculty");
            String domain = payload.get("domain");
            String specialty = payload.get("specialty");
            String year = payload.get("year");
            String group = payload.get("group");
            String semigroup = payload.get("semigroup");

            // Fetch Person and LogInfo
            Person person = null;
            try {
                person = personActorService.findByUsernamePerson(username);
            } catch (ServicesExceptions e) {
                throw new RuntimeException(e);
            }
            LogInfo logInfo = person.getPersonLogInfo();
            Institution institution = null;
            try {
                Iterable<Institution> institutions = personActorService.findByNameInstitution(university);
                institution = institutions.iterator().next();

            } catch (ServicesExceptions e) {
                throw new RuntimeException(e);
            }
            // Create and save student entity
            Student student = new Student(logInfo, person.getPoints(),person.getPersonalDate(),person.getMedicalInfo(),person.getInstitution(),Integer.parseInt(year),group,
                    faculty,specialty,institution);

            studentService.saveStudent(student);

            // Remove the code from pending verifications
            studentService.removePendingVerification(code);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).body("Invalid code");
        }
    }
}
