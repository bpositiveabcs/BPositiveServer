package bpos.server.service;

import bpos.common.model.Center;
import bpos.common.model.Donation;
import bpos.common.model.Event;
import bpos.common.model.Person;

public interface IObserver {
    void loginPersonEvent(Person password) throws ServicesExceptions;
    void logoutPersonEvent(Person password) throws ServicesExceptions;
    void loginCenterEvent(Center password) throws ServicesExceptions;
    void logoutCenterEvent(Center password) throws ServicesExceptions;
    void eventHappened(Event event) throws ServicesExceptions;
    void donationRegistered(Donation donation) throws ServicesExceptions;
}
