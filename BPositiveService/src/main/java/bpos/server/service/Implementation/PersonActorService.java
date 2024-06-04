package bpos.server.service.Implementation;

import bpos.common.model.*;
import bpos.common.model.Enums.BloodType;
import bpos.common.model.Enums.Rh;
import bpos.other.NotificationRest;
import bpos.other.PersonRequest;
import bpos.server.repository.Interfaces.*;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.IPersonActorInterface;
import bpos.server.service.ServicesExceptions;
//import bpos.server.service.WebSockets.JwtResponse;
//import bpos.server.service.WebSockets.JwtTokenUtil;
import bpos.server.service.WebSockets.NotificationService;
//import bpos.server.service.WebSockets.WebSocketHandler;
import bpos.server.service.exceptions.InvalidCredentialsException;
import bpos.server.service.exceptions.UserAlreadyLoggedInException;
import bpos.server.service.utils.PasswordEncryption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PersonActorService implements IPersonActorInterface {
    private final PersonalDataRepository dbPersonalData;
    private final PersonRepository dbPerson;
    private final StudentRepository dbStudent;
    private final LogInfoRepository dbLogInfo;
    private final AddressRepository dbAdress;
    private final MedicalInfoRepository dbMedicalInfo;
    private final InstitutionRepository dbInstitution;

//    private  UserDetailsService userDetailsService;
//    private  JwtTokenUtil jwtTokenUtil;
private final ConcurrentHashMap<String, Boolean> loggedInUsers = new ConcurrentHashMap<>();
    private final Map<Integer, IObserver> loggedCenter = new ConcurrentHashMap<>();
    private final NotificationService notificationService;

    private ObjectMapper objectMapper;
    private final BloodTestRepository dbBloodTest;

    public PersonActorService(PersonalDataRepository personalDataRepository, PersonRepository personRepository, StudentRepository studentRepository, LogInfoRepository logInfoRepository, AddressRepository dbAdress,
                              InstitutionRepository institutionRepository,
            /*ObjectMapper objectMapper, UserDetailsService userDetailsService*//*, JwtTokenUtil jwtTokenUtil*/NotificationService notificationService,MedicalInfoRepository medicalInfoRepository,
                              BloodTestRepository bloodTestRepository) {
        this.dbPersonalData = personalDataRepository;
        this.dbPerson=personRepository;
        this.dbStudent=studentRepository;
        this.dbLogInfo=logInfoRepository;
        this.dbAdress = dbAdress;
        this.dbInstitution=institutionRepository;
        //vad daca trebuie sa initilaizesz si pt observer
        this.notificationService = notificationService;
        //        this.userDetailsService = userDetailsService;
//        this.jwtTokenUtil = jwtTokenUtil;
        this.dbMedicalInfo=medicalInfoRepository;
        this.dbBloodTest=bloodTestRepository;
    }
    public void addBloodTestToMedicalInfo(String username, String fileName, String filePath) throws ServicesExceptions {
        // Find the person's medical info by username
        Person person = dbPerson.findByUsername(username);
        if (person == null) {
            throw new ServicesExceptions("Person not found: " + username);
        }
        MedicalInfo medicalInfo = person.getMedicalInfo();
        if (medicalInfo == null) {
            throw new ServicesExceptions("Medical Info not found for user: " + username);
        }

        // Create a new BloodTest entity
        BloodTest bloodTest = new BloodTest();
        bloodTest.setName(fileName);
        bloodTest.setPath(filePath);
        bloodTest.setMedicalInfo(medicalInfo.getId());

        dbBloodTest.save(bloodTest);
    }
    @Override
    public void logout(Person person) throws ServicesExceptions {
        loggedInUsers.remove(person.getPersonLogInfo().getUsername());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new ServicesExceptions("Error sending message to all clients");
        }

        // Trimiteți obiectul serializat prin WebSocket
        if (json != null) {
            try {
                notificationService.notifyAdmins(String.valueOf(NotificationRest.LOGOUT));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<PersonalData> findOnePersonalData(Integer integer) throws ServicesExceptions {
         return dbPersonalData.findOne(integer);
    }

    @Override
    public Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions {
        return dbPersonalData.findAll();
    }

    @Override
    public Optional<PersonalData> savePersonalData(PersonalData entity) throws ServicesExceptions {
        return dbPersonalData.save(entity);
    }

    @Override
    public Optional<PersonalData> deletePersonalData(PersonalData entity) throws ServicesExceptions {
        return dbPersonalData.delete(entity);
    }

    @Override
    public Optional<PersonalData> updatePersonalData(PersonalData entity) throws ServicesExceptions {
        return dbPersonalData.update(entity);
    }

    @Override
    public Iterable<PersonalData> findByFirstNamePersonalData(String firstName) throws ServicesExceptions {
        return dbPersonalData.findByFirstName(firstName);
    }

    @Override
    public Iterable<PersonalData> findByLastNamePersonalData(String lastName) throws ServicesExceptions {
        return dbPersonalData.findByLastName(lastName);
        }

    @Override
    public PersonalData findByCnpPersonalData(String cnp) throws ServicesExceptions {
        return dbPersonalData.findByCnp(cnp);
    }

    @Override
    public Optional<Person> findOnePerson(Integer integer) throws ServicesExceptions {
        return dbPerson.findOne(integer);
    }

    @Override
    public Iterable<Person> findAllPersons() throws ServicesExceptions {
        return dbPerson.findAll();
    }
    @Override
    public Iterable<Person> findAllPersonsNonStudents() throws ServicesExceptions {
        Iterable<Person> persons=dbPerson.findAll();
        Iterable<Student> students=dbStudent.findAll();
        ArrayList<Person> personsNonStudents=new ArrayList<>();
        for(Person person:persons)
        {
            boolean isStudent=false;
            for(Student student:students)
            {
                if(person.getId()==student.getId())
                {
                    isStudent=true;
                    break;
                }
            }
            if(!isStudent)
            {
                personsNonStudents.add(person);
            }
        }
        return personsNonStudents;
    }

    @Override
    public Optional<Person> savePerson(Person entity) throws ServicesExceptions {
        Person person = dbPerson.save(entity).get();
        notificationService.notifyAdmins(String.valueOf(NotificationRest.REGISTER));
        return Optional.ofNullable(person);
    }

    @Override
    public Optional<Person> deletePerson(Person entity) throws ServicesExceptions {

        return dbPerson.delete(entity);
    }
    @Override
    public Optional<Person> updatePerson(Person object) throws ServicesExceptions {
        Person person= dbPerson.update(object).get();
        notificationService.notifyAdmins(String.valueOf(NotificationRest.USER_UPDATE));
        notificationService.notifyClient(String.valueOf(NotificationRest.USER_UPDATE));
        return dbPerson.update(object);
    }

    @Override
    public Iterable<Person> findByFirstNamePerson(String firstName) throws ServicesExceptions {
        return dbPerson.findByFirstName(firstName);
    }

    @Override
    public Iterable<Person> findByLastNamePerson(String lastName) throws ServicesExceptions {
        return dbPerson.findByLastName(lastName);
    }

    @Override
    public Iterable<Person> findByCnpPerson(String cnp) throws ServicesExceptions {
        return dbPerson.findByCnp(cnp);
    }

    @Override
    public Person findByEmailPerson(String email) throws ServicesExceptions {
        return dbPerson.findByEmail(email);
    }

    @Override
    public Iterable<Person> findByPhoneNumberPerson(String phoneNumber) throws ServicesExceptions {
        return dbPerson.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Person findByUsernamePerson(String username) throws ServicesExceptions {
        return dbPerson.findByUsername(username);
    }

    @Override
    public Optional<Student> findOneStudent(Integer integer) throws ServicesExceptions {
        return dbStudent.findOne(integer);
    }

    @Override
    public Iterable<Student> findAllStudent() throws ServicesExceptions {
        return dbStudent.findAll();
    }

    @Override
    public Optional<Student> saveStudent(Student entity) throws ServicesExceptions {
        return dbStudent.save(entity);
    }

    @Override
    public Optional<Student> deleteStudent(Student entity) throws ServicesExceptions {
        return dbStudent.delete(entity);
    }

    @Override
    public Optional<Student> updateStudent(Student entity) throws ServicesExceptions {
        return dbStudent.update(entity);
    }

    @Override
    public Iterable<Student> findByFirstNameStudent(String firstName) throws ServicesExceptions {
        return dbStudent.findByFirstName(firstName);
    }

    @Override
    public Iterable<Student> findByLastNameStudent(String lastName) throws ServicesExceptions {
        return dbStudent.findByLastName(lastName);
    }

    @Override
    public Iterable<Student> findByCnpStudent(String cnp) throws ServicesExceptions {
        return dbStudent.findByCnp(cnp);
    }

    @Override
    public Student findByEmailStudent(String email) throws ServicesExceptions {
        return dbStudent.findByEmail(email);
    }

    @Override
    public Iterable<Student> findByPhoneNumberStudent(String phoneNumber) throws ServicesExceptions {
        return dbStudent.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Student findByUsernameStudent(String username) throws ServicesExceptions {
        return dbStudent.findByUsername(username);
    }

    @Override
    public Optional<Person> login(String username, String password) throws UserAlreadyLoggedInException, InvalidCredentialsException, ServicesExceptions {

        LogInfo logInfo = new LogInfo(username, password, username, null);
        Person person = dbPerson.findByUsername(logInfo.getUsername());

        if (person == null) {
            throw new InvalidCredentialsException("Username does not exist");
        }

        // Get stored password hash and seed from the database
        LogInfo storedLogInfo = dbLogInfo.findByUsername(logInfo.getUsername());
        String storedHashedPassword = storedLogInfo.getPassword(); // assuming getPassword() returns the hashed password
        String storedSeed = storedLogInfo.getSeed(); // assuming getSeed() returns the seed used for hashing

        // Verify the password
        try {
            if (!PasswordEncryption.verifyPassword(password, storedHashedPassword, storedSeed)) {
                throw new InvalidCredentialsException("Invalid password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ServicesExceptions("Password verification failed", e);
        }

        if (loggedInUsers.containsKey(person.getPersonLogInfo().getUsername()) && loggedInUsers.get(username)) {
            throw new UserAlreadyLoggedInException("User is already logged in");
        }

        notificationService.notifyAdmins(String.valueOf(NotificationRest.LOGIN));
        return Optional.of(person);
    }



//    @Override
//    public Optional<JwtResponse> login(LogInfo logInfo, IObserver observer) throws ServicesExceptions {
//        if(dbLogInfo.findByUsername(logInfo.getUsername())==null) {
//            throw new ServicesExceptions("Username does not exist");
//        }
//        Person person = dbPerson.findByUsername(logInfo.getUsername());
//        if(person != null) {
//            if(loggedEntities.get(person.getId()) != null) {
//                throw new ServicesExceptions("User already logged in.");
//            }
//        } else {
//            throw new ServicesExceptions("Authentication failed.");
//        }
//        loggedEntities.put(person.getId(), person);
//
//        String json = null;
//        try {
//            json = objectMapper.writeValueAsString(person);
//        } catch (Exception e) {
//            throw new ServicesExceptions("Error sending message to all clients");
//        }
//        try {
//            webSocketHandler.sendMessageToAll(json);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // Generate JWT token
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(logInfo.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        return Optional.of(new JwtResponse(token));
//    }
//    private void notifyTheOthersLogInPerson(Person person) {
//        Iterable<Person> personIterable=dbPerson.findAll();
//        personIterable.forEach(person1 -> {
//            IObserver client=loggedEntities.get(person1.getId());
//            if(client!=null){
//                try {
//                    client.loginPersonEvent(person);
//                } catch (ServicesExceptions e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }






    private Person handlePersonRequest(PersonRequest personRequest, String type) throws ServicesExceptions {
        Person person = new Person();
        if (type.equals("UPDATE")) {
            person = dbPerson.findByUsername(personRequest.getUsername());
            if (person == null) {
                throw new ServicesExceptions("Person does not exist");
            }
        }
        LogInfo logInfo = new LogInfo();
        Address address = new Address();
        PersonalData personalData = new PersonalData();
        MedicalInfo medicalInfo = new MedicalInfo();
        if (type.equals("UPDATE")) {
            personalData = person.getPersonalDate();
            logInfo = person.getPersonLogInfo();
            address = personalData.getAddress();
            medicalInfo = person.getMedicalInfo();
        }
        try {
            logInfo.setUsername(personRequest.getUsername());
            List<String> passwordList = List.of(PasswordEncryption.hashPassword(personRequest.getPassword()));
            String password=passwordList.get(1);
            String hash=passwordList.get(0);
            logInfo.setPassword(password);
            logInfo.setEmail(personRequest.getEmail());
            logInfo.setSeed(hash);
            if (type.equals("SAVE")) {
                Optional<LogInfo> newLogInfo = dbLogInfo.save(logInfo);
                person.setPersonLogInfo(newLogInfo.get());
            } else {
                Optional<LogInfo> newLogInfo = dbLogInfo.update(logInfo);
                person.setPersonLogInfo(newLogInfo.get());
            }
            personalData.setFirstName(personRequest.getFirstName());
            personalData.setLastName(personRequest.getLastName());
            personalData.setCnp(personRequest.getCnp());
            personalData.setBirthDate(LocalDate.parse(personRequest.getBirthday()));
            personalData.setPhoneNumber(personRequest.getTelephone());
            personalData.setSex(personRequest.getSex());

            address.setApartment(personRequest.getApartment());
            address.setBlock(personRequest.getBlock());
            address.setCity(personRequest.getCity());
            address.setCountry(personRequest.getCountry());
            address.setFloor(Integer.valueOf(personRequest.getFloor()));
            address.setCounty(personRequest.getCounty());
            address.setNumberStreet(personRequest.getNumber());
            address.setStreet(personRequest.getStreet());
            if (type.equals("SAVE")) {
                Optional<Address> newAddress = dbAdress.save(address);
                personalData.setAddress(newAddress.get());
            } else {
                Optional<Address> newAddress = dbAdress.update(address);
                personalData.setAddress(newAddress.get());
            }
            personalData.setId(logInfo.getId());
            if (type.equals("SAVE")) {
                Optional<PersonalData> newPersonalData = savePersonalData(personalData);
                person.setPersonalDate(newPersonalData.get());
            } else {
                Optional<PersonalData> newPersonalData = updatePersonalData(personalData);
                person.setPersonalDate(newPersonalData.get());
            }
            if(!(personRequest.getBloodType()==null || personRequest.getBloodRh()==null))
            {
                medicalInfo.setBloodType(BloodType.valueOf(personRequest.getBloodType().toUpperCase()));
                medicalInfo.setRh(Rh.valueOf(personRequest.getBloodRh().toUpperCase()));
                medicalInfo.setEligibility(personRequest.getEligibility()==1);
            }


            if (type.equals("SAVE")) {
                person.setEvents(new ArrayList<>());
                person.setDonations(new ArrayList<>());
                person.setPoints(0);
                person.setMedicalInfo(medicalInfo);
                person.setInstitution(null);
                person.setId(logInfo.getId());
                medicalInfo.setId(logInfo.getId());
                Optional<MedicalInfo> newMedicalInfo = dbMedicalInfo.save(medicalInfo);
                person.setMedicalInfo(newMedicalInfo.get());

                Optional<Person> newPerson = savePerson(person);
            } else {
                person.setMedicalInfo(medicalInfo);
                dbMedicalInfo.update(medicalInfo);
                Optional<Person> newPerson = updatePerson(person);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return person;
    }


    @Override
    public Optional<Institution> findOneInstitution(Integer integer) throws ServicesExceptions {
        return dbInstitution.findOne(integer);
    }

    @Override
    public Iterable<Institution> findAllInstitutions() throws ServicesExceptions {
        return dbInstitution.findAll();
    }

    @Override
    public Optional<Institution> saveInstitution(Institution entity) throws ServicesExceptions {
        return dbInstitution.save(entity);
    }

    @Override
    public Optional<Institution> deleteInstitution(Institution entity) throws ServicesExceptions {
        return dbInstitution.delete(entity);
    }

    @Override
    public Optional<Institution> updateInstitution(Institution entity) throws ServicesExceptions {
        return dbInstitution.update(entity);
    }

    @Override
    public Iterable<Institution> findByNameInstitution(String name) throws ServicesExceptions {
        return dbInstitution.findByName(name);
    }

    @Override
    public Iterable<Institution> findByAddressInstitution(String address) throws ServicesExceptions {
        return dbInstitution.findByAddress(address);
    }

    @Override
    public Iterable<Institution> findByEmailInstitution(String email) throws ServicesExceptions {
        return dbInstitution.findByEmail(email);
    }

    @Override
    public void signUp(PersonRequest personRequest) {
        try {
            handlePersonRequest(personRequest,"SAVE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        notificationService.notifyAdmins(String.valueOf(NotificationRest.REGISTER));

    }
    @Override
    public Person profileChange(PersonRequest personRequest) throws ServicesExceptions {
        Person person= dbPerson.findByUsername(personRequest.getUsername());
        if (person == null) {
            throw new ServicesExceptions("Person does not exist");
        }
        try {
            person= Optional.of(handlePersonRequest(personRequest,"UPDATE")).get();
            notificationService.notifyAdmins(String.valueOf(NotificationRest.USER_UPDATE));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return person;

    }

    @Override
    public void logoutAdmin(Person person) throws ServicesExceptions {
        System.out.println(person.getPersonLogInfo().getUsername());
        loggedInUsers.remove(person.getPersonLogInfo().getUsername());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new ServicesExceptions("Error sending message to all clients");

        }
    }

    private LogInfo authenticate(String username, String password) {
        boolean found = false;
        LogInfo logInfo = new LogInfo();
        if (Objects.equals(username, "admin") && Objects.equals(password, "admin")) {
            logInfo = new LogInfo("admin", "admin", "admin", "admin");
            return logInfo;
        }
        return logInfo;
    }





}
