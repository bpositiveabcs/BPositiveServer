package bpos.server;

import bpos.common.model.Address;
//import bpos.server.controller.Controller;
import bpos.server.service.Interface.IAddressService;
import bpos.server.service.ServicesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/addresses")
public class AddressController {
    private IAddressService service;
    public AddressController(IAddressService addressService) {
        this.service = addressService;
    }
    @RequestMapping("/{id}")
    public ResponseEntity<?> findOneAddress(@PathVariable Integer integer) {
        try {
            Optional<Address> address = service.findOneAddress(integer);
            return address.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllAddresses() {
        try {
            Iterable<Address> addresses = service.findAllAddresses();
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAddress(@RequestBody Address entity) {
        try {
            Optional<Address> updatedAddress = service.updateAddress(entity);
            if (updatedAddress.isPresent()) {
                return ResponseEntity.ok("Address updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Address could not be updated");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }



    @DeleteMapping
    public ResponseEntity<?> deleteAddress(@RequestParam Address id) {
        try {
            Optional<Address> deletedAddress = service.deleteAddress(id);
            if (deletedAddress.isPresent()) {
                return ResponseEntity.ok("Address deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping
    public ResponseEntity<?> saveAddress(@RequestBody Address entity) {
        try {
            Optional<Address> savedAddress = service.saveAddress(entity);
            if (savedAddress.isPresent()) {
                return ResponseEntity.ok("Address saved successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save address");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

}
