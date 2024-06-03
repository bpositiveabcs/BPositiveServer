package bpos.server;

import bpos.common.model.Coupon;
import bpos.common.model.Event;
import bpos.common.model.Person;
import bpos.common.model.RetrievedCoupons;
import bpos.other.NotificationRest;
import bpos.server.service.Implementation.EventDTO;
import bpos.server.service.Interface.IEventService;
import bpos.server.service.Interface.IPersonActorInterface;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.WebSockets.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController

public class EventController {
    private NotificationService notifyService;

    public EventController(IEventService service, IPersonActorInterface servicePerson, NotificationService notifyService) {
        this.service = service;
        this.servicePerson= servicePerson;
        this.notifyService=notifyService;
    }
    @PostMapping("/events/join-event")
    public ResponseEntity<?> joinEvent(@RequestParam(value="username") String username, @RequestBody Event event) {
        try {
            Person findPerson = servicePerson.findByUsernamePerson(username);
            if (findPerson != null) {
                Person person=service.joinEvent(findPerson, event);
                if(person==null){
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "evenimentul a fost inchis, prea multi participanti");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                // Correctly get the event list and update it
//                List<Event> eventList = findPerson.getEvents();
//                eventList.add(event);
//
//                findPerson.setEvents(eventList);
//
//                Optional<Person> updatePerson = servicePerson.updatePerson(findPerson);

                return new ResponseEntity<>(person, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/retrieved-coupons")
    public ResponseEntity<?> updateRetrieved(@RequestBody RetrievedCoupons entity) {
        try {
            Optional<RetrievedCoupons> updatedEntity = service.updateRetrieved(entity);
            if (updatedEntity.isPresent()) {
                return new ResponseEntity<>(updatedEntity.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/retrieved-coupons")
    public ResponseEntity<?> deleteRetrieved(@RequestBody RetrievedCoupons entity) {
        try {
            Optional<RetrievedCoupons> deletedEntity = service.deleteRetrieved(entity);
            if (deletedEntity.isPresent()) {
                return new ResponseEntity<>(deletedEntity.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/retrieved-coupons/list-coupons")
    public ResponseEntity<?> listCouponsSelected(@RequestBody PersonCouponRequest personCouponRequest){

        try {
            ArrayList<Coupon> coupons=new ArrayList<>();
            Person personOptional=servicePerson.findByUsernamePerson(personCouponRequest.getUsername());
            int points=0;
            for(Coupon coupon:personCouponRequest.getCouponList()){
                Optional<Coupon>couponFind=service.findOneCoupon(coupon.getId());
                if(couponFind.isPresent()){
                    coupon=couponFind.get();
                }

                if(personOptional!=null){
                    RetrievedCoupons retrievedCoupons=new RetrievedCoupons();
                    retrievedCoupons.setId_persoana(personOptional.getId());
                    retrievedCoupons.setCoupon(coupon);
                    retrievedCoupons.setSeries(coupon.getSeries());
                    retrievedCoupons.setExpirationDate(coupon.getUnavailableToClaimFrom());
                    retrievedCoupons.setReceivedDate(LocalDateTime.now());
                    points+=coupon.getNecessaryPoints();
                    Optional<RetrievedCoupons> savedEntity = service.saveRetrieved(retrievedCoupons);
                    coupons.add(coupon);
                }
            }
            //facem update la puncte la persoana
            personOptional.setPoints(personOptional.getPoints()-points);
            servicePerson.updatePerson(personOptional);
            assert personOptional != null;
            PersonCouponResponse personCouponResponse=new PersonCouponResponse(coupons,personOptional.getPersonLogInfo().getEmail());

            ///////////////////////////////////////

            ArrayList<String> nume_cupoane = new ArrayList<>();
            for (Coupon c: coupons)
                nume_cupoane.add(c.getName()+".png");

            pregatireTrimitereCupoane(personCouponResponse.getEmail(), nume_cupoane);

            ////////////////////////////////////////

            return new ResponseEntity<>(personCouponResponse, HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////

    public static void pregatireTrimitereCupoane(String email, ArrayList<String> lista_cupoane) {
        String filePath = "C:\\Users\\hp\\Documents\\UiPath\\EmailCupoane\\data.xlsx";

        // Create a new workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create the first row and set the email
        Row firstRow = sheet.createRow(0);
        Cell emailCell = firstRow.createCell(0);
        emailCell.setCellValue(email);

        // Add coupon data to subsequent rows
        for (int i = 0; i < lista_cupoane.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(lista_cupoane.get(i));
        }

        // Write the new workbook to the file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            apelTrimitereCupoane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void apelTrimitereCupoane() {
        System.setProperty("java.awt.headless", "false");
        String uipathProjectPath = "C:\\Users\\hp\\Documents\\UiPath\\EmailCupoane\\Main.xaml";

        try {
            Runtime.getRuntime().exec(new String[]{"C:\\Users\\hp\\AppData\\Local\\Programs\\UiPath\\Studio\\UiPath.Studio.exe", uipathProjectPath});
            Thread.sleep(15000);

            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            Thread.sleep(5000);
            Thread.sleep(20000);

            int x = 1920;
            int y = 0;
            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (IOException | AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////

    @PostMapping("/retrieved-coupons")
    public ResponseEntity<?> saveRetrieved(@RequestBody RetrievedCoupons entity) {
        try {
            Optional<RetrievedCoupons> savedEntity = service.saveRetrieved(entity);
            return new ResponseEntity<>(savedEntity.orElse(null), HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private IPersonActorInterface servicePerson;
    private IEventService service;
    //nush cum sa dau URI inca
    @GetMapping("/coupons/code-coupon")
    public ResponseEntity<?> findByCodeCoupon(@RequestParam(value="code_coupon",required = true) String code) {
        try {
            Iterable<Coupon> coupons = service.findByCodeCoupon(code);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/coupons/provider-coupon")
    public ResponseEntity<?> findByProviderCoupon(@RequestParam(value="provider",required = true) String provider) {
        try {
            Iterable<Coupon> coupons = service.findByProviderCoupon(provider);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/coupons/name-coupon")
    public ResponseEntity<?> findByNameCoupon(@RequestParam(value = "name",required = true) String name) {
        try {
            Iterable<Coupon> coupons = service.findByNameCoupon(name);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //asta merge nush daca am dat eu val buna
    @GetMapping("/coupons/endDate")
    public ResponseEntity<?> findByEndDateCoupon(@RequestParam(value="endDate",required = true) LocalDate endDate) {
        try {
            Iterable<Coupon> coupons = service.findByEndDateCoupon(endDate);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/coupons/{idCoupon}")
    public ResponseEntity<?> findOneCoupon(@PathVariable Integer idCoupon) {
        try {
            Optional<Coupon> coupon = service.findOneCoupon(idCoupon);
            return coupon.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/coupons")
    public ResponseEntity<?> findAllCoupons() {
        try {
            Iterable<Coupon> coupons = service.findAllCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/events/name_event")
    public ResponseEntity<?> findByNameEvent(@RequestParam(value = "name_event", required = true) String nume) {
        try {
            Iterable<Event> events = service.findByNameEvent(nume);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/events/announcement-date-event")
    public ResponseEntity<?> findByAnnouncementDateEvent(@RequestParam(value = "announcement_date_event", required = true) LocalDate data) {
        try {
            Iterable<Event> events = service.findByAnnouncementDateEvent(data);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/events/center-id-event")
    public ResponseEntity<?> findByCenterIdEvent(@RequestParam(value="centruID",required = true) Integer centruId) {
        try {
            Iterable<Event> events = service.findByCenterIdEvent(centruId);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //cred ca nush sa dau eu stringul bun pentru data
    //requestul merge
    @GetMapping("/events/data_inceput_event")
    public ResponseEntity<?> findByDataInceputEvent(@RequestParam(value = "date_start",required = true) LocalDate data) {
        try {
            Iterable<Event> events = service.findByDataInceputEvent(data);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/events/{id}")
    public ResponseEntity<?> findOneEvent(@PathVariable Integer id) {
        try {
            Optional<Event> event = service.findOneEvent(id);
            return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/events-dto")
    public ResponseEntity<?> findAllEventsDTO() {
        try {
            List<EventDTO> events = service.findAllEventsDTO();
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/events")
    public ResponseEntity<?> findAllEvents() {
        try {
            Iterable<Event> events = service.findAllEvents();
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieved-coupons/{id}")
    public ResponseEntity<?> findOneRetrieved(@PathVariable Integer id) {
        try {
            Optional<RetrievedCoupons> retrievedCoupon = service.findOneRetrieved(id);
            return retrievedCoupon.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //merge
    @GetMapping("/retrieved-coupons")
    public ResponseEntity<?> findAllRetrieved() {
        try {
            Iterable<RetrievedCoupons> retrievedCoupons = service.findAllRetrieved();
            return new ResponseEntity<>(retrievedCoupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieved-coupons/couponId")
    public ResponseEntity<?> findByCouponIdRetrieved(@RequestParam(value="couponId",required = true) Integer couponId) {
        try {
            Iterable<RetrievedCoupons> retrievedCoupons = service.findByCouponIdRetrieved(couponId);
            return new ResponseEntity<>(retrievedCoupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieved-coupons/personId")
    public ResponseEntity<?> findByPersonIdRetrieved(@RequestParam(value="personId",required = true) Integer personId) {
        try {
            Iterable<RetrievedCoupons> retrievedCoupons = service.findByPersonIdRetrieved(personId);
            return new ResponseEntity<>(retrievedCoupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/retrieved-coupons/dateRetrieved")
    public ResponseEntity<?> findByDateRetrieved(@RequestParam(value="date",required = true) String date) {
        try {
            Iterable<RetrievedCoupons> retrievedCoupons = service.findByDateRetrieved(date);
            return new ResponseEntity<>(retrievedCoupons, HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/events")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        try {
            System.out.println(event.getEventDescription());
            System.out.println(event.getEventRequirements());
            Optional<Event> updatedEvent = service.updateEvent(event);
            if(updatedEvent.isPresent()) {
                return new ResponseEntity<>(updatedEvent.get(), HttpStatus.OK);
            }
        } catch (ServicesExceptions e) {
            // Tratarea cazurilor în care actualizarea a eșuat din diverse motive
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/events")
    public ResponseEntity<Event> deleteEvent(@RequestBody Event event) {
        try {
            Optional<Event> deletedEvent=service.deleteEvent(event);
            return new ResponseEntity<>(deletedEvent.get(),HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/events")
    public ResponseEntity<?> saveEvent(@RequestBody Event entity) {
        try {
            Optional<Event> savedEvent = service.saveEvent(entity);
            if(savedEvent.isPresent()) {
                notifyService.notifyCenter(NotificationRest.NEW_EVENT+":"+entity.getEventName()+" a fost adaugat");
                return new ResponseEntity<>(savedEvent.get(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>("Eroare la procesarea cererii: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/coupons")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon entity) {
        try {
            Optional<Coupon> updatedCoupon = service.updateCoupon(entity);
            if (updatedCoupon.isPresent()) {

                return ResponseEntity.ok("Coupon updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update coupon");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @DeleteMapping("/coupons")
    public ResponseEntity<?> deleteCoupon(@RequestBody Coupon entity) {
        try {
            Optional<Coupon> deletedCoupon = service.deleteCoupon(entity);
            if (deletedCoupon.isPresent()) {
                return ResponseEntity.ok("Coupon deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete coupon");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }



    @PostMapping("/coupons")
    public ResponseEntity<?> saveCoupon(@RequestBody Coupon entity) {
        try {
            Optional<Coupon> savedCoupon = service.saveCoupon(entity);
            if (savedCoupon.isPresent()) {
                return ResponseEntity.ok(savedCoupon.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save coupon");
            }
        } catch (ServicesExceptions e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


}
