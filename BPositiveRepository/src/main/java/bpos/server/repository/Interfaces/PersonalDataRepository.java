package bpos.repository.Interfaces;

import bpos.model.PersonalData;

public interface PersonalDataRepository extends IRepository<Integer, PersonalData>
{
    Iterable<PersonalData> findByFirstName(String firstName);
    Iterable<PersonalData> findByLastName(String lastName);
    PersonalData findByCnp(String cnp);

}
