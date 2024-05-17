package bpos.server.service.Interface;

import bpos.common.model.*;
import bpos.server.service.IObserver;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.WebSockets.JwtResponse;

import java.util.Optional;

public interface IPersonActorInterface {
    Optional<PersonalData> findOnePersonalData(Integer integer) throws ServicesExceptions;
    Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions;
    Optional<PersonalData> savePersonalData(PersonalData entity) throws ServicesExceptions;
    Optional<PersonalData> deletePersonalData(PersonalData entity) throws ServicesExceptions;
    Optional<PersonalData> updatePersonalData(PersonalData entity) throws ServicesExceptions;
    Iterable<PersonalData> findByFirstNamePersonalData(String firstName) throws ServicesExceptions;
    Iterable<PersonalData> findByLastNamePersonalData(String lastName) throws ServicesExceptions;
    PersonalData findByCnpPersonalData(String cnp) throws ServicesExceptions;
    //person services
    Optional<Person> findOnePerson(Integer integer) throws ServicesExceptions;
    Iterable<Person> findAllPersons() throws ServicesExceptions;
    Optional<Person> savePerson(Person entity) throws ServicesExceptions;
    Optional<Person> deletePerson(Person entity) throws ServicesExceptions;
    Optional<Person> updatePerson(Person entity) throws ServicesExceptions;
    Iterable<Person> findByFirstNamePerson(String firstName) throws ServicesExceptions;
    Iterable<Person> findByLastNamePerson(String lastName) throws ServicesExceptions;
    Iterable<Person> findByCnpPerson(String cnp) throws ServicesExceptions;
    Person findByEmailPerson(String email) throws ServicesExceptions;
    Iterable<Person> findByPhoneNumberPerson(String phoneNumber) throws ServicesExceptions;
    Person findByUsernamePerson(String username) throws ServicesExceptions;
    Optional<Student> findOneStudent(Integer integer) throws ServicesExceptions;
    Iterable<Student> findAllStudent() throws ServicesExceptions;
    Optional<Student> saveStudent(Student entity) throws ServicesExceptions;
    Optional<Student> deleteStudent(Student entity) throws ServicesExceptions;
    Optional<Student> updateStudent(Student entity) throws ServicesExceptions;
    Iterable<Student> findByFirstNameStudent(String firstName) throws ServicesExceptions;
    Iterable<Student> findByLastNameStudent(String lastName) throws ServicesExceptions;
    Iterable<Student> findByCnpStudent(String cnp) throws ServicesExceptions;
    Student findByEmailStudent(String email) throws ServicesExceptions;
    Iterable<Student> findByPhoneNumberStudent(String phoneNumber) throws ServicesExceptions;
    Student findByUsernameStudent(String username) throws ServicesExceptions;
    Optional<JwtResponse> login(LogInfo logInfo, IObserver observer) throws ServicesExceptions;

    void  logoutPerson(Person center,IObserver observer) throws ServicesExceptions;
    Optional<Institution> findOneInstitution(Integer integer) throws ServicesExceptions;
    Iterable<Institution> findAllInstitutions() throws ServicesExceptions;
    Optional<Institution> saveInstitution(Institution entity) throws ServicesExceptions;
    Optional<Institution> deleteInstitution(Institution entity) throws ServicesExceptions;
    Optional<Institution> updateInstitution(Institution entity) throws ServicesExceptions;
    Iterable<Institution> findByNameInstitution(String name)  throws ServicesExceptions;
    Iterable<Institution> findByAddressInstitution(String address) throws ServicesExceptions;
    Iterable<Institution> findByEmailInstitution(String email) throws ServicesExceptions;

}
