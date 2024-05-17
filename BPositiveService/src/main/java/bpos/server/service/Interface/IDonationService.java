package bpos.server.service.Interface;

import bpos.common.model.Donation;
import bpos.common.model.DonationType;
import bpos.common.model.Event;
import bpos.common.model.Person;
import bpos.server.service.ServicesExceptions;

import java.util.Optional;

public interface IDonationService {
    Iterable<Donation> findByTipDonation(String tipDonation) throws ServicesExceptions;
    Iterable<Donation> findByIdTipDonation(Integer tipDonation) throws ServicesExceptions;
    Optional<Donation> findOneDonation(Integer integer) throws ServicesExceptions;
    Iterable<Donation> findAllDonations() throws ServicesExceptions;
    Optional<Donation> saveDonation(Donation entity) throws ServicesExceptions;
    Optional<Donation> deleteDonation(Donation entity) throws ServicesExceptions;
    Optional<Donation> updateDonation(Donation entity) throws ServicesExceptions;
    //donationType
    Optional<DonationType> findOneDonationType(Integer integer) throws ServicesExceptions;
    Iterable<DonationType> findAllDonationType() throws ServicesExceptions;
    Optional<DonationType> saveDonationType(DonationType entity) throws ServicesExceptions;
    Optional<DonationType> deleteDonationType(DonationType entity) throws ServicesExceptions;
    Optional<DonationType> updateDonationType(DonationType entity) throws ServicesExceptions;
    void donationRegister(Donation donation, Person person, Event event) throws ServicesExceptions;


}
