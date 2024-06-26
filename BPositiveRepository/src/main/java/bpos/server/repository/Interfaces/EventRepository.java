package bpos.server.repository.Interfaces;


import bpos.common.model.Event;
import bpos.common.model.Person;

import java.time.LocalDate;

public interface EventRepository extends IRepository<Integer, Event> {
    Iterable<Event> findByNume(String nume);
    Iterable<Event> findByDataAnunt(LocalDate data);
    Iterable<Event> findByCentruId(Integer centruId);
    Iterable<Event> findByDataInceput(LocalDate data);
    Iterable<Person> findParticipants(Integer eventId);
}
