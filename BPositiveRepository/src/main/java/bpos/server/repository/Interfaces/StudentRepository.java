package bpos.server.repository.Interfaces;


import bpos.common.model.Student;

public interface StudentRepository extends IRepository<Integer, Student>{
    Iterable<Student> findByFirstName(String firstName);
    Iterable<Student> findByLastName(String lastName);
    Iterable<Student> findByCnp(String cnp);
    Student findByEmail(String email);
    Iterable<Student> findByPhoneNumber(String phoneNumber);
    Student findByUsername(String username);
}
