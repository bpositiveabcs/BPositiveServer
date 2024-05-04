package bpos.server.repository.Interfaces;


import bpos.common.model.Center;

public interface CenterRepository extends IRepository<Integer, Center> {
    Center findByUsername(String username);
    Center findByEmail(String email);

    Iterable<Center> findByName(String name);
}
