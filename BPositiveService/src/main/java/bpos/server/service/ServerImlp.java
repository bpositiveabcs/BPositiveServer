package bpos.server.service;


import bpos.common.model.*;
import bpos.server.repository.Implementations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServerImlp implements IServiceImpl {

    private DBInstitutionRepository dbInstitution;

    private DBLogInfoRepository dbLogInfo;

    private DBMedicalInfoRepository dbMedicalInfo;

    private DBPersonRepository dbPerson;


    private DBPersonalDataRepository dbPersonalData;



    private DBRetrievedCouponsRepository dbRetrievedCoupons;


    private DBAddressRepository dbAddress;


    private DBBloodTestRepository dbBloodTest;


    private DBCenterRepository dbCenter;


    private DBCouponRepository dbCoupon;


    private DBDonationRepository dbDonation;


    private DBDonationTypeRepository dbDonationType;


    private DBStudentRepository dbStudent;


    private Map<Integer,IObserver> loggedClients;
    private Map<Integer,IObserver> loggedCenter;


    private DBEventRepository dbEvent;
    private ServerImlp(){}

    public ServerImlp(DBInstitutionRepository dbInstitution, DBLogInfoRepository dbLogInfo, DBMedicalInfoRepository dbMedicalInfo, DBPersonRepository dbPerson, DBPersonalDataRepository dbPersonalData, DBRetrievedCouponsRepository dbRetrievedCoupons, DBAddressRepository dbAddress, DBBloodTestRepository dbBloodTest, DBCenterRepository dbCenter, DBCouponRepository dbCoupon, DBDonationRepository dbDonation, DBDonationTypeRepository dbDonationType, DBEventRepository dbEvent, DBStudentRepository dbStudent) {
        this.dbInstitution = dbInstitution;
        this.dbLogInfo = dbLogInfo;
        this.dbMedicalInfo = dbMedicalInfo;
        this.dbPerson = dbPerson;
        this.dbPersonalData = dbPersonalData;
        this.dbRetrievedCoupons = dbRetrievedCoupons;
        this.dbAddress = dbAddress;
        this.dbBloodTest = dbBloodTest;
        this.dbCenter = dbCenter;
        this.dbCoupon = dbCoupon;
        this.dbDonation = dbDonation;
        this.dbDonationType = dbDonationType;
        this.dbEvent = dbEvent;
        this.dbStudent = dbStudent;
        this.loggedClients= new java.util.HashMap<>();
        this.loggedCenter=new java.util.HashMap<>();
    }

//    @Override
//    public Iterable<Address> findAllUtilitaryAddress(List<String> attributes, List<Object> values) throws ServicesExceptions {
//        return null;
//    }

    @Override
    public Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions {
        return dbAddress.findOne(integer);
    }

    @Override
    public Iterable<Address> findAllAddresses() throws ServicesExceptions {
        return dbAddress.findAll();
    }

    @Override
    public Optional<BloodTest> findOneBloodTest(Integer integer) throws ServicesExceptions {
        return dbBloodTest.findOne(integer);
    }


    @Override
    public Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions {
        return dbBloodTest.findAll();
    }


    @Override
    public Center findByUsernameCenter(String username) throws ServicesExceptions {
        return dbCenter.findByUsername(username);
    }

    @Override
    public Center findByEmailCenter(String email) throws ServicesExceptions {
        return dbCenter.findByEmail(email);
    }

    @Override
    public Iterable<Center> findByNameCenter(String name) throws ServicesExceptions {
        return dbCenter.findByName(name);
    }

    @Override
    public Optional<Center> findOneCenter(Integer integer) throws ServicesExceptions {
        return dbCenter.findOne(integer);
    }

    @Override
    public Iterable<Center> findAllCenters() throws ServicesExceptions {
        return dbCenter.findAll();
    }


    @Override
    public Iterable<Coupon> findByCodeCoupon(String code) throws ServicesExceptions {
        return dbCoupon.findByCodeCoupon(code);
    }

    @Override
    public Iterable<Coupon> findByProviderCoupon(String provider) throws ServicesExceptions {
        return dbCoupon.findByProvider(provider);
    }

    @Override
    public Iterable<Coupon> findByNameCoupon(String name) throws ServicesExceptions {
        return dbCoupon.findByNume(name);
    }

    @Override
    public Iterable<Coupon> findByEndDateCoupon(LocalDate endDate) throws ServicesExceptions {
        return dbCoupon.findByEndDate(endDate);
    }

    @Override
    public Optional<Coupon> findOneCoupon(Integer integer) throws ServicesExceptions {
        return dbCoupon.findOne(integer);
    }

    @Override
    public Iterable<Coupon> findAllCoupons() throws ServicesExceptions {
        return dbCoupon.findAll();
    }


    @Override
    public Iterable<Donation> findByTipDonation(String tipDonatie) throws ServicesExceptions {
        return dbDonation.findByTipDonatie(tipDonatie);
    }
    //merge
    @Override
    public Iterable<Donation> findByIdTipDonation(Integer idTipDonatie) throws ServicesExceptions {
        return dbDonation.findByIdTipDonatie(idTipDonatie);
    }

    @Override
    public Optional<Donation> findOneDonation(Integer integer) throws ServicesExceptions {
        return dbDonation.findOne(integer);
    }

    @Override
    public Iterable<Donation> findAllDonations() throws ServicesExceptions {
        return dbDonation.findAll();
    }



    @Override
    public Optional<DonationType> findOneDonationType(Integer integer) throws ServicesExceptions {
        return dbDonationType.findOne(integer);
    }

    @Override
    public Iterable<DonationType> findAllDonationType() throws ServicesExceptions {
        return dbDonationType.findAll();
    }


    @Override
    public Iterable<Event> findByNameEvent(String nume) throws ServicesExceptions {
        return dbEvent.findByNume(nume);
    }

    @Override
    public Iterable<Event> findByAnnouncementDateEvent(LocalDate data) throws ServicesExceptions {
        return dbEvent.findByDataAnunt(data);
    }

    @Override
    public Iterable<Event> findByCenterIdEvent(Integer centruId) throws ServicesExceptions {
        return dbEvent.findByCentruId(centruId);
    }

    @Override
    public Iterable<Event> findByDataInceputEvent(LocalDate data) throws ServicesExceptions {
        return dbEvent.findByDataInceput(data);
    }

    @Override
    public Optional<Event> findOneEvent(Integer integer) throws ServicesExceptions {
        return dbEvent.findOne(integer);
    }

    @Override
    public Iterable<Event> findAllEvents() throws ServicesExceptions {
        return dbEvent.findAll();
    }


    @Override
    public Optional<Institution> findOneInstitution(Integer integer) throws ServicesExceptions {
        return dbInstitution.findOne(integer);
    }

    @Override
    public Iterable<Institution> findAllInstitutions() throws ServicesExceptions {
        return dbInstitution.findAll();
    }

    @Override
    public Iterable<Institution> findByNameInstitution(String name) throws ServicesExceptions {
        return dbInstitution.findByName(name);
    }

    @Override
    public Iterable<Institution> findByAddressInstitution(String address) throws ServicesExceptions {
        return dbInstitution.findByAddress(address);
    }

    @Override
    public Iterable<Institution> findByEmailInstitution(String email) throws ServicesExceptions {
        return dbInstitution.findByEmail(email);
    }



    @Override
    public Optional<LogInfo> findOneLogInfo(Integer integer) throws ServicesExceptions {
        return dbLogInfo.findOne(integer);
    }

    @Override
    public Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions {
        return dbLogInfo.findAll();
    }

    @Override
    public LogInfo findByUsernameLogInfo(String username) throws ServicesExceptions {
        return dbLogInfo.findByUsername(username);
    }

    @Override
    public LogInfo findByEmailLogInfo(String email) throws ServicesExceptions {
        return dbLogInfo.findByEmail(email);
    }



    @Override
    public Optional<MedicalInfo> findOneMedicalInfo(Integer integer) throws ServicesExceptions {
        return dbMedicalInfo.findOne(integer);
    }

    @Override
    public Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions {
        return dbMedicalInfo.findAll();
    }

    @Override
    public Iterable<MedicalInfo> findByBloodTypeMedicalInfo(String bloodType) throws ServicesExceptions {
        return dbMedicalInfo.findByBloodType(bloodType);
    }

    @Override
    public Iterable<MedicalInfo> findByRhMedicalInfo(String rh) throws ServicesExceptions {
        return dbMedicalInfo.findByRh(rh);
    }

    @Override
    public Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(String bloodType, String rh) throws ServicesExceptions {
        return  dbMedicalInfo.findByBloodTypeAndRh(bloodType,rh);
    }

    @Override
    public Optional<PersonalData> findOnePersonalData(Integer integer) throws ServicesExceptions {
        return dbPersonalData.findOne(integer);
    }

    @Override
    public Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions {
        return dbPersonalData.findAll();
    }

    @Override
    public Iterable<PersonalData> findByFirstNamePersonalData(String firstName) throws ServicesExceptions {
        return dbPersonalData.findByFirstName(firstName);
    }

    @Override
    public Iterable<PersonalData> findByLastNamePersonalData(String lastName) throws ServicesExceptions {
        return dbPersonalData.findByLastName(lastName);
    }

    @Override
    public PersonalData findByCnpPersonalData(String cnp) throws ServicesExceptions {
        return dbPersonalData.findByCnp(cnp);
    }

    @Override
    public Optional<Person> findOnePerson(Integer integer) throws ServicesExceptions {
        return dbPerson.findOne(integer);
    }

    @Override
    public Iterable<Person> findAllPersons() throws ServicesExceptions {
        return dbPerson.findAll();
    }

    @Override
    public Iterable<Person> findByFirstNamePerson(String firstName) throws ServicesExceptions {
        return dbPerson.findByFirstName(firstName);
    }

    @Override
    public Iterable<Person> findByLastNamePerson(String lastName) throws ServicesExceptions {
        return dbPerson.findByLastName(lastName);
    }

    @Override
    public Iterable<Person> findByCnpPerson(String cnp) throws ServicesExceptions {
        return dbPerson.findByCnp(cnp);
    }

    @Override
    public Person findByEmailPerson(String email) throws ServicesExceptions {
        return dbPerson.findByEmail(email);
    }

    @Override
    public Iterable<Person> findByPhoneNumberPerson(String phoneNumber) throws ServicesExceptions {
        return dbPerson.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Person findByUsernamePerson(String username) throws ServicesExceptions {
        return dbPerson.findByUsername(username);
    }


    @Override
    public Optional<RetrievedCoupons> findOneRetrieved(Integer integer) throws ServicesExceptions {
        return dbRetrievedCoupons.findOne(integer);
    }

    @Override
    public Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions {
        return dbRetrievedCoupons.findAll();
    }

    @Override
    public Iterable<RetrievedCoupons> findByCouponIdRetrieved(Integer couponId) throws ServicesExceptions {
        return dbRetrievedCoupons.findByCouponId(couponId);
    }

    @Override
    public Iterable<RetrievedCoupons> findByPersonIdRetrieved(Integer personId) throws ServicesExceptions {
        return dbRetrievedCoupons.findByPersonId(personId);
    }

    @Override
    public Iterable<RetrievedCoupons> findByDateRetrieved(String date) throws ServicesExceptions {
        return dbRetrievedCoupons.findByDate(date);
    }

    @Override
    public Optional<Student> findOneStudent(Integer integer) throws ServicesExceptions {
        return dbStudent.findOne(integer);
    }

    @Override
    public Iterable<Student> findAllStudent() throws ServicesExceptions {
        return dbStudent.findAll();
    }

    @Override
    public Optional<Student> saveStudent(Student entity) throws ServicesExceptions {
        return dbStudent.save(entity);
    }

    @Override
    public Optional<Student> deleteStudent(Student entity) throws ServicesExceptions {
        return dbStudent.delete(entity);
    }

    @Override
    public Optional<Student> updateStudent(Student entity) throws ServicesExceptions {
        return dbStudent.update(entity);
    }

    @Override
    public Iterable<Student> findByFirstNameStudent(String firstName) throws ServicesExceptions {
        return dbStudent.findByFirstName(firstName);
    }

    @Override
    public Iterable<Student> findByLastNameStudent(String lastName) throws ServicesExceptions {
        return dbStudent.findByLastName(lastName);
    }

    @Override
    public Iterable<Student> findByCnpStudent(String cnp) throws ServicesExceptions {
        return dbStudent.findByCnp(cnp);
    }

    @Override
    public Student findByEmailStudent(String email) throws ServicesExceptions {
        return dbStudent.findByEmail(email);
    }

    @Override
    public Iterable<Student> findByPhoneNumberStudent(String phoneNumber) throws ServicesExceptions {
        return dbStudent.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Student findByUsernameStudent(String username) throws ServicesExceptions {
        return dbStudent.findByUsername(username);
    }

    @Override
    public  synchronized  Optional<Person> login(LogInfo logInfo, IObserver observer) throws ServicesExceptions {
        if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
        {
            throw new ServicesExceptions("Username does not exist");
        }
        Person person = dbPerson.findByUsername(logInfo.getUsername());
        //Person person1=dbPerson.findByEmail(logInfo.getEmail());
        if(person!=null /*&& person.equals(person1)*/){
            if(loggedClients.get(person.getId())!=null){
                throw new ServicesExceptions("User already logged in.");
            }
        }
        else{
                throw new ServicesExceptions("Authentication failed.");
        }
            loggedClients.put(person.getId(), observer);
        notifyTheOthersLogInPerson(person);
        return Optional.of(person);
    }

    private void notifyTheOthersLogInPerson(Person person) {
        Iterable<Person> personIterable=dbPerson.findAll();
        personIterable.forEach(person1 -> {
            IObserver client=loggedClients.get(person1.getId());
            if(client!=null){
                try {
                    client.loginPersonEvent(person);
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public synchronized Optional<Center> loginCenter(LogInfo logInfo , IObserver observer) throws ServicesExceptions {
        if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
        {
            throw new ServicesExceptions("Username does not exist");
        }
        Center center = dbCenter.findByUsername(logInfo.getUsername());
        Center center1=dbCenter.findByEmail(logInfo.getEmail());
        if(center!=null && center.equals(center1)) {
            if (loggedCenter.get(center.getId()) != null) {
                throw new ServicesExceptions("User already logged in.");
            }
        }else{
                throw new ServicesExceptions("Authentication failed.");
        }

        loggedCenter.put(center.getId(),observer);
        notifyLogInCenter(center);
        return Optional.of(center);
    }

    @Override
    public void logoutCenter(Center center, IObserver observer) throws ServicesExceptions {
        IObserver localClient=loggedCenter.remove(center.getId());
        if (localClient==null)
            throw new ServicesExceptions("User "+center+" is not logged in.");
        notifyLogOutCenter(center);
    }

    private void notifyLogOutCenter(Center center) {
        Iterable<Center> centerIterable=dbCenter.findAll();
        centerIterable.forEach(center1 -> {
            IObserver client=loggedCenter.get(center1.getId());
            if(client!=null){
                try {
                    client.logoutCenterEvent(center);
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void notifyLogInCenter(Center center) {
        Iterable<Center> centerIterable=dbCenter.findAll();
        centerIterable.forEach(center1 -> {
            IObserver client=loggedCenter.get(center1.getId());
            if(client!=null){
                try {
                    client.loginCenterEvent(center);
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public synchronized  void  logoutPerson(Person password, IObserver observer) throws ServicesExceptions {
        IObserver localClient=loggedClients.remove(password.getId());
        if (localClient==null)
            throw new ServicesExceptions("User "+password+" is not logged in.");
        notifyLogOutPerson(password);
    }

    @Override
    public void donationRegister(Donation donation, Person person, Event event) throws ServicesExceptions {
        Optional<Donation> donationOptional = dbDonation.save(donation);
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



    private void notifyLogOutPerson(Person password) {
        Iterable<Person> personIterable=dbPerson.findAll();
        personIterable.forEach(person -> {
            IObserver client=loggedClients.get(person.getId());
            if(client!=null){
                try {
                    client.logoutPersonEvent(person);
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public Optional<RetrievedCoupons> updateRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
        return dbRetrievedCoupons.update(entity);
    }

    @Override
    public Optional<RetrievedCoupons> deleteRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
        return  dbRetrievedCoupons.delete(entity);
    }

    @Override
    public Optional<RetrievedCoupons> saveRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
        return dbRetrievedCoupons.save(entity);
    }

    @Override
    public Optional<Person> updatePerson(Person entity) throws ServicesExceptions {
        return dbPerson.update(entity);
    }

    @Override
    public Optional<Person> deletePerson(Person entity) throws ServicesExceptions {
        return dbPerson.delete(entity);
    }

    @Override
    public Optional<Person> savePerson(Person entity) throws ServicesExceptions {
        return dbPerson.save(entity);
    }

    @Override
    public Optional<PersonalData> updatePersonalData(PersonalData entity) throws ServicesExceptions {

        return dbPersonalData.update(entity);
    }

    @Override
    public Optional<PersonalData> deletePersonalData(PersonalData entity) throws ServicesExceptions {
        return dbPersonalData.delete(entity);
    }

    @Override
    public Optional<PersonalData> savePersonalData(PersonalData entity) throws ServicesExceptions {
        return  dbPersonalData.save(entity);
    }

    @Override
    public Optional<MedicalInfo> updateMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
        return dbMedicalInfo.update(entity);
    }

    @Override
    public Optional<MedicalInfo> deleteMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
        return dbMedicalInfo.delete(entity);
    }

    @Override
    public Optional<MedicalInfo> saveMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
        return dbMedicalInfo.save(entity);
    }

    @Override
    public Optional<LogInfo> updateLogInfo(LogInfo entity) throws ServicesExceptions {
        return dbLogInfo.update(entity);
    }

    @Override
    public Optional<LogInfo> deleteLogInfo(LogInfo entity) throws ServicesExceptions {
        return dbLogInfo.delete(entity);
    }

    @Override
    public Optional<LogInfo> saveLogInfo(LogInfo entity) throws ServicesExceptions {
        return dbLogInfo.save(entity);
    }

    @Override
    public Optional<Institution> updateInstitution(Institution entity) throws ServicesExceptions {
        return dbInstitution.update(entity);
    }

    @Override
    public Optional<Institution> deleteInstitution(Institution entity) throws ServicesExceptions {
        return dbInstitution.delete(entity);
    }

    @Override
    public Optional<Institution> saveInstitution(Institution entity) throws ServicesExceptions {
        return dbInstitution.save(entity);
    }

    @Override
    public Optional<Event> updateEvent(Event entity) throws ServicesExceptions {
        return dbEvent.update(entity);
    }

    @Override
    public Optional<Event> deleteEvent(Event entity) throws ServicesExceptions {
        return dbEvent.delete(entity);
    }

    @Override
    public Optional<Event> saveEvent(Event entity) throws ServicesExceptions {
        Optional<Event> event = dbEvent.save(entity);
        event.ifPresent(this::notifyTheOthers);
        return event;
    }

    private void notifyTheOthers(Event event) {
            Iterable<Person> personIterable=dbPerson.findAll();
            Iterable<Center>personCenter=dbCenter.findAll();
            personIterable.forEach(person -> {
                IObserver client=loggedClients.get(person.getPersonLogInfo().getPassword());
                if(client!=null){
                    try {
                        client.eventHappened( event);
                    } catch (ServicesExceptions e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            personCenter.forEach(center -> {
                IObserver client=loggedClients.get(center.getLogInfo().getPassword());
                if(client!=null){
                    try {
                        client.eventHappened(event);
                    } catch (ServicesExceptions e) {
                        throw new RuntimeException(e);
                    }
                }
            });
    }

    @Override
    public Optional<DonationType> updateDonationType(DonationType entity) throws ServicesExceptions {
        return dbDonationType.update(entity);
    }

    @Override
    public Optional<DonationType> deleteDonationType(DonationType entity) throws ServicesExceptions {
        return dbDonationType.delete(entity);
    }

    @Override
    public Optional<DonationType> saveDonationType(DonationType entity) throws ServicesExceptions {
        return dbDonationType.save(entity);
    }

    @Override
    public Optional<Donation> updateDonation(Donation entity) throws ServicesExceptions {
        return dbDonation.update(entity);
    }

    @Override
    public Optional<Donation> deleteDonation(Donation entity) throws ServicesExceptions {
        return dbDonation.delete(entity);
    }

    @Override
    public Optional<Donation> saveDonation(Donation entity) throws ServicesExceptions {
        return dbDonation.save(entity);
    }

    @Override
    public Optional<Coupon> updateCoupon(Coupon entity) throws ServicesExceptions {
        return dbCoupon.update(entity);
    }

    @Override
    public Optional<Coupon> deleteCoupon(Coupon entity) throws ServicesExceptions {
        return dbCoupon.delete(entity);
    }

    @Override
    public Optional<Coupon> saveCoupon(Coupon entity) throws ServicesExceptions {
        return dbCoupon.save(entity);
    }

    @Override
    public Optional<Center> updateCenter(Center entity) throws ServicesExceptions {
        return dbCenter.update(entity);
    }

    @Override
    public Optional<Center> deleteCenter(Center entity) throws ServicesExceptions {
        return dbCenter.delete(entity);
    }

    @Override
    public Optional<Center> saveCenter(Center entity) throws ServicesExceptions {
        return dbCenter.save(entity);
    }

    @Override
    public Optional<BloodTest> updateBloodTest(BloodTest entity) throws ServicesExceptions {
        return dbBloodTest.update(entity);
    }

    @Override
    public Optional<BloodTest> deleteBloodTest(BloodTest entity) throws ServicesExceptions {
        return dbBloodTest.delete(entity);
    }

    @Override
    public Optional<BloodTest> saveBloodTest(BloodTest entity) throws ServicesExceptions {
        return dbBloodTest.save(entity);
    }

    @Override
    public Optional<Address> updateAddress(Address entity) throws ServicesExceptions {
        return dbAddress.update(entity );
    }

    @Override
    public Optional<Address> deleteAddress(Address entity) throws ServicesExceptions {
        return dbAddress.delete(entity);
    }

    @Override
    public Optional<Address> saveAddress(Address entity) throws ServicesExceptions {
        return dbAddress.save(entity);
    }

}
