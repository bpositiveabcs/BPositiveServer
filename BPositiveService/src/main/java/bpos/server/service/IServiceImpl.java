package bpos.server.service;

import bpos.common.model.*;

import java.time.LocalDate;
import java.util.Optional;
public interface IServiceImpl {
    //Address services

     Optional<Address> findOneAddress(Integer integer) throws ServicesExceptions;
     Iterable<Address> findAllAddresses() throws ServicesExceptions;
     Optional<Address> saveAddress(Address entity) throws ServicesExceptions;
     Optional<Address> deleteAddress(Address entity) throws ServicesExceptions;
     Optional<Address> updateAddress(Address entity) throws ServicesExceptions;
     Optional<BloodTest> findOneBloodTest(Integer integer) throws ServicesExceptions;

     Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions;
     Optional<BloodTest> saveBloodTest(BloodTest entity) throws ServicesExceptions;
     Optional<BloodTest> deleteBloodTest(BloodTest entity) throws  ServicesExceptions;
     Optional<BloodTest> updateBloodTest(BloodTest entity) throws ServicesExceptions;
     //center

    Center findByUsernameCenter(String username) throws ServicesExceptions;
    Center findByEmailCenter(String email) throws ServicesExceptions;
    Iterable<Center> findByNameCenter(String name) throws ServicesExceptions;
    Optional<Center> findOneCenter(Integer integer) throws ServicesExceptions;
    Iterable<Center> findAllCenters() throws ServicesExceptions;
    Optional<Center> saveCenter(Center entity) throws ServicesExceptions;
    Optional<Center> deleteCenter(Center entity) throws ServicesExceptions;
    Optional<Center> updateCenter(Center entity) throws ServicesExceptions;
    //coupon

    Iterable<Coupon> findByCodeCoupon(String code) throws ServicesExceptions;
    Iterable<Coupon> findByProviderCoupon(String provider) throws ServicesExceptions;
    Iterable<Coupon> findByNameCoupon(String name) throws ServicesExceptions;
    Iterable<Coupon> findByEndDateCoupon(LocalDate endDate) throws ServicesExceptions;
    Optional<Coupon> findOneCoupon(Integer integer) throws ServicesExceptions;
    Iterable<Coupon> findAllCoupons() throws ServicesExceptions;
    Optional<Coupon> saveCoupon(Coupon entity) throws ServicesExceptions;
    Optional<Coupon> deleteCoupon(Coupon entity) throws ServicesExceptions;
    Optional<Coupon> updateCoupon(Coupon entity) throws ServicesExceptions;
    //donation
   // Iterable<Donation> findAllUtilitaryDonation(List<String > attributes, List<Object> values) throws ServicesExceptions;
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
    //event services
    //Iterable<Event> findAllUtilitaryEvent(List<String > attributes, List<Object> values) throws ServicesExceptions;
    Iterable<Event> findByNameEvent(String nume) throws ServicesExceptions;
    Iterable<Event> findByAnnouncementDateEvent(LocalDate data) throws ServicesExceptions;
    Iterable<Event> findByCenterIdEvent(Integer centruId) throws ServicesExceptions;
    Iterable<Event> findByDataInceputEvent(LocalDate data) throws ServicesExceptions;

    Optional<Event> findOneEvent(Integer integer) throws ServicesExceptions;
    Iterable<Event> findAllEvents() throws ServicesExceptions;
    Optional<Event> saveEvent(Event entity) throws ServicesExceptions;
    Optional<Event> deleteEvent(Event entity) throws ServicesExceptions;
    Optional<Event> updateEvent(Event entity) throws ServicesExceptions;
    //institution

