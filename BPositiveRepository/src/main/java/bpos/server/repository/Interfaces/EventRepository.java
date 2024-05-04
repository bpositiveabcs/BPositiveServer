package bpos.repository.Interfaces;

import bpos.model.Event;

import java.time.LocalDate;

public interface EventRepository extends IRepository<Integer, Event> {
    Iterable<Event> findByNume(String nume);
    Iterable<Event> findByDataAnunt(LocalDate data);
    Iterable<Event> findByCentruId(Integer centruId);
    Iterable<Event> findByDataInceput(LocalDate data);
}
