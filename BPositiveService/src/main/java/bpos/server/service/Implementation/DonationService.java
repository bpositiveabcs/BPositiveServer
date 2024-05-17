package bpos.server.service.Implementation;

import bpos.common.model.Donation;
import bpos.common.model.DonationType;
import bpos.common.model.Event;
import bpos.common.model.Person;
import bpos.server.repository.Interfaces.DonationRepository;
import bpos.server.repository.Interfaces.DonationTypeRepository;
import bpos.server.repository.Interfaces.EventRepository;
import bpos.server.repository.Interfaces.PersonRepository;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.IDonationService;
import bpos.server.service.ServicesExceptions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DonationService implements IDonationService {
    private DonationRepository donationRepository;
    private DonationTypeRepository donationTypeRepository;
    private PersonRepository  dbPerson;
    private EventRepository dbEvent;
    private Map<Integer,IObserver> loggedClients;

    public DonationService(DonationRepository donationRepository, DonationTypeRepository donationTypeRepository, PersonRepository dbPerson, EventRepository dbEvent) {
        this.donationRepository = donationRepository;
        this.donationTypeRepository = donationTypeRepository;
        this.dbPerson = dbPerson;
        this.dbEvent = dbEvent;
    }

    @Override
    public Iterable<Donation> findByTipDonation(String tipDonation) throws ServicesExceptions {
        return donationRepository.findByTipDonatie(tipDonation);
    }

    @Override
    public Iterable<Donation> findByIdTipDonation(Integer tipDonation) throws ServicesExceptions {
        return donationRepository.findByIdTipDonatie(tipDonation);
    }

    @Override
    public Optional<Donation> findOneDonation(Integer integer) throws ServicesExceptions {
        return donationRepository.findOne(integer);
    }

    @Override
    public Iterable<Donation> findAllDonations() throws ServicesExceptions {
        return donationRepository.findAll();
    }

    @Override
    public Optional<Donation> saveDonation(Donation entity) throws ServicesExceptions {
        return donationRepository.save(entity);
    }

    @Override
    public Optional<Donation> deleteDonation(Donation entity) throws ServicesExceptions {
        return donationRepository.delete(entity);
    }

    @Override
    public Optional<Donation> updateDonation(Donation entity) throws ServicesExceptions {
        return donationRepository.update(entity);
    }

    @Override
    public Optional<DonationType> findOneDonationType(Integer integer) throws ServicesExceptions {
        return donationTypeRepository.findOne(integer);
    }

    @Override
    public Iterable<DonationType> findAllDonationType() throws ServicesExceptions {
        return donationTypeRepository.findAll();
    }

    @Override
    public Optional<DonationType> saveDonationType(DonationType entity) throws ServicesExceptions {
        return donationTypeRepository.save(entity);
    }

    @Override
    public Optional<DonationType> deleteDonationType(DonationType entity) throws ServicesExceptions {
        return donationTypeRepository.delete(entity);
    }

    @Override
    public Optional<DonationType> updateDonationType(DonationType entity) throws ServicesExceptions {
        return donationTypeRepository.update(entity);
    }

    @Override
    public void donationRegister(Donation donation, Person person, Event event) throws ServicesExceptions {
        Optional<Donation> donationOptional = donationRepository.save(donation);
        List<Donation> donationArrayList=person.getDonations();
        donationArrayList.add(donation);
        // List<Event> eventArrayList=person.get();
        person.setDonations(donationArrayList);
        Optional<Person> personOptional = dbPerson.update(person);
        event.setMaxParticipants(event.getMaxParticipants()+1);
        Optional<Event> eventOptional = dbEvent.update(event);
        if(personOptional.isPresent() && eventOptional.isPresent()&& donationOptional.isPresent()){
            notifyDonationRegistered(donationOptional);

        }
        else{
            throw new ServicesExceptions("Donation could not be registered");
        }
    }
    private void notifyDonationRegistered(Optional<Donation> donationOptional) {
        Iterable<Person> personIterable=dbPerson.findAll();
        personIterable.forEach(person -> {
            IObserver client=loggedClients.get(person.getId());
            if(client!=null){
                try {
                    client.donationRegistered(donationOptional.get());
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
