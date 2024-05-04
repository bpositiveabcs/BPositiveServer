package bpos.server.repository.Interfaces;


import bpos.common.model.Institution;

public interface InstitutionRepository extends IRepository<Integer, Institution>{
    Iterable<Institution> findByName(String name);
    Iterable<Institution> findByAddress(String address);
    Iterable<Institution> findByEmail(String email);

}
