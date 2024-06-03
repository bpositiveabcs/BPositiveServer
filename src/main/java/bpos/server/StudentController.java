package bpos.server;

import bpos.common.model.Institution;
import bpos.common.model.LogInfo;
import bpos.common.model.Person;
import bpos.common.model.Student;
import bpos.server.service.Implementation.LogInfoService;
import bpos.server.service.Implementation.PersonActorService;
import bpos.server.service.Implementation.StudentService;
import bpos.server.service.ServicesExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

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
            // Ensure the identityCards directory exists
            Path directoryPath = Paths.get("C:\\Users\\hp\\Documents\\UiPath\\ReadText");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Save the identity card
            Path identityCardPath = directoryPath.resolve(username + ".pdf");
            Files.copy(identityCard.getInputStream(), identityCardPath, StandardCopyOption.REPLACE_EXISTING);

            // Trigger UiPath to read the PDF and extract CNP
            System.out.println("am ajuns la etapa de pregatire a pdf-ului");
            studentService.pregatireCitirePdf(identityCardPath.toString());

//            wait(120000);
//
//            // Wait for UiPath to process and generate the verification code
//            String extractedCnp = studentService.readVerificationCodeFromFile();
//            if (extractedCnp == null) {
//                return ResponseEntity.status(500).body("Error extracting CNP");
//            }
//
//            // Generate a unique code
//            String code = studentService.generateUniqueCode(extractedCnp);

            return ResponseEntity.ok().build();

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
            Person person;
            try {
                person = personActorService.findByUsernamePerson(username);
            } catch (ServicesExceptions e) {
                throw new RuntimeException(e);
            }
            LogInfo logInfo = person.getPersonLogInfo();
            Institution institution;
            try {
                Iterable<Institution> institutions = personActorService.findByNameInstitution(university);
                institution = institutions.iterator().next();
            } catch (ServicesExceptions e) {
                throw new RuntimeException(e);
            }

            // Create and save student entity
            Student student = new Student(logInfo, person.getPoints(), person.getPersonalDate(), person.getMedicalInfo(),
                    person.getInstitution(), Integer.parseInt(year), group, faculty, specialty, institution);

            studentService.saveStudent(student);

            // Remove the code from pending verifications
            studentService.removePendingVerification(code);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).body("Invalid code");
        }
    }

    @PostMapping("/upload-medical-info/{username}")
    public ResponseEntity<?> uploadMedicalInfo(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            // Create the main directory if it doesn't exist
            Path mainDir = Paths.get("user_medicalinfo");
            if (!Files.exists(mainDir)) {
                Files.createDirectory(mainDir);
            }

            // Create the user-specific directory if it doesn't exist
            Path userDir = mainDir.resolve(username);
            if (!Files.exists(userDir)) {
                Files.createDirectory(userDir);
            }

            // Copy the file to the user-specific directory
            Path targetLocation = userDir.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok().body("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
}
