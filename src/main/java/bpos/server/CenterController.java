package bpos.server;

import bpos.common.model.Center;
import bpos.server.service.Implementation.CenterActorService;
import bpos.server.service.Implementation.PersonActorService;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.exceptions.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
@RestController
@RequestMapping("/centers")
public class CenterController {
    private CenterActorService service;
    private PersonActorService personService;

    public CenterController(CenterActorService service,PersonActorService personService) {
        this.service = service;
        this.personService=personService;

    }
    @GetMapping("/username")
    public ResponseEntity<?> findByUsernameCenter(@RequestParam(value="username",required = true) String username) {
        try {
            Center center = service.findByUsernameCenter(username);
            return new ResponseEntity<>(center, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> findByEmailCenter(@RequestParam(value="email",required = true) String email) {
        try {
            Center center = service.findByEmailCenter(email);
            return new ResponseEntity<>(center, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> findByNameCenter(@RequestParam(value="name",required = true) String name) {
        try {
            Iterable<Center> centers = service.findByNameCenter(name);
            return new ResponseEntity<>(centers, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOneCenter(@PathVariable Integer id) {
        try {
            Optional<Center> center = service.findOneCenter(id);
            return center.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllCenters() {
        try {
            Iterable<Center> centers = service.findAllCenters();
            return new ResponseEntity<>(centers, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCenter(@RequestBody Center entity) {
        try {
            Optional<Center> updatedCenter = service.updateCenter(entity);
            if (updatedCenter.isPresent()) {
                return ResponseEntity.ok(updatedCenter.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update center");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteCenter(@RequestParam Center entity) {
        try {
            Optional<Center> deletedCenter = service.deleteCenter(entity);
            if (deletedCenter.isPresent()) {
                return ResponseEntity.ok("Center deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Center not found");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
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
            //acum sa adaug in repo
            personService.addBloodTestToMedicalInfo(username,targetLocation.toString(),file.getOriginalFilename());



            return ResponseEntity.ok().body("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        } catch (ServicesExceptions e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping
    public ResponseEntity<?> saveCenter(@RequestBody Center entity) {
        try {
            Optional<Center> savedCenter = service.saveCenter(entity);
            if (savedCenter.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedCenter.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Center could not be saved");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutCenter(@RequestParam String username) {
        try {
            Center center=service.findByUsernameCenter(username);
            service.logoutCenter(center);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotLoggedInException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginCenter(@RequestParam(value="username") String username, @RequestParam (value="password")String password) {
        try {
            Center center=service.findByUsernameCenter(username);
            Optional<Center> loggedCenter = service.loginCenter(center.getLogInfo());
            if (loggedCenter.isPresent()) {
                return ResponseEntity.ok(loggedCenter.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