    Optional<Institution> findOneInstitution(Integer integer) throws ServicesExceptions;
    Iterable<Institution> findAllInstitutions() throws ServicesExceptions;
    Optional<Institution> saveInstitution(Institution entity) throws ServicesExceptions;
    Optional<Institution> deleteInstitution(Institution entity) throws ServicesExceptions;
    Optional<Institution> updateInstitution(Institution entity) throws ServicesExceptions;
    Iterable<Institution> findByNameInstitution(String name)  throws ServicesExceptions;
    Iterable<Institution> findByAddressInstitution(String address) throws ServicesExceptions;
    Iterable<Institution> findByEmailInstitution(String email) throws ServicesExceptions;
    //LogInfo
    Optional<LogInfo> findOneLogInfo(Integer integer) throws ServicesExceptions;
    Iterable<LogInfo> findAllLogInfos() throws ServicesExceptions;
    Optional<LogInfo> saveLogInfo(LogInfo entity) throws ServicesExceptions;
    Optional<LogInfo> deleteLogInfo(LogInfo entity) throws ServicesExceptions;
    Optional<LogInfo> updateLogInfo(LogInfo entity) throws ServicesExceptions;
    LogInfo findByUsernameLogInfo(String username) throws ServicesExceptions;
    LogInfo findByEmailLogInfo(String email) throws ServicesExceptions;
    //medical info
    Optional<MedicalInfo> findOneMedicalInfo(Integer integer) throws ServicesExceptions;
    Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions;
    Optional<MedicalInfo> saveMedicalInfo(MedicalInfo entity) throws ServicesExceptions;
    Optional<MedicalInfo> deleteMedicalInfo(MedicalInfo entity) throws ServicesExceptions;
    Optional<MedicalInfo> updateMedicalInfo(MedicalInfo entity) throws ServicesExceptions;
    Iterable<MedicalInfo> findByBloodTypeMedicalInfo(String bloodType) throws ServicesExceptions;
    Iterable<MedicalInfo> findByRhMedicalInfo(String rh) throws ServicesExceptions;
    Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(String bloodType, String rh) throws ServicesExceptions;
    //pesonal dta
    Optional<PersonalData> findOnePersonalData(Integer integer) throws ServicesExceptions;
    Iterable<PersonalData> findAllPersonalDatas() throws ServicesExceptions;
    Optional<PersonalData> savePersonalData(PersonalData entity) throws ServicesExceptions;
    Optional<PersonalData> deletePersonalData(PersonalData entity) throws ServicesExceptions;
    Optional<PersonalData> updatePersonalData(PersonalData entity) throws ServicesExceptions;
    Iterable<PersonalData> findByFirstNamePersonalData(String firstName) throws ServicesExceptions;
    Iterable<PersonalData> findByLastNamePersonalData(String lastName) throws ServicesExceptions;
    PersonalData findByCnpPersonalData(String cnp) throws ServicesExceptions;
    //person services
    Optional<Person> findOnePerson(Integer integer) throws ServicesExceptions;
    Iterable<Person> findAllPersons() throws ServicesExceptions;
    Optional<Person> savePerson(Person entity) throws ServicesExceptions;
    Optional<Person> deletePerson(Person entity) throws ServicesExceptions;
    Optional<Person> updatePerson(Person entity) throws ServicesExceptions;
    Iterable<Person> findByFirstNamePerson(String firstName) throws ServicesExceptions;
    Iterable<Person> findByLastNamePerson(String lastName) throws ServicesExceptions;
    Iterable<Person> findByCnpPerson(String cnp) throws ServicesExceptions;
    Person findByEmailPerson(String email) throws ServicesExceptions;
    Iterable<Person> findByPhoneNumberPerson(String phoneNumber) throws ServicesExceptions;
    Person findByUsernamePerson(String username) throws ServicesExceptions;
    //retrieved

    Optional<RetrievedCoupons> findOneRetrieved(Integer integer) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findAllRetrieved() throws ServicesExceptions;
    Optional<RetrievedCoupons> saveRetrieved(RetrievedCoupons entity) throws ServicesExceptions;
    Optional<RetrievedCoupons> deleteRetrieved(RetrievedCoupons entity) throws ServicesExceptions;
    Optional<RetrievedCoupons> updateRetrieved(RetrievedCoupons entity) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findByCouponIdRetrieved(Integer couponId) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findByPersonIdRetrieved(Integer personId) throws ServicesExceptions;
    Iterable<RetrievedCoupons> findByDateRetrieved(String date) throws ServicesExceptions;
    //student
    Optional<Student> findOneStudent(Integer integer) throws ServicesExceptions;
    Iterable<Student> findAllStudent() throws ServicesExceptions;
    Optional<Student> saveStudent(Student entity) throws ServicesExceptions;
    Optional<Student> deleteStudent(Student entity) throws ServicesExceptions;
    Optional<Student> updateStudent(Student entity) throws ServicesExceptions;
    Iterable<Student> findByFirstNameStudent(String firstName) throws ServicesExceptions;
    Iterable<Student> findByLastNameStudent(String lastName) throws ServicesExceptions;
    Iterable<Student> findByCnpStudent(String cnp) throws ServicesExceptions;
    Student findByEmailStudent(String email) throws ServicesExceptions;
    Iterable<Student> findByPhoneNumberStudent(String phoneNumber) throws ServicesExceptions;
    Student findByUsernameStudent(String username) throws ServicesExceptions;

    //login stuff
    Optional<Person> login(LogInfo logInfo,IObserver observer) throws ServicesExceptions;
    Optional<Center>loginCenter(LogInfo logInfo,IObserver observer) throws ServicesExceptions;

    void  logoutCenter(Center center,IObserver observer) throws ServicesExceptions;
    void  logoutPerson(Person center,IObserver observer) throws ServicesExceptions;
    void donationRegister(Donation donation, Person person, Event event) throws ServicesExceptions;
}