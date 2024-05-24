package bpos.server;

import bpos.common.model.Donation;
import bpos.common.model.DonationType;
import bpos.common.model.Event;
import bpos.common.model.Person;
import bpos.server.service.Interface.IDonationService;
import bpos.server.service.ServicesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
public class DonationController {
    private IDonationService service;

    public DonationController(IDonationService service) {
        this.service = service;
    }
    @PostMapping("/donație")
    public ResponseEntity<?> donationRegister(@RequestBody Donation donation, @RequestBody Person person, @RequestBody Event event) {
        try {
            service.donationRegister(donation, person, event);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/donations/tip-donatie")
    public ResponseEntity<?> findByTipDonation(@RequestParam(value = "tip_donatie",required = true) String tipDonatie) {
        try {
            Iterable<Donation> donations = service.findByTipDonation(tipDonatie);
            return new ResponseEntity<>(donations, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/donations/id-tip-donatie")
    public ResponseEntity<?> findByIdTipDonation(@RequestParam(value = "id_type_donation",required = true) Integer idTipDonatie) {
        try {
            Iterable<Donation> donations = service.findByIdTipDonation(idTipDonatie);
            return new ResponseEntity<>(donations, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/donations/{id}")
    public ResponseEntity<?> findOneDonation(@PathVariable Integer id) {
        try {
            Optional<Donation> donation = service.findOneDonation(id);
            return donation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/donations")
    public ResponseEntity<?> findAllDonations() {
        try {
            Iterable<Donation> donations = service.findAllDonations();
            return new ResponseEntity<>(donations, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/donation-types/{id}")
    public ResponseEntity<?> findOneDonationType(@PathVariable Integer id) {
        try {
            Optional<DonationType> donationType = service.findOneDonationType(id);
            return donationType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/donation-types")
    public ResponseEntity<?> findAllDonationType() {
        try {
            Iterable<DonationType> donationTypes = service.findAllDonationType();
            return new ResponseEntity<>(donationTypes, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/donations")
    public ResponseEntity<?> updateDonation(@RequestBody Donation entity) {
        try {
            Optional<Donation> updatedDonation = service.updateDonation(entity);
            if (updatedDonation.isPresent()) {
                return ResponseEntity.ok(updatedDonation.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update donation");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/donations")
    public ResponseEntity<?> deleteDonation(@RequestBody Donation entity) {
        try {
            Optional<Donation> deletedDonation = service.deleteDonation(entity);
            if (deletedDonation.isPresent()) {
                return ResponseEntity.ok("Donation deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete donation");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }



    @PostMapping("/donations")
    public ResponseEntity<?> saveDonation(@RequestBody Donation entity) {
        try {
            Optional<Donation> savedDonation = service.saveDonation(entity);
            if (savedDonation.isPresent()) {
                return ResponseEntity.ok("Donation saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save donation");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @PutMapping("/donation-types")
    public ResponseEntity<?> updateDonationType(@RequestBody DonationType entity) {
        try {
            Optional<DonationType> updatedDonationType = service.updateDonationType(entity);
            if (updatedDonationType.isPresent()) {
                return ResponseEntity.ok(updatedDonationType.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/donation-types")
    public ResponseEntity<?> deleteDonationType(@RequestBody DonationType entity) {
        try {
            Optional<DonationType> deletedDonationType = service.deleteDonationType(entity);
            if (deletedDonationType.isPresent()) {
                return ResponseEntity.ok(deletedDonationType.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping("/donation-types")
    public ResponseEntity<?> saveDonationType(@RequestBody DonationType entity) {
        try {
            Optional<DonationType> savedDonationType = service.saveDonationType(entity);
            if (savedDonationType.isPresent()) {
                return ResponseEntity.ok(savedDonationType.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save donation type");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
