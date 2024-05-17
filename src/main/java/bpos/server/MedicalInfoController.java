package bpos.server;

import bpos.common.model.BloodTest;
import bpos.common.model.MedicalInfo;
import bpos.server.service.Interface.IMedicalInformationService;
import bpos.server.service.ServicesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
public class MedicalInfoController {
    private final IMedicalInformationService service;

    public MedicalInfoController(IMedicalInformationService service) {
        this.service = service;
    }

    @GetMapping("/blood-tests/{id}")
    public Optional<BloodTest> findOneBloodTest(@PathVariable Integer id) throws ServicesExceptions {
        return service.findOneBloodTest(id);
    }
    //asta nu merge
    @GetMapping("/blood-tests")
    public Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions {
        return service.findAllBloodTest();
    }
    @GetMapping("/medical-infos/{id}")
    public Optional<MedicalInfo> findOneMedicalInfo(@PathVariable Integer id) throws ServicesExceptions {
        return service.findOneMedicalInfo(id);
    }
    //merge
    @GetMapping("/medical-infos")
    public Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions {
        return service.findAllMedicalInfos();
    }
    //merge
    @GetMapping("/medical-infos/blood-type")
    public Iterable<MedicalInfo> findByBloodTypeMedicalInfo(@RequestParam(value="blood_type",required = true)String bloodType) throws ServicesExceptions {
        return service.findByBloodTypeMedicalInfo(bloodType);
    }
    //merge
    @GetMapping("/medical-infos/rh")
    public Iterable<MedicalInfo> findByRhMedicalInfo(@RequestParam(value="rh",required = true)String rh) throws ServicesExceptions {
        return service.findByRhMedicalInfo(rh);
    }

    @GetMapping("/medical-infos/blood-type-rh")
    public Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(@RequestParam(value="bloodType",required = true)String bloodType,@RequestParam(value="rh",required = true) String rh) throws ServicesExceptions {
        return service.findByBloodTypeAndRhMedicalInfo(bloodType, rh);
    }
    @PutMapping("/medical-infos")
    public ResponseEntity<?> updateMedicalInfo(@RequestBody MedicalInfo entity) {
        try {
            Optional<MedicalInfo> updatedEntity = service.updateMedicalInfo(entity);
            return new ResponseEntity<>(updatedEntity.orElse(null), HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medical-infos")
    public ResponseEntity<?> deleteMedicalInfo(@RequestBody MedicalInfo entity) {
        try {
            service.deleteMedicalInfo(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/blood-tests")
    public ResponseEntity<?> updateBloodTest(@RequestBody BloodTest entity) {
        try {
            Optional<BloodTest> updatedBloodTest = service.updateBloodTest(entity);
            if (updatedBloodTest.isPresent()) {
                return ResponseEntity.ok(updatedBloodTest.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Blood test could not be updated");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @DeleteMapping("/blood-tests")
    public ResponseEntity<?> deleteBloodTest(@RequestBody BloodTest entity) {
        try {
            Optional<BloodTest> deletedBloodTest = service.deleteBloodTest(entity);
            if (deletedBloodTest.isPresent()) {
                return ResponseEntity.ok("Blood test deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Blood test could not be deleted");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping("/blood-tests")
    public ResponseEntity<?> saveBloodTest(@RequestBody BloodTest entity) {
        try {
            Optional<BloodTest> savedBloodTest = service.saveBloodTest(entity);
            if (savedBloodTest.isPresent()) {
                return ResponseEntity.ok("Blood test saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Blood test could not be saved");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }



    @PostMapping("/medical-infos")
    public ResponseEntity<?> saveMedicalInfo(@RequestBody MedicalInfo entity) {
        try {
            Optional<MedicalInfo> savedInfo = service.saveMedicalInfo(entity);
            if (savedInfo.isPresent()) {
                return new ResponseEntity<>(savedInfo.get(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
