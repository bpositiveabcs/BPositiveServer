package bpos.server;

import bpos.common.model.*;
import bpos.server.service.Implementation.LogInfoService;
import bpos.server.service.Implementation.PersonActorService;
import bpos.server.service.Implementation.StudentService;
import bpos.server.service.ServicesExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
            @RequestParam("username") String username,
            @RequestParam("university") String university,
            @RequestParam("faculty") String faculty,
            @RequestParam("domain") String domain,
            @RequestParam("specialization") String specialization,
            @RequestParam("year") String year,
            @RequestParam("group") String group,
            @RequestParam("semigroup") String semigroup) {

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
            studentService.pregatireCitirePdf(identityCardPath.toString());

//            wait(10000);
//
//            String fileName = "C:\\Users\\hp\\Documents\\UiPath\\ReadText\\VerificareStudent\\rezultat.txt";
//
//            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//                String firstLine = br.readLine();
//
//                if (firstLine.equals("Email not found"))
//                {
//                    return ResponseEntity.status(500).body("Not a student");
//                }
//                else {
//                    // Fetch Person and LogInfo
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
                            person.getInstitution(), Integer.parseInt(year), group, faculty, specialization, institution);
                    student.setId(person.getId());
                    studentService.saveStudent(student);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the file");
        }
        return ResponseEntity.ok().build();
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

            // Save the medical info to the repository
            // Assume that you have a method to get the person's medical info by username
            Person person = personActorService.findByUsernamePerson(username);
            BloodTest bloodTest = new BloodTest();
            bloodTest.setName(file.getOriginalFilename());
            bloodTest.setPath(targetLocation.toString());
            bloodTest.setMedicalInfo(person.getMedicalInfo().getId());
            person.getMedicalInfo().getMedicalHistory().add(bloodTest);
            personActorService.savePerson(person);

            return ResponseEntity.ok().body("File uploaded and saved successfully!");
        } catch (IOException | ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and save file: " + e.getMessage());
        }
    }
}
