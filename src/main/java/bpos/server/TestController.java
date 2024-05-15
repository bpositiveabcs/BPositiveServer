package bpos.server;

import bpos.common.model.*;
import bpos.server.service.IServiceImpl;
import bpos.server.service.ServicesExceptions;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RequestMapping("/api/test")
@CrossOrigin
@RestController
public class TestController {
    private  IServiceImpl service;
    public TestController(IServiceImpl service) {
        this.service = service;
    }

    public Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions {
        return service.findOneAddress(integer);
    }

    @GetMapping("/getAllAddresses")
    public Iterable<Address> findAllAddresses() throws ServicesExceptions {
        return service.findAllAddresses();
    }
    //merge
    @GetMapping("/getAllPersons")
    public Iterable<Person> findAllPersons() throws ServicesExceptions {
        System.out.println("getAllPersons");
        return service.findAllPersons();
    }
    @GetMapping("/findOneBloodTest")
    public Optional<BloodTest> findOneBloodTest(@RequestParam(value="id_blood_test",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneBloodTest(integer);
    }
    //asta nu merge
    @GetMapping("/findAllBloodTest")
    public Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions {
        return service.findAllBloodTest();
    }

    //asta merge
    @GetMapping("/findByUsernameCenter")
    public Center findByUsernameCenter(@RequestParam(value="username",required = true) String username) throws ServicesExceptions {
        return service.findByUsernameCenter(username);
    }
    //asta merge
    @GetMapping("/findByEmailCenter")
    public Center findByEmailCenter(@RequestParam(value="email",required = true) String email) throws ServicesExceptions {
        return service.findByEmailCenter(email);
    }
//merge
    @GetMapping("/findByNameCenter")
    public Iterable<Center> findByNameCenter(@RequestParam(value="name",required = true) String name) throws ServicesExceptions {
        return service.findByNameCenter(name);
    }
//merge
    @GetMapping("/findOneCenter")
    public Optional<Center> findOneCenter(@RequestParam(value="id_center",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneCenter(integer);
    }
    //merge
    @GetMapping("/findAllCenters")
    public Iterable<Center> findAllCenters() throws ServicesExceptions {
        return service.findAllCenters();
    }
    //merge
    @GetMapping("/findByCodeCoupon")
    public Iterable<Coupon> findByCodeCoupon(@RequestParam(value="code_coupon",required = true) String code) throws ServicesExceptions {
        return service.findByCodeCoupon(code);
    }
    //merge
    @GetMapping("/findByProviderCoupon")
    public Iterable<Coupon> findByProviderCoupon(@RequestParam(value="provider",required = true) String provider) throws ServicesExceptions {
        return service.findByProviderCoupon(provider);
    }
    //merge
    @GetMapping("/findByNameCoupon")
    public Iterable<Coupon> findByNameCoupon(@RequestParam(value = "name",required = true) String name) throws ServicesExceptions {
        return service.findByNameCoupon(name);
    }
    //asta merge nush daca am dat eu val buna
    @GetMapping("/findByEndDateCoupon")
    public Iterable<Coupon> findByEndDateCoupon(@RequestParam(value="endDate",required = true) LocalDate endDate) throws ServicesExceptions {
        return service.findByEndDateCoupon(endDate);
    }
    //merge
    @GetMapping("/findOneCoupon")
    public Optional<Coupon> findOneCoupon(@RequestParam(value="id_coupon",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneCoupon(integer);
    }
    //merge
    @GetMapping("/findAllCoupons")
    public Iterable<Coupon> findAllCoupons() throws ServicesExceptions {
        return service.findAllCoupons();
    }

    //merge
    @GetMapping("/findByTipDonation")
    public Iterable<Donation> findByTipDonation(@RequestParam(value = "tip_donatie",required = true) String tipDonatie) throws ServicesExceptions {
        return service.findByTipDonation(tipDonatie);
    }
    //merge
    @GetMapping("/findByIdTipDonation")
    public Iterable<Donation> findByIdTipDonation(@RequestParam(value = "id_type_donation",required = true) Integer idTipDonatie) throws ServicesExceptions {
        return service.findByIdTipDonation(idTipDonatie);
    }
    //merge
    @GetMapping("/findOneDonation")
    public Optional<Donation> findOneDonation(@RequestParam(value="id_donation",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneDonation(integer);
    }
    //merge
    @GetMapping("/findAllDonations")
    public Iterable<Donation> findAllDonations() throws ServicesExceptions {
        return service.findAllDonations();
    }
    //merge
    @GetMapping("/findOneDonationType")
    public Optional<DonationType> findOneDonationType(@RequestParam(value = "id_donation_type",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneDonationType(integer);
    }
    //merge
    @GetMapping("/findAllDonationType")
    public Iterable<DonationType> findAllDonationType() throws ServicesExceptions {
        return service.findAllDonationType();
    }
    //merge

    @GetMapping("/findByNameEvent")
    public Iterable<Event> findByNameEvent(@RequestParam(value = "name_event",required = true) String nume) throws ServicesExceptions {
        return service.findByNameEvent(nume);
    }
    //merge
    @GetMapping("/findByAnnouncementDateEvent")
    public Iterable<Event> findByAnnouncementDateEvent(@RequestParam(value = "announcement_date_event",required = true) LocalDate data) throws ServicesExceptions {
        return service.findByAnnouncementDateEvent(data);
    }
    //merge
    @GetMapping("/findByCenterIdEvent")
    public Iterable<Event> findByCenterIdEvent(@RequestParam(value="centruID",required = true) Integer centruId) throws ServicesExceptions {
        return service.findByCenterIdEvent(centruId);
    }
    //cred ca nush sa dau eu stringul bun pentru data
    //requestul merge
    @GetMapping("/findByDataInceputEvent")
    public Iterable<Event> findByDataInceputEvent(@RequestParam(value = "date_start",required = true) LocalDate data) throws ServicesExceptions {
        return service.findByDataInceputEvent(data);
    }
    //merge
    @GetMapping("/findOneEvent")
    public Optional<Event> findOneEvent(@RequestParam(value="id_event",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneEvent(integer);
    }
    //merge
    @GetMapping("/findAllEvents")
    public Iterable<Event> findAllEvents() throws ServicesExceptions {
        return service.findAllEvents();
    }

    //merge
    @GetMapping("/findOneInstitution")
    public Optional<Institution> findOneInstitution(@RequestParam(value="id_institution",required = true) Integer integer) throws ServicesExceptions {
        return service.findOneInstitution(integer);
    }
    //merge
    @GetMapping("/findAllInstitutions")
    public Iterable<Institution> findAllInstitutions() throws ServicesExceptions {
        return service.findAllInstitutions();
    }
    //merge
    @GetMapping("/findByNameInstitution")
    public Iterable<Institution> findByNameInstitution(@RequestParam(value="name_institution",required = true)String name) throws ServicesExceptions {
        return service.findByNameInstitution(name);
    }
    //merge
    @GetMapping("/findByAddressInstitution")
    public Iterable<Institution> findByAddressInstitution(@RequestParam(value="address_institution",required = true)String address) throws ServicesExceptions {
        return service.findByAddressInstitution(address);
    }
    //merge
    @GetMapping("/findByEmailInstitution")
    public Iterable<Institution> findByEmailInstitution(@RequestParam(value="email",required = true) String email) throws ServicesExceptions {
        return service.findByEmailInstitution(email);
    }


    //merge
    @GetMapping("/findOneLogInfo")
    public Optional<LogInfo> findOneLogInfo(@RequestParam(value="id_log_info",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneLogInfo(integer);
    }
    //merge
    @GetMapping("/findAllLogInfos")
    public Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions {
        return service.findAllLogInfos();
    }
    //merge
    @GetMapping("/findByUsernameLogInfo")
    public LogInfo findByUsernameLogInfo(@RequestParam(value="username",required = true)String username) throws ServicesExceptions {
        return service.findByUsernameLogInfo(username);
    }
    //merge
    @GetMapping("/findByEmailLogInfo")
    public LogInfo findByEmailLogInfo(@RequestParam(value="email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailLogInfo(email);
    }


    //merge
    @GetMapping("/findOneMedicalInfo")
    public Optional<MedicalInfo> findOneMedicalInfo(@RequestParam(value="id_medical_info",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneMedicalInfo(integer);
    }
    //merge
    @GetMapping("/findAllMedicalInfos")
    public Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions {
        return service.findAllMedicalInfos();
    }
    //merge
    @GetMapping("/findByBloodTypeMedicalInfo")
    public Iterable<MedicalInfo> findByBloodTypeMedicalInfo(@RequestParam(value="blood_type",required = true)String bloodType) throws ServicesExceptions {
        return service.findByBloodTypeMedicalInfo(bloodType);
    }
    //merge
    @GetMapping("/findByRhMedicalInfo")
    public Iterable<MedicalInfo> findByRhMedicalInfo(@RequestParam(value="rh",required = true)String rh) throws ServicesExceptions {
        return service.findByRhMedicalInfo(rh);
    }

    @GetMapping("/findByBloodTypeAndRhMedicalInfo")
    public Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(@RequestParam(value="bloodType",required = true)String bloodType,@RequestParam(value="rh",required = true) String rh) throws ServicesExceptions {
        return service.findByBloodTypeAndRhMedicalInfo(bloodType, rh);
    }
    //merge
    @GetMapping("/findOnePersonalData")
    public Optional<PersonalData> findOnePersonalData(@RequestParam(value="id_personal_data",required = true)Integer integer) throws ServicesExceptions {
        return service.findOnePersonalData(integer);
    }
    //merge
    @GetMapping("/findAllPersonalDatas")
    public Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions {
        return service.findAllPersonalDatas();
    }

    @GetMapping("/findByFirstNamePersonalData")
    public Iterable<PersonalData> findByFirstNamePersonalData(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNamePersonalData(firstName);
    }

    @GetMapping("/findByLastNamePersonalData")
    public Iterable<PersonalData> findByLastNamePersonalData(@RequestParam(value="id_personal_data",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNamePersonalData(lastName);
    }

    @GetMapping("/findByCnpPersonalData")
    public PersonalData findByCnpPersonalData(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpPersonalData(cnp);
    }

    @GetMapping("/findByEmailPersonalData")
    public Optional<Person> findOnePerson(@RequestParam(value="id_person",required = true)Integer integer) throws ServicesExceptions {
        return service.findOnePerson(integer);
    }



    @GetMapping("/findByFirstNamePerson")
    public Iterable<Person> findByFirstNamePerson(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNamePerson(firstName);
    }

    @GetMapping("/findByLastNamePerson")
    public Iterable<Person> findByLastNamePerson(@RequestParam(value="lastName",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNamePerson(lastName);
    }

    @GetMapping("/findByCnpPerson")
    public Iterable<Person> findByCnpPerson(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpPerson(cnp);
    }

    @GetMapping("/findByEmailPerson")
    public Person findByEmailPerson(@RequestParam(value="id_email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailPerson(email);
    }

    @GetMapping("/findByPhoneNumberPerson")
    public Iterable<Person> findByPhoneNumberPerson(@RequestParam(value="phoneNumber",required = true)String phoneNumber) throws ServicesExceptions {
        return service.findByPhoneNumberPerson(phoneNumber);
    }

    @GetMapping("/findByUsernamePerson")
    public Person findByUsernamePerson(@RequestParam(value="usernamePerson",required = true)String username) throws ServicesExceptions {
        return service.findByUsernamePerson(username);
    }


    @GetMapping("/findOneRetrieved")
    public Optional<RetrievedCoupons> findOneRetrieved(@RequestParam(value="id_retrieved",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneRetrieved(integer);
    }

    @GetMapping("/findAllRetrieved")
    public Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions {
        return service.findAllRetrieved();
    }

    @GetMapping("/findByCouponIdRetrieved")
    public Iterable<RetrievedCoupons> findByCouponIdRetrieved(@RequestParam(value="couponId",required = true)Integer couponId) throws ServicesExceptions {
        return service.findByCouponIdRetrieved(couponId);
    }

    @GetMapping("/findByPersonIdRetrieved")
    public Iterable<RetrievedCoupons> findByPersonIdRetrieved(@RequestParam(value="personId",required = true)Integer personId) throws ServicesExceptions {
        return service.findByPersonIdRetrieved(personId);
    }

    @GetMapping("/findByDateRetrieved")
    public Iterable<RetrievedCoupons> findByDateRetrieved(@RequestParam(value="date",required = true)String date) throws ServicesExceptions {
        return service.findByDateRetrieved(date);
    }

    @GetMapping("/findOneStudent")
    public Optional<Student> findOneStudent(@RequestParam(value="id_student",required = true)Integer integer) throws ServicesExceptions {
        return service.findOneStudent(integer);
    }

    @GetMapping("/findAllStudent")
    public Iterable<Student> findAllStudent() throws ServicesExceptions {
        return service.findAllStudent();
    }

//    @GetMapping("/saveStudent")
    public Optional<Student> saveStudent(Student entity) throws ServicesExceptions {
        return service.saveStudent(entity);
    }

//    @GetMapping("/deleteStudent")
    public Optional<Student> deleteStudent(Student entity) throws ServicesExceptions {
        return service.deleteStudent(entity);
    }

//    @GetMapping("/updateStudent")
    public Optional<Student> updateStudent(Student entity) throws ServicesExceptions {
        return service.updateStudent(entity);
    }

    @GetMapping("/findByFirstNameStudent")
    public Iterable<Student> findByFirstNameStudent(@RequestParam(value="firstName",required = true)String firstName) throws ServicesExceptions {
        return service.findByFirstNameStudent(firstName);
    }

    @GetMapping("/findByLastNameStudent")
    public Iterable<Student> findByLastNameStudent(@RequestParam(value="lastName",required = true)String lastName) throws ServicesExceptions {
        return service.findByLastNameStudent(lastName);
    }

    @GetMapping("/findByCnpStudent")
    public Iterable<Student> findByCnpStudent(@RequestParam(value="cnp",required = true)String cnp) throws ServicesExceptions {
        return service.findByCnpStudent(cnp);
    }

    @GetMapping("/findByEmailStudent")
    public Student findByEmailStudent(@RequestParam(value="email",required = true)String email) throws ServicesExceptions {
        return service.findByEmailStudent(email);
    }

    @GetMapping("/findByPhoneNumberStudent")
    public Iterable<Student> findByPhoneNumberStudent(@RequestParam(value="phoneNumber",required = true)String phoneNumber) throws ServicesExceptions {
        return service.findByPhoneNumberStudent(phoneNumber);
    }

    @GetMapping("/findByUsernameStudent")
    public Student findByUsernameStudent(@RequestParam(value="username",required = true)String username) throws ServicesExceptions {
        return service.findByUsernameStudent(username);
    }

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
