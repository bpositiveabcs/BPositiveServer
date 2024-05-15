package bpos.server;

import bpos.common.model.*;
import bpos.server.service.IObserver;
import bpos.server.service.IServiceImpl;
import bpos.server.service.ServicesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RequestMapping("/api/test")
@CrossOrigin
@RestController
public class TestController {
    private  IServiceImpl service;
    public TestController(IServiceImpl service) {
        this.service = service;
    }

    public Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions {
        return service.findOneAddress(integer);
    }

    @GetMapping("/getAllAddresses")
    public Iterable<Address> findAllAddresses() throws ServicesExceptions {
        return service.findAllAddresses();
    }
    //merge
    @GetMapping("/getAllPersons")
    public Iterable<Person> findAllPersons() throws ServicesExceptions {
        System.out.println("getAllPersons");
        return service.findAllPersons();
    }
    @GetMapping("/findOneBloodTest")
    public Optional<BloodTest> findOneBloodTest(@RequestParam(value="id_blood_test",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneBloodTest(integer);
    }
    //asta nu merge
    @GetMapping("/findAllBloodTest")
    public Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions {
        return service.findAllBloodTest();
    }

    //asta merge
    @GetMapping("/findByUsernameCenter")
    public Center findByUsernameCenter(@RequestParam(value="username",required = true) String username) throws ServicesExceptions {
        return service.findByUsernameCenter(username);
    }
    //asta merge
    @GetMapping("/findByEmailCenter")
    public Center findByEmailCenter(@RequestParam(value="email",required = true) String email) throws ServicesExceptions {
        return service.findByEmailCenter(email);
    }
//merge
    @GetMapping("/findByNameCenter")
    public Iterable<Center> findByNameCenter(@RequestParam(value="name",required = true) String name) throws ServicesExceptions {
        return service.findByNameCenter(name);
    }
//merge
    @GetMapping("/findOneCenter")
    public Optional<Center> findOneCenter(@RequestParam(value="id_center",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneCenter(integer);
    }
    //merge
    @GetMapping("/findAllCenters")
    public Iterable<Center> findAllCenters() throws ServicesExceptions {
        return service.findAllCenters();
    }
    //merge
    @GetMapping("/findByCodeCoupon")
    public Iterable<Coupon> findByCodeCoupon(@RequestParam(value="code_coupon",required = true) String code) throws ServicesExceptions {
        return service.findByCodeCoupon(code);
    }
    //merge
    @GetMapping("/findByProviderCoupon")
    public Iterable<Coupon> findByProviderCoupon(@RequestParam(value="provider",required = true) String provider) throws ServicesExceptions {
        return service.findByProviderCoupon(provider);
    }
    //merge
    @GetMapping("/findByNameCoupon")
    public Iterable<Coupon> findByNameCoupon(@RequestParam(value = "name",required = true) String name) throws ServicesExceptions {
        return service.findByNameCoupon(name);
    }
    //asta merge nush daca am dat eu val buna
    @GetMapping("/findByEndDateCoupon")
    public Iterable<Coupon> findByEndDateCoupon(@RequestParam(value="endDate",required = true) LocalDate endDate) throws ServicesExceptions {
        return service.findByEndDateCoupon(endDate);
    }
    //merge
    @GetMapping("/findOneCoupon")
    public Optional<Coupon> findOneCoupon(@RequestParam(value="id_coupon",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneCoupon(integer);
    }
    //merge
    @GetMapping("/findAllCoupons")
    public Iterable<Coupon> findAllCoupons() throws ServicesExceptions {
        return service.findAllCoupons();
    }

    //merge
    @GetMapping("/findByTipDonation")
    public Iterable<Donation> findByTipDonation(@RequestParam(value = "tip_donatie",required = true) String tipDonatie) throws ServicesExceptions {
        return service.findByTipDonation(tipDonatie);
    }
    //merge
    @GetMapping("/findByIdTipDonation")
    public Iterable<Donation> findByIdTipDonation(@RequestParam(value = "id_type_donation",required = true) Integer idTipDonatie) throws ServicesExceptions {
        return service.findByIdTipDonation(idTipDonatie);
    }
    //merge
    @GetMapping("/findOneDonation")
    public Optional<Donation> findOneDonation(@RequestParam(value="id_donation",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneDonation(integer);
    }
    //merge
    @GetMapping("/findAllDonations")
    public Iterable<Donation> findAllDonations() throws ServicesExceptions {
        return service.findAllDonations();
    }
    //merge
    @GetMapping("/findOneDonationType")
    public Optional<DonationType> findOneDonationType(@RequestParam(value = "id_donation_type",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneDonationType(integer);
    }
    //merge
    @GetMapping("/findAllDonationType")
    public Iterable<DonationType> findAllDonationType() throws ServicesExceptions {
        return service.findAllDonationType();
    }
    //merge

    @GetMapping("/findByNameEvent")
    public Iterable<Event> findByNameEvent(@RequestParam(value = "name_event",required = true) String nume) throws ServicesExceptions {
        return service.findByNameEvent(nume);
    }
    //merge
    @GetMapping("/findByAnnouncementDateEvent")
    public Iterable<Event> findByAnnouncementDateEvent(@RequestParam(value = "announcement_date_event",required = true) LocalDate data) throws ServicesExceptions {
        return service.findByAnnouncementDateEvent(data);
    }
    //merge
    @GetMapping("/findByCenterIdEvent")
    public Iterable<Event> findByCenterIdEvent(@RequestParam(value="centruID",required = true) Integer centruId) throws ServicesExceptions {
        return service.findByCenterIdEvent(centruId);
    }
    //cred ca nush sa dau eu stringul bun pentru data
    //requestul merge
    @GetMapping("/findByDataInceputEvent")
    public Iterable<Event> findByDataInceputEvent(@RequestParam(value = "date_start",required = true) LocalDate data) throws ServicesExceptions {
        return service.findByDataInceputEvent(data);
    }
    //merge
    @GetMapping("/findOneEvent")
    public Optional<Event> findOneEvent(@RequestParam(value="id_event",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneEvent(integer);
    }
    //merge
    @GetMapping("/findAllEvents")
    public Iterable<Event> findAllEvents() throws ServicesExceptions {
        return service.findAllEvents();
    }

    //merge
    @GetMapping("/findOneInstitution")
    public Optional<Institution> findOneInstitution(@RequestParam(value="id_institution",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneInstitution(integer);
    }
    //merge
    @GetMapping("/findAllInstitutions")
    public Iterable<Institution> findAllInstitutions() throws ServicesExceptions {
        return service.findAllInstitutions();
    }
    //merge
    @GetMapping("/findByNameInstitution")
    public Iterable<Institution> findByNameInstitution(@RequestParam(value="name_institution",required = true)String name) throws ServicesExceptions {
        return service.findByNameInstitution(name);
    }
    //merge
    @GetMapping("/findByAddressInstitution")
    public Iterable<Institution> findByAddressInstitution(@RequestParam(value="address_institution",required = true)String address) throws ServicesExceptions {
        return service.findByAddressInstitution(address);
    }
    //merge
    @GetMapping("/findByEmailInstitution")
    public Iterable<Institution> findByEmailInstitution(@RequestParam(value="email",required = true) String email) throws ServicesExceptions {
        return service.findByEmailInstitution(email);
    }


    //merge
    @GetMapping("/findOneLogInfo")
    public Optional<LogInfo> findOneLogInfo(@RequestParam(value="id_log_info",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneLogInfo(integer);
    }
    //merge
    @GetMapping("/findAllLogInfos")
    public Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions {
        return service.findAllLogInfos();
    }
    //merge
    @GetMapping("/findByUsernameLogInfo")
    public LogInfo findByUsernameLogInfo(@RequestParam(value="username",required = true)String username) throws ServicesExceptions {
        return service.findByUsernameLogInfo(username);
    }
    //merge
    @GetMapping("/findByEmailLogInfo")
    public LogInfo findByEmailLogInfo(@RequestParam(value="email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailLogInfo(email);
    }


    //merge
    @GetMapping("/findOneMedicalInfo")
    public Optional<MedicalInfo> findOneMedicalInfo(@RequestParam(value="id_medical_info",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneMedicalInfo(integer);
    }
    //merge
    @GetMapping("/findAllMedicalInfos")
    public Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions {
        return service.findAllMedicalInfos();
    }
    //merge
    @GetMapping("/findByBloodTypeMedicalInfo")
    public Iterable<MedicalInfo> findByBloodTypeMedicalInfo(@RequestParam(value="blood_type",required = true)String bloodType) throws ServicesExceptions {
        return service.findByBloodTypeMedicalInfo(bloodType);
    }
    //merge
    @GetMapping("/findByRhMedicalInfo")
    public Iterable<MedicalInfo> findByRhMedicalInfo(@RequestParam(value="rh",required = true)String rh) throws ServicesExceptions {
        return service.findByRhMedicalInfo(rh);
    }

    @GetMapping("/findByBloodTypeAndRhMedicalInfo")
    public Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(@RequestParam(value="bloodType",required = true)String bloodType,@RequestParam(value="rh",required = true) String rh) throws ServicesExceptions {
        return service.findByBloodTypeAndRhMedicalInfo(bloodType, rh);
    }
    //merge
    @GetMapping("/findOnePersonalData")
    public Optional<PersonalData> findOnePersonalData(@RequestParam(value="id_personal_data",required = true)Integer integer) throws ServicesExceptions {
        return service.findOnePersonalData(integer);
    }
    //merge
    @GetMapping("/findAllPersonalDatas")
    public Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions {
        return service.findAllPersonalDatas();
    }
    // e nula functia din repo
    @GetMapping("/findByFirstNamePersonalData")
    public Iterable<PersonalData> findByFirstNamePersonalData(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNamePersonalData(firstName);
    }
    // e nula functia din repo
    @GetMapping("/findByLastNamePersonalData")
    public Iterable<PersonalData> findByLastNamePersonalData(@RequestParam(value="id_personal_data",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNamePersonalData(lastName);
    }
    // TO DO - nula functia din repo
    @GetMapping("/findByCnpPersonalData")
    public PersonalData findByCnpPersonalData(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpPersonalData(cnp);
    }
    //e nula functia din repo
    @GetMapping("/findByEmailPersonalData")
    public Optional<Person> findOnePerson(@RequestParam(value="id_person",required = true)Integer integer) throws ServicesExceptions {
        return service.findOnePerson(integer);
    }



    @GetMapping("/findByFirstNamePerson")
    public Iterable<Person> findByFirstNamePerson(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNamePerson(firstName);
    }

    @GetMapping("/findByLastNamePerson")
    public Iterable<Person> findByLastNamePerson(@RequestParam(value="lastName",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNamePerson(lastName);
    }
    //merge
    @GetMapping("/findByCnpPerson")
    public Iterable<Person> findByCnpPerson(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpPerson(cnp);
    }
    //asta nu merge da eroare in db
    @GetMapping("/findByEmailPerson")
    public Person findByEmailPerson(@RequestParam(value="id_email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailPerson(email);
    }
    //merge
    @GetMapping("/findByPhoneNumberPerson")
    public Iterable<Person> findByPhoneNumberPerson(@RequestParam(value="phoneNumber",required = true)String phoneNumber) throws ServicesExceptions {
        return service.findByPhoneNumberPerson(phoneNumber);
    }
    //merge
    @GetMapping("/findByUsernamePerson")
    public Person findByUsernamePerson(@RequestParam(value="usernamePerson",required = true)String username) throws ServicesExceptions {
        return service.findByUsernamePerson(username);
    }


    @GetMapping("/findOneRetrieved")
    public Optional<RetrievedCoupons> findOneRetrieved(@RequestParam(value="id_retrieved",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneRetrieved(integer);
    }
    //merge
    @GetMapping("/findAllRetrieved")
    public Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions {
        return service.findAllRetrieved();
    }
    //merge

    @GetMapping("/findByCouponIdRetrieved")
    public Iterable<RetrievedCoupons> findByCouponIdRetrieved(@RequestParam(value="couponId",required = true)Integer couponId) throws ServicesExceptions {
        return service.findByCouponIdRetrieved(couponId);
    }
    //nu merge
    @GetMapping("/findByPersonIdRetrieved")
    public Iterable<RetrievedCoupons> findByPersonIdRetrieved(@RequestParam(value="personId",required = true)Integer personId) throws ServicesExceptions {
        return service.findByPersonIdRetrieved(personId);
    }

    @GetMapping("/findByDateRetrieved")
    public Iterable<RetrievedCoupons> findByDateRetrieved(@RequestParam(value="date",required = true)String date) throws ServicesExceptions {
        return service.findByDateRetrieved(date);
    }

    @GetMapping("/findOneStudent")
    public Optional<Student> findOneStudent(@RequestParam(value="id_student",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneStudent(integer);
    }
    //nu merge
    @GetMapping("/findAllStudent")
    public Iterable<Student> findAllStudent() throws ServicesExceptions {
        return service.findAllStudent();
    }

//    @GetMapping("/saveStudent")
@PostMapping("/students")
public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
    try {
        Optional<Student> savedStudent = service.saveStudent(student);
        return new ResponseEntity<>(savedStudent.get(), HttpStatus.CREATED);
    } catch (ServicesExceptions e) {
        // Handle exception
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//    @GetMapping("/deleteStudent")
    public Optional<Student> deleteStudent(Student entity) throws ServicesExceptions {
        return service.deleteStudent(entity);
    }

//    @GetMapping("/updateStudent")
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
    try {
        Optional<Student> updatedStudent = service.updateStudent(student);
        return updatedStudent.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } catch (ServicesExceptions e) {
        // Handle exception
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @GetMapping("/findByFirstNameStudent")
    public Iterable<Student> findByFirstNameStudent(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNameStudent(firstName);
    }

    @GetMapping("/findByLastNameStudent")
    public Iterable<Student> findByLastNameStudent(@RequestParam(value="lastName",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNameStudent(lastName);
    }

    @GetMapping("/findByCnpStudent")
    public Iterable<Student> findByCnpStudent(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpStudent(cnp);
    }

    @GetMapping("/findByEmailStudent")
    public Student findByEmailStudent(@RequestParam(value="email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailStudent(email);
    }

    @GetMapping("/findByPhoneNumberStudent")
    public Iterable<Student> findByPhoneNumberStudent(@RequestParam(value="phoneNumber",required = true)String phoneNumber) throws ServicesExceptions {
        return service.findByPhoneNumberStudent(phoneNumber);
    }

    @GetMapping("/findByUsernameStudent")
    public Student findByUsernameStudent(@RequestParam(value="username",required = true)String username) throws ServicesExceptions {
        return service.findByUsernameStudent(username);
    }


    @PostMapping("/login")
    public ResponseEntity<Person> login(@RequestBody LogInfo logInfo, @RequestBody IObserver observer) {
        try {
            Optional<Person> loggedInPerson = service.login(logInfo, observer);
            if (loggedInPerson.isPresent()) {
                return new ResponseEntity<>(loggedInPerson.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//
//
//
//        public synchronized Optional<Center> loginCenter(LogInfo logInfo , IObserver observer) {
//            if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
//            {
//                throw new ServicesExceptions("Username does not exist");
//            }
//            Center center = dbCenter.findByUsername(logInfo.getUsername());
//            Center center1=dbCenter.findByEmail(logInfo.getEmail());
//            if(center!=null && center.equals(center1)) {
//                if (loggedCenter.get(center.getId()) != null) {
//                    throw new ServicesExceptions("User already logged in.");
//                }
//            }else{
//                throw new ServicesExceptions("Authentication failed.");
//            }
//
//            loggedCenter.put(center.getId(),observer);
//            notifyLogInCenter(center);
//            return Optional.of(center);
//        }
//
//
//        public void logoutCenter(Center center, IObserver observer)  {
//            IObserver localClient=loggedCenter.remove(center.getId());
//            if (localClient==null)
//                throw new ServicesExceptions("User "+center+" is not logged in.");
//            notifyLogOutCenter(center);
//        }
//
//
//
//
//
//        public synchronized  void  logoutPerson(Person password, IObserver observer)  {
//            IObserver localClient=loggedClients.remove(password.getId());
//            if (localClient==null)
//                throw new ServicesExceptions("User "+password+" is not logged in.");
//            notifyLogOutPerson(password);
//        }
//
//
@PostMapping("/registerDonation")
public ResponseEntity<?> donationRegister(@RequestBody Donation donation, @RequestBody Person person, @RequestBody Event event) {
    try {
        service.donationRegister(donation, person, event);
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (ServicesExceptions e) {
        // Handle exception
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @PutMapping("/updateRetrieved")
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

    @DeleteMapping("/deleteRetrieved")
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
    @PostMapping("/retrieved/save")
    public ResponseEntity<?> saveRetrieved(@RequestBody RetrievedCoupons entity) {
        try {
            Optional<RetrievedCoupons> savedEntity = service.saveRetrieved(entity);
            return new ResponseEntity<>(savedEntity.orElse(null), HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/person/update")
    public ResponseEntity<?> updatePerson(@RequestBody Person entity) {
        try {
            Optional<Person> updatedEntity = service.updatePerson(entity);
            return new ResponseEntity<>(updatedEntity.orElse(null), HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/person/delete")
    public ResponseEntity<?> deletePerson(@RequestBody Person entity) {
        try {
            service.deletePerson(entity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/person/save")
    public ResponseEntity<?> savePerson(@RequestBody Person entity) {
        try {
            Optional<Person> savedEntity = service.savePerson(entity);
            return new ResponseEntity<>(savedEntity.orElse(null), HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/personaldata/update")
    public ResponseEntity<?> updatePersonalData(@RequestBody PersonalData entity) {
        try {
            Optional<PersonalData> updatedEntity = service.updatePersonalData(entity);
            return new ResponseEntity<>(updatedEntity.orElse(null), HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/personaldata/delete")
    public ResponseEntity<?> deletePersonalData(@RequestBody PersonalData entity) {
        try {
            service.deletePersonalData(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/personaldata/save")
    public ResponseEntity<?> savePersonalData(@RequestBody PersonalData entity) {
        try {
            Optional<PersonalData> savedEntity = service.savePersonalData(entity);
            return new ResponseEntity<>(savedEntity.orElse(null), HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/medicalinfo/update")
    public ResponseEntity<?> updateMedicalInfo(@RequestBody MedicalInfo entity) {
        try {
            Optional<MedicalInfo> updatedEntity = service.updateMedicalInfo(entity);
            return new ResponseEntity<>(updatedEntity.orElse(null), HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medicalinfo/delete")
    public ResponseEntity<?> deleteMedicalInfo(@RequestBody MedicalInfo entity) {
        try {
            service.deleteMedicalInfo(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/medicalinfo/save")
    public ResponseEntity<?> saveMedicalInfo(@RequestBody MedicalInfo entity) {
        try {
            Optional<MedicalInfo> savedInfo = service.saveMedicalInfo(entity);
            if (savedInfo.isPresent()) {
                return new ResponseEntity<>(savedInfo.get(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/loginfo/update")
    public ResponseEntity<?> updateLogInfo(@RequestBody LogInfo entity) {
        try {
            Optional<LogInfo> updatedInfo = service.updateLogInfo(entity);
            if (updatedInfo.isPresent()) {
                return new ResponseEntity<>(updatedInfo.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/loginfo/delete")
    public ResponseEntity<?> deleteLogInfo(LogInfo entity) {
        try {
            Optional<LogInfo> deleted = service.deleteLogInfo(entity);
            if (deleted.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/loginfo/save")
    public ResponseEntity<LogInfo> saveLogInfo(@RequestBody LogInfo logInfo) {
        try {
            Optional<LogInfo> savedLogInfo = service.saveLogInfo(logInfo);
            if(savedLogInfo.isPresent()) {
                return new ResponseEntity<>(savedLogInfo.get(), HttpStatus.CREATED);
            }
        }
        catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/institution/update")
    public ResponseEntity<Institution> updateInstitution(@RequestBody Institution institution) {
        try {
            Optional<Institution> updatedInstitution = service.updateInstitution(institution);
            if(updatedInstitution.isPresent()) {
                return new ResponseEntity<>(updatedInstitution.get(), HttpStatus.OK);
            }
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/institution/delete")
    public ResponseEntity<Void> deleteInstitution(@RequestParam Institution entity) {
        try {
            service.deleteInstitution(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/institution/save")
    public ResponseEntity<Institution> saveInstitution(@RequestBody Institution institution) {
        try {
            Optional<Institution> savedInstitution = service.saveInstitution(institution);
            if(savedInstitution.isPresent()){
                return new ResponseEntity<>(savedInstitution.get(), HttpStatus.CREATED);
            }
            //return new ResponseEntity<>(savedInstitution, HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Tratați cazurile în care salvarea a eșuat din motive diverse
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/event/update")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        try {
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
    @DeleteMapping("/events/delete")
    public ResponseEntity<Void> deleteEvent(@RequestBody Event event) {
        try {
            service.deleteEvent(event);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Tratarea cazurilor în care ștergerea a eșuat din diverse motive
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/events/save")
    public Optional<Event> saveEvent(@RequestBody Event entity) throws ServicesExceptions {
        return service.saveEvent(entity);
    }


    @PutMapping("/donation-types/update")
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

    @DeleteMapping("/donation-types/delete")
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


    @PostMapping("/donation-types/save")
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


    @PutMapping("/donations/updateDonation")
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

    @DeleteMapping("/donations/deleteDonation")
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



    @PostMapping("/donations/saveDonation")
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


    @PutMapping("/coupons/updateCoupon")
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


    @DeleteMapping("/coupons/deleteCoupon")
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



    @PostMapping("/coupons/saveCoupon")
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



    @PutMapping("/centers/updateCenter")
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


    @DeleteMapping("/centers/deleteCenter/{id}")
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


    @PostMapping("/centers/saveCenter")
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


    @PutMapping("/bloodtests/updateBloodTest")
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


    @DeleteMapping("/bloodtests/deleteBloodTest")
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


    @PostMapping("/bloodtests")
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


    @PutMapping("/addresses/updateAddress")
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



    @DeleteMapping("/addresses/deleteAddress")
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


    @PostMapping("/addresses/saveAddress")
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
