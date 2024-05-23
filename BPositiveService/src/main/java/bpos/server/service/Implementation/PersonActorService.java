package bpos.server.service.Implementation;

import bpos.common.model.*;
import bpos.server.repository.Interfaces.*;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.IPersonActorInterface;
import bpos.server.service.ServicesExceptions;
//import bpos.server.service.WebSockets.JwtResponse;
//import bpos.server.service.WebSockets.JwtTokenUtil;
import bpos.server.service.WebSockets.WebSocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Optional;

public class PersonActorService implements IPersonActorInterface {
    private final PersonalDataRepository dbPersonalData;
    private final PersonRepository dbPerson;
    private final StudentRepository dbStudent;
    private final LogInfoRepository dbLogInfo;
    private final InstitutionRepository dbInstitution;
    private final WebSocketHandler webSocketHandler;
    private final Map<Integer, Object> loggedEntities;
//    private  UserDetailsService userDetailsService;
//    private  JwtTokenUtil jwtTokenUtil;

    private ObjectMapper objectMapper;


    public PersonActorService(PersonalDataRepository personalDataRepository, PersonRepository personRepository, StudentRepository studentRepository, LogInfoRepository logInfoRepository, InstitutionRepository institutionRepository, WebSocketHandler webSocketHandler /*ObjectMapper objectMapper, UserDetailsService userDetailsService*//*, JwtTokenUtil jwtTokenUtil*/) {
        this.dbPersonalData = personalDataRepository;
        this.dbPerson=personRepository;
        this.dbStudent=studentRepository;
        this.dbLogInfo=logInfoRepository;
        this.dbInstitution=institutionRepository;
        //vad daca trebuie sa initilaizesz si pt observer
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
//        this.userDetailsService = userDetailsService;
//        this.jwtTokenUtil = jwtTokenUtil;
        this.loggedEntities = new java.util.concurrent.ConcurrentHashMap<>();
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
    public Optional<Person> savePerson(Person entity) throws ServicesExceptions {
        return dbPerson.save(entity);
    }

    @Override
    public Optional<Person> deletePerson(Person entity) throws ServicesExceptions {
        return dbPerson.delete(entity);
    }

    @Override
    public Optional<Person> updatePerson(Person entity) throws ServicesExceptions {
        return dbPerson.update(entity);
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

    @Override
    public void logoutPerson(Person person, IObserver observer) throws ServicesExceptions {
        loggedEntities.remove(person.getId());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new ServicesExceptions("Error sending message to all clients");
        }

        // Trimiteți obiectul serializat prin WebSocket
        if (json != null) {
            try {
                webSocketHandler.sendMessageToAll(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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

}
