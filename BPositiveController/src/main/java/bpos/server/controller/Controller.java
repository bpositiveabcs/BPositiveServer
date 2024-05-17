package bpos.server.controller;
import bpos.common.model.*;
import bpos.server.service.ServicesExceptions;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
@RequestMapping("/api")
@CrossOrigin
//@RestController
public class Controller {
//    private final IServiceImpl service;
//    public Controller(IServiceImpl service) {
//        this.service = service;
//    }
//
//    public Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions {
//        return service.findOneAddress(integer);
//    }
//
//    @GetMapping("/getAllAddresses")
//    public Iterable<Address> findAllAddresses() throws ServicesExceptions {
//        return service.findAllAddresses();
//    }
//    @GetMapping("/getAllPersons")
//    public Iterable<Person> findAllPersons() throws ServicesExceptions {
//        System.out.println("getAllPersons");
//        return service.findAllPersons();
//    }
//    @GetMapping("/findOneBloodTest")
//    public Optional<BloodTest> findOneBloodTest(@RequestParam(value="id_blood_test",required = true)Integer integer) throws ServicesExceptions {
//        return service.findOneBloodTest(integer);
//    }
//    public Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions {
//        return service.findAllBloodTest();
//    }
//
//
//
//    public Center findByUsernameCenter(String username) throws ServicesExceptions {
//        return service.findByUsernameCenter(username);
//    }
//
//
//    public Center findByEmailCenter(String email) throws ServicesExceptions {
//        return service.findByEmailCenter(email);
//    }
//
//
//        public Iterable<Center> findByNameCenter(String name) throws ServicesExceptions {
//            return service.findByNameCenter(name);
//    }
//
//
//        public Optional<Center> findOneCenter(Integer integer) throws ServicesExceptions {
//            return service.findOneCenter(integer);
//        }
//
//        @GetMapping("/findAllCenters")
//        public Iterable<Center> findAllCenters() throws ServicesExceptions {
//            return service.findAllCenters();
//        }
//
//
//
//        public Iterable<Coupon> findByCodeCoupon(String code) throws ServicesExceptions {
//            return service.findByCodeCoupon(code);
//        }
//
//
//        public Iterable<Coupon> findByProviderCoupon(String provider) throws ServicesExceptions {
//            return service.findByProviderCoupon(provider);
//        }
//
//
//        public Iterable<Coupon> findByNameCoupon(String name) throws ServicesExceptions {
//            return service.findByNameCoupon(name);
//        }
//
//
//        public Iterable<Coupon> findByEndDateCoupon(LocalDate endDate) throws ServicesExceptions {
//            return service.findByEndDateCoupon(endDate);
//        }
//
//
//        public Optional<Coupon> findOneCoupon(Integer integer) throws ServicesExceptions {
//            return service.findOneCoupon(integer);
//        }
//
//
//        public Iterable<Coupon> findAllCoupons() throws ServicesExceptions {
//            return service.findAllCoupons();
//        }
//
//
//
//        public Iterable<Donation> findByTipDonation(String tipDonatie) throws ServicesExceptions {
//            return service.findByTipDonation(tipDonatie);
//        }
//
//
//        public Iterable<Donation> findByIdTipDonation(Integer idTipDonatie) throws ServicesExceptions {
//            return service.findByIdTipDonation(idTipDonatie);
//        }
//
//
//        public Optional<Donation> findOneDonation(Integer integer) throws ServicesExceptions {
//            return service.findOneDonation(integer);
//        }
//
//
//        public Iterable<Donation> findAllDonations() throws ServicesExceptions {
//            return service.findAllDonations();
//        }
//
//
//
//
//        public Optional<DonationType> findOneDonationType(Integer integer) throws ServicesExceptions {
//            return service.findOneDonationType(integer);
//        }
//
//
//        public Iterable<DonationType> findAllDonationType() throws ServicesExceptions {
//            return service.findAllDonationType();
//        }
//
//
//
//        public Iterable<Event> findByNameEvent(String nume) throws ServicesExceptions {
//            return service.findByNameEvent(nume);
//        }
//
//
//        public Iterable<Event> findByAnnouncementDateEvent(LocalDate data) throws ServicesExceptions {
//            return service.findByAnnouncementDateEvent(data);
//        }
//
//
//        public Iterable<Event> findByCenterIdEvent(Integer centruId) throws ServicesExceptions {
//            return service.findByCenterIdEvent(centruId);
//        }
//
//
//        public Iterable<Event> findByDataInceputEvent(LocalDate data) throws ServicesExceptions {
//            return service.findByDataInceputEvent(data);
//        }
//
//
//        public Optional<Event> findOneEvent(Integer integer) throws ServicesExceptions {
//            return service.findOneEvent(integer);
//        }
//
//
//        public Iterable<Event> findAllEvents() throws ServicesExceptions {
//            return service.findAllEvents();
//        }
//
//
//
//        public Optional<Institution> findOneInstitution(Integer integer) throws ServicesExceptions {
//            return service.findOneInstitution(integer);
//        }
//
//
//        public Iterable<Institution> findAllInstitutions() throws ServicesExceptions {
//            return service.findAllInstitutions();
//        }
//
//
//        public Iterable<Institution> findByNameInstitution(String name) throws ServicesExceptions {
//            return service.findByNameInstitution(name);
//        }
//
//
//        public Iterable<Institution> findByAddressInstitution(String address) throws ServicesExceptions {
//            return service.findByAddressInstitution(address);
//        }
//
//
//        public Iterable<Institution> findByEmailInstitution(String email) throws ServicesExceptions {
//            return service.findByEmailInstitution(email);
//        }
//
//
//
//
//        public Optional<LogInfo> findOneLogInfo(Integer integer) throws ServicesExceptions {
//            return service.findOneLogInfo(integer);
//        }
//
//
//        public Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions {
//            return service.findAllLogInfos();
//        }
//
//
//        public LogInfo findByUsernameLogInfo(String username) throws ServicesExceptions {
//            return service.findByUsernameLogInfo(username);
//        }
//
//
//        public LogInfo findByEmailLogInfo(String email) throws ServicesExceptions {
//            return service.findByEmailLogInfo(email);
//        }
//
//
//
//
//        public Optional<MedicalInfo> findOneMedicalInfo(Integer integer) throws ServicesExceptions {
//            return service.findOneMedicalInfo(integer);
//        }
//
//
//        public Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions {
//            return service.findAllMedicalInfos();
//        }
//
//
//        public Iterable<MedicalInfo> findByBloodTypeMedicalInfo(String bloodType) throws ServicesExceptions {
//            return service.findByBloodTypeMedicalInfo(bloodType);
//        }
//
//
//        public Iterable<MedicalInfo> findByRhMedicalInfo(String rh) throws ServicesExceptions {
//            return service.findByRhMedicalInfo(rh);
//        }
//
//
//        public Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(String bloodType, String rh) throws ServicesExceptions {
//            return service.findByBloodTypeAndRhMedicalInfo(bloodType, rh);
//        }
//
//
//        public Optional<PersonalData> findOnePersonalData(Integer integer) throws ServicesExceptions {
//            return service.findOnePersonalData(integer);
//        }
//
//
//        public Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions {
//            return service.findAllPersonalDatas();
//        }
//
//
//        public Iterable<PersonalData> findByFirstNamePersonalData(String firstName) throws ServicesExceptions {
//            return service.findByFirstNamePersonalData(firstName);
//        }
//
//
//        public Iterable<PersonalData> findByLastNamePersonalData(String lastName) throws ServicesExceptions {
//            return service.findByLastNamePersonalData(lastName);
//        }
//
//
//        public PersonalData findByCnpPersonalData(String cnp) throws ServicesExceptions {
//            return service.findByCnpPersonalData(cnp);
//        }
//
//
//        public Optional<Person> findOnePerson(Integer integer) throws ServicesExceptions {
//            return service.findOnePerson(integer);
//        }
//
//
//
//
//        public Iterable<Person> findByFirstNamePerson(String firstName) throws ServicesExceptions {
//            return service.findByFirstNamePerson(firstName);
//        }
//
//
//        public Iterable<Person> findByLastNamePerson(String lastName) throws ServicesExceptions {
//            return service.findByLastNamePerson(lastName);
//        }
//
//
//        public Iterable<Person> findByCnpPerson(String cnp) throws ServicesExceptions {
//            return service.findByCnpPerson(cnp);
//        }
//
//
//        public Person findByEmailPerson(String email) throws ServicesExceptions {
//            return service.findByEmailPerson(email);
//        }
//
//
//        public Iterable<Person> findByPhoneNumberPerson(String phoneNumber) throws ServicesExceptions {
//            return service.findByPhoneNumberPerson(phoneNumber);
//        }
//
//
//        public Person findByUsernamePerson(String username) throws ServicesExceptions {
//            return service.findByUsernamePerson(username);
//        }
//
//
//
//        public Optional<RetrievedCoupons> findOneRetrieved(Integer integer) throws ServicesExceptions {
//            return service.findOneRetrieved(integer);
//        }
//
//
//        public Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions {
//            return service.findAllRetrieved();
//        }
//
//
//        public Iterable<RetrievedCoupons> findByCouponIdRetrieved(Integer couponId) throws ServicesExceptions {
//            return service.findByCouponIdRetrieved(couponId);
//        }
//
//
//        public Iterable<RetrievedCoupons> findByPersonIdRetrieved(Integer personId) throws ServicesExceptions {
//            return service.findByPersonIdRetrieved(personId);
//        }
//
//
//        public Iterable<RetrievedCoupons> findByDateRetrieved(String date) throws ServicesExceptions {
//            return service.findByDateRetrieved(date);
//        }
//
//
//        public Optional<Student> findOneStudent(Integer integer) throws ServicesExceptions {
//            return service.findOneStudent(integer);
//        }
//
//
//        public Iterable<Student> findAllStudent() throws ServicesExceptions {
//            return service.findAllStudent();
//        }
//
//
//        public Optional<Student> saveStudent(Student entity) throws ServicesExceptions {
//            return service.saveStudent(entity);
//        }
//
//
//        public Optional<Student> deleteStudent(Student entity) throws ServicesExceptions {
//            return service.deleteStudent(entity);
//        }
//
//
//        public Optional<Student> updateStudent(Student entity) throws ServicesExceptions {
//            return service.updateStudent(entity);
//        }
//
//
//        public Iterable<Student> findByFirstNameStudent(String firstName) throws ServicesExceptions {
//            return service.findByFirstNameStudent(firstName);
//        }
//
//
//        public Iterable<Student> findByLastNameStudent(String lastName) throws ServicesExceptions {
//            return service.findByLastNameStudent(lastName);
//        }
//
//
//        public Iterable<Student> findByCnpStudent(String cnp) throws ServicesExceptions {
//            return service.findByCnpStudent(cnp);
//        }
//
//
//        public Student findByEmailStudent(String email) throws ServicesExceptions {
//            return service.findByEmailStudent(email);
//        }
//
//
//        public Iterable<Student> findByPhoneNumberStudent(String phoneNumber) throws ServicesExceptions {
//            return service.findByPhoneNumberStudent(phoneNumber);
//        }
//
//
//        public Student findByUsernameStudent(String username) throws ServicesExceptions {
//            return service.findByUsernameStudent(username);
//        }

//
//        public  synchronized  Optional<Person> login(LogInfo logInfo, IObserver observer) throws ServicesExceptions {
//            if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
//            {
//                throw new ServicesExceptions("Username does not exist");
//            }
//            Person person = dbPerson.findByUsername(logInfo.getUsername());
//            //Person person1=dbPerson.findByEmail(logInfo.getEmail());
//            if(person!=null /*&& person.equals(person1)*/){
//                if(loggedClients.get(person.getId())!=null){
//                    throw new ServicesExceptions("User already logged in.");
//                }
//            }
//            else{
//                throw new ServicesExceptions("Authentication failed.");
//            }
//            loggedClients.put(person.getId(), observer);
//            notifyTheOthersLogInPerson(person);
//            return Optional.of(person);
//        }
//
//
//
//
//        public synchronized Optional<Center> loginCenter(LogInfo logInfo , IObserver observer) {
//            if(dbLogInfo.findByUsername(logInfo.getUsername())==null)
//            {
//                throw new ServicesExceptions("Username does not exist");
//            }
//            Center center = dbCenter.findByUsername(logInfo.getUsername());
//            Center center1=dbCenter.findByEmail(logInfo.getEmail());
//            if(center!=null && center.equals(center1)) {
//                if (loggedCenter.get(center.getId()) != null) {
//                    throw new ServicesExceptions("User already logged in.");
//                }
//            }else{
//                throw new ServicesExceptions("Authentication failed.");
//            }
//
//            loggedCenter.put(center.getId(),observer);
//            notifyLogInCenter(center);
//            return Optional.of(center);
//        }
//
//
//        public void logoutCenter(Center center, IObserver observer)  {
//            IObserver localClient=loggedCenter.remove(center.getId());
//            if (localClient==null)
//                throw new ServicesExceptions("User "+center+" is not logged in.");
//            notifyLogOutCenter(center);
//        }
//
//
//
//
//
//        public synchronized  void  logoutPerson(Person password, IObserver observer)  {
//            IObserver localClient=loggedClients.remove(password.getId());
//            if (localClient==null)
//                throw new ServicesExceptions("User "+password+" is not logged in.");
//            notifyLogOutPerson(password);
//        }
//
//
//        public void donationRegister(Donation donation, Person person, Event event) {
//            service.donationRegister(donation,person,event);
//        }
//
//
//
//
//
//
//
//        public Optional<RetrievedCoupons> updateRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
//            return dbRetrievedCoupons.update(entity);
//        }
//
//
//        public Optional<RetrievedCoupons> deleteRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
//            return  dbRetrievedCoupons.delete(entity);
//        }
//
//
//        public Optional<RetrievedCoupons> saveRetrieved(RetrievedCoupons entity) throws ServicesExceptions {
//            return dbRetrievedCoupons.save(entity);
//        }
//
//
//        public Optional<Person> updatePerson(Person entity) throws ServicesExceptions {
//            return service.updatePerson(entity);
//        }
//
//
//        public Optional<Person> deletePerson(Person entity) throws ServicesExceptions {
//
//        }
//
//
//        public Optional<Person> savePerson(Person entity) throws ServicesExceptions {
//            return dbPerson.save(entity);
//        }
//
//
//        public Optional<PersonalData> updatePersonalData(PersonalData entity) throws ServicesExceptions {
//
//            return dbPersonalData.update(entity);
//        }
//
//
//        public Optional<PersonalData> deletePersonalData(PersonalData entity) throws ServicesExceptions {
//            return dbPersonalData.delete(entity);
//        }
//
//
//        public Optional<PersonalData> savePersonalData(PersonalData entity) throws ServicesExceptions {
//            return  dbPersonalData.save(entity);
//        }
//
//
//        public Optional<MedicalInfo> updateMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
//            return dbMedicalInfo.update(entity);
//        }
//
//
//        public Optional<MedicalInfo> deleteMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
//            return dbMedicalInfo.delete(entity);
//        }
//
//
//        public Optional<MedicalInfo> saveMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
//            return dbMedicalInfo.save(entity);
//        }
//
//
//        public Optional<LogInfo> updateLogInfo(LogInfo entity) throws ServicesExceptions {
//            return dbLogInfo.update(entity);
//        }
//
//
//        public Optional<LogInfo> deleteLogInfo(LogInfo entity) throws ServicesExceptions {
//            return dbLogInfo.delete(entity);
//        }
//
//
//        public Optional<LogInfo> saveLogInfo(LogInfo entity) throws ServicesExceptions {
//            return dbLogInfo.save(entity);
//        }
//
//
//        public Optional<Institution> updateInstitution(Institution entity) throws ServicesExceptions {
//            return dbInstitution.update(entity);
//        }
//
//
//        public Optional<Institution> deleteInstitution(Institution entity) throws ServicesExceptions {
//            return dbInstitution.delete(entity);
//        }
//
//
//        public Optional<Institution> saveInstitution(Institution entity) throws ServicesExceptions {
//            return dbInstitution.save(entity);
//        }
//
//
//        public Optional<Event> updateEvent(Event entity) throws ServicesExceptions {
//            return dbEvent.update(entity);
//        }
//
//
//        public Optional<Event> deleteEvent(Event entity) throws ServicesExceptions {
//            return dbEvent.delete(entity);
//        }
//
//
//        public Optional<Event> saveEvent(Event entity) throws ServicesExceptions {
//            Optional<Event> event = dbEvent.save(entity);
//            event.ifPresent(this::notifyTheOthers);
//            return event;
//        }
//
//        private void notifyTheOthers(Event event) {
//            Iterable<Person> personIterable=dbPerson.findAll();
//            Iterable<Center>personCenter=dbCenter.findAll();
//            personIterable.forEach(person -> {
//                IObserver client=loggedClients.get(person.getPersonLogInfo().getPassword());
//                if(client!=null){
//                    try {
//                        client.eventHappened( event);
//                    } catch (ServicesExceptions e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//            personCenter.forEach(center -> {
//                IObserver client=loggedClients.get(center.getLogInfo().getPassword());
//                if(client!=null){
//                    try {
//                        client.eventHappened(event);
//                    } catch (ServicesExceptions e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//        }
//
//
//        public Optional<DonationType> updateDonationType(DonationType entity) throws ServicesExceptions {
//            return dbDonationType.update(entity);
//        }
//
//
//        public Optional<DonationType> deleteDonationType(DonationType entity) throws ServicesExceptions {
//            return dbDonationType.delete(entity);
//        }
//
//
//        public Optional<DonationType> saveDonationType(DonationType entity) throws ServicesExceptions {
//            return dbDonationType.save(entity);
//        }
//
//
//        public Optional<Donation> updateDonation(Donation entity) throws ServicesExceptions {
//            return dbDonation.update(entity);
//        }
//
//
//        public Optional<Donation> deleteDonation(Donation entity) throws ServicesExceptions {
//            return dbDonation.delete(entity);
//        }
//
//
//        public Optional<Donation> saveDonation(Donation entity) throws ServicesExceptions {
//            return dbDonation.save(entity);
//        }
//
//
//        public Optional<Coupon> updateCoupon(Coupon entity) throws ServicesExceptions {
//            return dbCoupon.update(entity);
//        }
//
//
//        public Optional<Coupon> deleteCoupon(Coupon entity) throws ServicesExceptions {
//            return dbCoupon.delete(entity);
//        }
//
//
//        public Optional<Coupon> saveCoupon(Coupon entity) throws ServicesExceptions {
//            return dbCoupon.save(entity);
//        }
//
//
//        public Optional<Center> updateCenter(Center entity) throws ServicesExceptions {
//            return dbCenter.update(entity);
//        }
//
//
//        public Optional<Center> deleteCenter(Center entity) throws ServicesExceptions {
//            return dbCenter.delete(entity);
//        }
//
//
//        public Optional<Center> saveCenter(Center entity) throws ServicesExceptions {
//            return dbCenter.save(entity);
//        }
//
//
//        public Optional<BloodTest> updateBloodTest(BloodTest entity) throws ServicesExceptions {
//            return dbBloodTest.update(entity);
//        }
//
//
//        public Optional<BloodTest> deleteBloodTest(BloodTest entity) throws ServicesExceptions {
//            return dbBloodTest.delete(entity);
//        }
//
//
//        public Optional<BloodTest> saveBloodTest(BloodTest entity) throws ServicesExceptions {
//            return dbBloodTest.save(entity);
//        }
//
//
//        public Optional<Address> updateAddress(Address entity) throws ServicesExceptions {
//            return dbAddress.update(entity );
//        }
//
//
//        public Optional<Address> deleteAddress(Address entity) throws ServicesExceptions {
//            return dbAddress.delete(entity);
//        }
//
//
//        public Optional<Address> saveAddress(Address entity) throws ServicesExceptions {
//            return dbAddress.save(entity);
//        }



}
