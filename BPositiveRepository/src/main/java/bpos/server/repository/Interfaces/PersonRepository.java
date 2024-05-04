package bpos.repository.Interfaces;

import bpos.model.Person;

public interface PersonRepository  extends IRepository<Integer, Person>{
    Iterable<Person> findByFirstName(String firstName);
    Iterable<Person> findByLastName(String lastName);
    Iterable<Person> findByCnp(String cnp);
    Person findByEmail(String email);
    Iterable<Person> findByPhoneNumber(String phoneNumber);
    Person findByUsername(String username);
}
