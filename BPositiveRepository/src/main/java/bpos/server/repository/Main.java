package bpos.repository;

import bpos.model.*;
import bpos.repository.Implementations.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void testLogInInfo(DBLogInfoRepository dbLogInfoRepository)
    {
       // dbLogInfoRepository.findAll().forEach(s -> System.out.println(s)    );
        dbLogInfoRepository.save(new LogInfo("admin","admin","admin","seeeee"));
        dbLogInfoRepository.findAll().forEach(s -> System.out.println(s)    );
        LogInfo logInfo= dbLogInfoRepository.findByEmail("admin");
    //    logInfo.setSeed("seeeee2");
      //  dbLogInfoRepository.update(logInfo);
        //dbLogInfoRepository.findAll().forEach(s -> System.out.println(s)    );
        //am loginfo cu id
//        LogInfo logInfo= dbLogInfoRepository.findOne(2).get();
//        System.out.println(logInfo);
//        dbLogInfoRepository.delete(logInfo);
//        dbLogInfoRepository.findAll().forEach(s -> System.out.println(s)    );

    }
    public static void testAddress(DBAddressRepository dbAddressRepository)
    {
        dbAddressRepository.findAll().forEach(s -> System.out.println(s)    );

        dbAddressRepository.save(new Address("Country","City","County","Street","Number","Block",0,"Apartment"));
        dbAddressRepository.findAll().forEach(s -> System.out.println(s)    );
    }
    public static void main(String[] args) {
        Properties properties=new Properties();
        try
        {
            properties.load(new FileReader("bd.config"));

        }
        catch (IOException e)
        {
            System.out.println("Could not find "+e);
        }
        //Repos
        DBLogInfoRepository dbLogInfoRepository = new DBLogInfoRepository(properties,null);
        DBAddressRepository dbAddressRepository = new DBAddressRepository(properties,null);
        DBCenterRepository dbCenterRepository = new DBCenterRepository(properties,null);
        DBCouponRepository dbCouponRepository = new DBCouponRepository(properties,null);
        DBRetrievedCouponsRepository dbRetrievedCouponsRepository = new DBRetrievedCouponsRepository(properties,null);
        DBBloodTestRepository dbBloodTestRepository = new DBBloodTestRepository(properties,null);
        DBPersonalDataRepository dbPersonalDataRepository = new DBPersonalDataRepository(properties,null);
        DBPersonRepository dbPersonRepository = new DBPersonRepository(properties,null);
        DBStudentRepository dbStudentRepository = new DBStudentRepository(properties,null);
        DBDonationRepository dbDonationRepository = new DBDonationRepository(properties,null);
        DBDonationTypeRepository dbDonationTypeRepository = new DBDonationTypeRepository(properties,null);
        DBEventRepository dbEventRepository = new DBEventRepository(properties,null);
        DBMedicalInfoRepository dbMedicalInfoRepository = new DBMedicalInfoRepository(properties,null);
        DBInstitutionRepository dbInstitutionRepository1 = new DBInstitutionRepository(properties,null);

        //Verified LogInfo
        //testLogInInfo(dbLogInfoRepository); //id=3
        //testAddress(dbAddressRepository);
      //  dbCenterRepository.findAll().forEach(s -> System.out.println(s)    );
    //    LogInfo logInfo= dbLogInfoRepository.findByEmail("admin");
   //     dbCenterRepository.save(new Center("aa",logInfo,"aaa","aaa"));
//        dbInstitutionRepository1.findAll().forEach(s -> System.out.println(s)    );
//        dbInstitutionRepository1.save(new Institution("name","details","address"));
//        dbInstitutionRepository1.findAll().forEach(s -> System.out.println(s)    );
//        MedicalInfo medicalInfo = new MedicalInfo(true, BloodType.A2, Rh.POSITIVE,new ArrayList<>());
//        medicalInfo.setId(3);
//        dbMedicalInfoRepository.findAll();
//        dbMedicalInfoRepository.save(medicalInfo);
//        dbMedicalInfoRepository.findAll().forEach(s -> System.out.println(s)    );

    //        BloodTest bloodTest=new BloodTest("a","a",1);
    //        bloodTest.setId(1);
    //        dbBloodTestRepository.save(bloodTest);
    //dbBloodTestRepository.findAll().forEach(s -> System.out.println(s)    );
//    //var a=dbMedicalInfoRepository.findAll();
//        Coupon coupon=new Coupon(20,"Ca","Meee","Ananas", LocalDateTime.now(),8,"XXA");
//        coupon.setId(1);
//        dbCouponRepository.save(coupon);
//        dbCouponRepository.findAll().forEach(s -> System.out.println(s)    );
      //  RetrievedCoupons retrievedCoupons=new RetrievedCoupons(coupon,1,"XXA",LocalDateTime.now(),LocalDateTime.now());
//        dbDonationTypeRepository.findAll();
//        DonationType donationType=new DonationType("Transfuzie",3);
//        donationType.setId(1);
//        dbDonationTypeRepository.save(donationType);
//        dbDonationTypeRepository.findAll().forEach(s -> System.out.println(s)    );
//        DonationType donationType=dbDonationTypeRepository.findOne(1).get();
//        Donation donation=new Donation(donationType,20);
//        dbDonationRepository.save(donation);
//        dbDonationRepository.findAll().forEach(s -> System.out.println(s)    );
//        Center center=dbCenterRepository.findByEmail("admin");
//        Event event=new Event("name",LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),1,"details","a",center);
//        dbEventRepository.save(event);
//        dbEventRepository.findAll().forEach(s -> System.out.println(s)    );
//        Address address=dbAddressRepository.findOne(1).get();
//        PersonalData personalData=new PersonalData(address,"nr","name","surname","CNP","email", LocalDate.now());
//        personalData.setId(4);
//        dbPersonalDataRepository.save(personalData);
//        dbPersonalDataRepository.findAll().forEach(s -> System.out.println(s)    );
//        Coupon coupon=dbCouponRepository.findOne(1).get();
//cupon id 1
        //persoana id 4
        LogInfo logInfo1= dbLogInfoRepository.findByEmail("p1");
        PersonalData personalData=dbPersonalDataRepository.findOne(4).get();
        MedicalInfo medicalInfo=dbMedicalInfoRepository.findOne(1).get();
        Institution institution=dbInstitutionRepository1.findOne(1).get();
        Person person=new Person(logInfo1,2,personalData,medicalInfo,institution);
        person.setId(4);
//        dbPersonRepository.save(person);
//        dbPersonRepository.findAll().forEach(s -> System.out.println(s));
      //  RetrievedCoupons retrievedCoupons=new RetrievedCoupons(coupon,person.getId(),"XXA",LocalDateTime.now(),LocalDateTime.now());
    //    dbRetrievedCouponsRepository.save(retrievedCoupons);
        dbRetrievedCouponsRepository.findAll().forEach(s -> System.out.println(s));
        dbPersonRepository.findAll().forEach(s -> System.out.println(s));

    }
}
