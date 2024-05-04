package bpos.repository.Interfaces;

import bpos.model.Institution;

public interface InstitutionRepository extends IRepository<Integer, Institution>{
    Iterable<Institution> findByName(String name);
    Iterable<Institution> findByAddress(String address);
    Iterable<Institution> findByEmail(String email);

}
