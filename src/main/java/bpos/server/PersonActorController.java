package bpos.server;

import bpos.common.model.*;
import bpos.server.service.IObserver;
import bpos.server.service.Implementation.PersonActorService;
import bpos.server.service.Interface.IAddressService;
import bpos.server.service.Interface.IEventService;
import bpos.server.service.Interface.ILogInfoService;
import bpos.server.service.Interface.IPersonActorInterface;
import bpos.server.service.ServicesExceptions;
//import bpos.server.service.WebSockets.JwtResponse;
//import bpos.server.service.WebSockets.JwtTokenUtil;
import bpos.server.service.exceptions.InvalidCredentialsException;
import bpos.server.service.exceptions.UserAlreadyLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;

@RequestMapping("/personActorService")
@CrossOrigin
@RestController
public class PersonActorController {

    private IPersonActorInterface service;
    private ILogInfoService logInfoService;
    private IEventService eventService;
    private IAddressService addressService;
//    private UserDetailsService userDetailService;
//    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    public PersonActorController(IPersonActorInterface service /*, @Qualifier("jwtUserDetailsService")UserDetailsService userDetailService, JwtTokenUtil jwtTokenUtil*/,ILogInfoService logInfo,IAddressService addressService,IEventService eventService) {
        this.service = service;
        this.logInfoService=logInfo;
        this.addressService=addressService;
        this.eventService=eventService;
//        this.userDetailService = userDetailService;
//        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/personRequest")
    public Person personRequest(@RequestParam (value="firstName") String firstName,@RequestParam(value="lastName") String lastName,@RequestParam(value="cnp") String cnp,@RequestParam (value="birthday") LocalDate birthday,@RequestParam(value="sex") String sex,@RequestParam(value="country") String country ,@RequestParam(value ="city") String city,@RequestParam(value="county") String county,@RequestParam(value="street") String street, @RequestParam(value="number") String number,@RequestParam(value="bloc") String bloc,@RequestParam(value="apartament") String apartment,@RequestParam(value="floor") Integer floor,@RequestParam(value="telephone") String telephone ,@RequestParam(value="email") String email,@RequestParam(value="username") String username,@RequestParam(value="password") String password,@RequestParam(value="confirm-password") String confirmPassword) {
        Person person = new Person();
        PersonalData personalData = new PersonalData();
        LogInfo logInfo = new LogInfo();
        Address address = new Address();
        logInfo.setUsername(username);
        logInfo.setPassword(password);
        logInfo.setEmail(email);
        try {
            Optional<LogInfo> newLogInfo=logInfoService.saveLogInfo(logInfo);
            person.setPersonLogInfo(newLogInfo.get());
            personalData.setFirstName(firstName);
            personalData.setLastName(lastName);
            personalData.setCnp(cnp);
            personalData.setBirthDate(birthday);
            personalData.setPhoneNumber(telephone);
            personalData.setSex(sex);
            address.setApartment(apartment);
            address.setBlock(bloc);
            address.setCity(city);
            address.setCountry(country);
            address.setFloor(floor);
            address.setCounty(city);
            address.setNumberStreet(number);
            address.setStreet(street);
            Optional<Address> newAddress=addressService.saveAddress(address);
            personalData.setAddress(newAddress.get());
            Optional<PersonalData> newPersonalData=service.savePersonalData(personalData);
            person.setPersonalDate(newPersonalData.get());
            person.setEvents(new ArrayList<>());
            person.setDonations(new ArrayList<>());
            person.setPoints(0);
            person.setMedicalInfo(new MedicalInfo());
            Optional<Person> newPerson=service.savePerson(person);

            return  person;
        } catch (ServicesExceptions e) {
            throw new RuntimeException(e);
        }


    }
    @PutMapping("/students/student-for-request")
    public Student studentRequest(@RequestParam(value="username") String username,@RequestParam(value="departamanet") String departament,@RequestParam(value="grupa") String grupa , @RequestParam(value="faculty") String faculty ,@RequestParam(value="name-university") String name,@RequestParam(value="year") Integer year,@RequestParam(value="email-university") String emailUniversity,@RequestParam(value = "address-university") String addressUniversity){
        try {
            Person person=service.findByUsernamePerson(username);
            Institution institution=new Institution();
            institution.setName(name);
            institution.setEmail(emailUniversity);
            institution.setAddress(addressUniversity);
            Student student=new Student();
            student.setFaculty(faculty);
            student.setDepartment(departament);
            student.setGroup(grupa);
            student.setUniversity(institution);
            student.setYear(year);
            student.setPersonLogInfo(person.getPersonLogInfo());
            student.setPoints(person.getPoints());
            student.setPersonalDate(person.getPersonalDate());
            student.setMedicalInfo(person.getMedicalInfo());
            student.setInstitution(person.getInstitution());
            Optional<Student> newStudent=service.saveStudent(student);
            return newStudent.get();
        } catch (ServicesExceptions e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/persons")
    public Iterable<Person> findAllPersons() throws ServicesExceptions {
        System.out.println("getAllPersons");
        return service.findAllPersons();
    }



    @GetMapping("/institutions/{id}")
    public Optional<Institution> findOneInstitution(@PathVariable Integer id) throws ServicesExceptions {
        return service.findOneInstitution(id);
    }
    //merge
    @GetMapping("/institutions")
    public Iterable<Institution> findAllInstitutions() throws ServicesExceptions {
        return service.findAllInstitutions();
    }
    //merge
    @GetMapping("/institutions/name")
    public Iterable<Institution> findByNameInstitution(@RequestParam(value="name_institution",required = true)String name) throws ServicesExceptions {
        return service.findByNameInstitution(name);
    }
    //merge
    @GetMapping("/institutions/address")
    public Iterable<Institution> findByAddressInstitution(@RequestParam(value="address_institution",required = true)String address) throws ServicesExceptions {
        return service.findByAddressInstitution(address);
    }
    //merge
    @GetMapping("/institutions/email")
    public Iterable<Institution> findByEmailInstitution(@RequestParam(value="email",required = true) String email) throws ServicesExceptions {
        return service.findByEmailInstitution(email);
    }

    @GetMapping("/personal-datas/{id}")
    public Optional<PersonalData> findOnePersonalData(@PathVariable Integer id) throws ServicesExceptions {
        return service.findOnePersonalData(id);
    }
    //merge
    @GetMapping("/personal-datas")
    public Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions {
        return service.findAllPersonalDatas();
    }
    // e nula functia din repo
    @GetMapping("/personal-datas/first-name")
    public Iterable<PersonalData> findByFirstNamePersonalData(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNamePersonalData(firstName);
    }
    // e nula functia din repo
    @GetMapping("/personal-datas/last-name")
    public Iterable<PersonalData> findByLastNamePersonalData(@RequestParam(value="id_personal_data",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNamePersonalData(lastName);
    }
    // TO DO - nula functia din repo
    @GetMapping("/personal-datas/cnp")
    public PersonalData findByCnpPersonalData(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpPersonalData(cnp);
    }
    //e nula functia din repo
    @GetMapping("/persons/{id}")
    public Optional<Person> findOnePerson(@PathVariable Integer id) throws ServicesExceptions {
        return service.findOnePerson(id);
    }



    @GetMapping("/persons/first-name")
    public Iterable<Person> findByFirstNamePerson(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNamePerson(firstName);
    }

    @GetMapping("/persons/last-name")
    public Iterable<Person> findByLastNamePerson(@RequestParam(value="lastName",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNamePerson(lastName);
    }
    //merge
    @GetMapping("/persons/cnp")
    public Iterable<Person> findByCnpPerson(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpPerson(cnp);
    }
    //asta nu merge da eroare in db
    @GetMapping("/persons/email")
    public Person findByEmailPerson(@RequestParam(value="id_email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailPerson(email);
    }
    //merge
    @GetMapping("/persons/phone-number")
    public Iterable<Person> findByPhoneNumberPerson(@RequestParam(value="phoneNumber",required = true)String phoneNumber) throws ServicesExceptions {
        return service.findByPhoneNumberPerson(phoneNumber);
    }
    //merge
    @GetMapping("/persons/username")
    public Person findByUsernamePerson(@RequestParam(value="usernamePerson",required = true)String username) throws ServicesExceptions {
        return service.findByUsernamePerson(username);
    }



    @GetMapping("/students/{id}")
    public Optional<Student> findOneStudent(@PathVariable Integer id) throws ServicesExceptions {
        return service.findOneStudent(id);
    }
    //nu merge
    @GetMapping("/students")
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

    @DeleteMapping("/students")
    public Optional<Student> deleteStudent(Student entity) throws ServicesExceptions {
        return service.deleteStudent(entity);
    }

//    @GetMapping("/updateStudent")
    @PutMapping("/students")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
    try {
        Optional<Student> updatedStudent = service.updateStudent(student);
        return updatedStudent.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } catch (ServicesExceptions e) {
        // Handle exception
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @GetMapping("/students/first-name")
    public Iterable<Student> findByFirstNameStudent(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNameStudent(firstName);
    }

    @GetMapping("/students/last-name")
    public Iterable<Student> findByLastNameStudent(@RequestParam(value="lastName",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNameStudent(lastName);
    }

    @GetMapping("/students/cnp")
    public Iterable<Student> findByCnpStudent(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpStudent(cnp);
    }

    @GetMapping("/students/email")
    public Student findByEmailStudent(@RequestParam(value="email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailStudent(email);
    }

    @GetMapping("/students/phone-number")
    public Iterable<Student> findByPhoneNumberStudent(@RequestParam(value="phoneNumber",required = true)String phoneNumber) throws ServicesExceptions {
        return service.findByPhoneNumberStudent(phoneNumber);
    }

    @GetMapping("/students/username")
    public Student findByUsernameStudent(@RequestParam(value="username",required = true)String username) throws ServicesExceptions {
        return service.findByUsernameStudent(username);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam (value="username")String username,@RequestParam(value ="password") String password) {
        try {
            Optional<Person> loggedInPerson = service.login(username, password);
            if (loggedInPerson.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("person", loggedInPerson.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidCredentialsException | UserAlreadyLoggedInException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String username) {
        try {
            Person person=service.findByUsernamePerson(username);
            service.logout(person);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServicesExceptions e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping("/authenticate")
//    public ResponseEntity<JwtResponse> authenticate(@RequestBody LogInfo logInfo) {
//        try {
//            // Attempt login
//            Optional<JwtResponse> loggedInPerson = service.login(logInfo, null); // Assuming observer is not needed here
//            if (loggedInPerson.isPresent()) {
//                // Authenticate using Spring Security
//                authenticate(logInfo.getUsername(), logInfo.getPassword());
//
//                // Load user details
//                 UserDetails userDetails = userDetailService.loadUserByUsername(logInfo.getUsername());
//
//                // Generate JWT token
//                final String token = jwtTokenUtil.generateToken(userDetails);
//
//                // Create and return the response with JWT token
//                return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }
//        } catch (ServicesExceptions e) {
//            // Handle login exception
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            // Handle other exceptions
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (Exception e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
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
//        public synchronized  void  logoutPerson(Person password, IObserver observer)  {
//            IObserver localClient=loggedClients.remove(password.getId());
//            if (localClient==null)
//                throw new ServicesExceptions("User "+password+" is not logged in.");
//            notifyLogOutPerson(password);
//        }
//
//




    @PutMapping("/persons")
    public ResponseEntity<?> updatePerson(@RequestBody Person entity) {
        try {
            Optional<Person> updatedEntity = service.updatePerson(entity);
            return new ResponseEntity<>(updatedEntity.orElse(null), HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/persons")
    public ResponseEntity<?> deletePerson(@RequestBody Person entity) {
        try {
            service.deletePerson(entity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/persons")
    public ResponseEntity<?> savePerson(@RequestBody Person entity) {
        try {
            Optional<Person> savedEntity = service.savePerson(entity);
            return new ResponseEntity<>(savedEntity.orElse(null), HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/personal-datas")
    public ResponseEntity<?> updatePersonalData(@RequestBody PersonalData entity) {
        try {
            Optional<PersonalData> updatedEntity = service.updatePersonalData(entity);
            return new ResponseEntity<>(updatedEntity.orElse(null), HttpStatus.OK);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/personal-datas")
    public ResponseEntity<?> deletePersonalData(@RequestBody PersonalData entity) {
        try {
            service.deletePersonalData(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/personal-datas")
    public ResponseEntity<?> savePersonalData(@RequestBody PersonalData entity) {
        try {
            Optional<PersonalData> savedEntity = service.savePersonalData(entity);
            return new ResponseEntity<>(savedEntity.orElse(null), HttpStatus.CREATED);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PutMapping("/institutions")
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

    @DeleteMapping("/institutions")
    public ResponseEntity<Void> deleteInstitution(@RequestParam Institution entity) {
        try {
            service.deleteInstitution(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ServicesExceptions e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/institutions")
    public ResponseEntity<Institution> saveInstitution(@RequestBody Institution institution) {
        try {
            Optional<Institution> savedInstitution = service.saveInstitution(institution);
            if(savedInstitution.isPresent()){
                return new ResponseEntity<>(savedInstitution.get(), HttpStatus.CREATED);
            }

        } catch (ServicesExceptions e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
