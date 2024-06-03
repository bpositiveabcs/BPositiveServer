package bpos.server.service.Interface;

import bpos.common.model.BloodTest;
import bpos.common.model.MedicalInfo;
import bpos.server.service.ServicesExceptions;
import org.springframework.core.io.Resource;

import java.util.Optional;

public interface IMedicalInformationService {
    Optional<BloodTest> findOneBloodTest(Integer integer) throws ServicesExceptions;

    Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions;
    Optional<BloodTest> saveBloodTest(BloodTest entity) throws ServicesExceptions;
    Optional<BloodTest> deleteBloodTest(BloodTest entity) throws  ServicesExceptions;
    Optional<BloodTest> updateBloodTest(BloodTest entity) throws ServicesExceptions;
    Optional<MedicalInfo> findOneMedicalInfo(Integer integer) throws ServicesExceptions;
    Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions;
    Optional<MedicalInfo> saveMedicalInfo(MedicalInfo entity) throws ServicesExceptions;
    Optional<MedicalInfo> deleteMedicalInfo(MedicalInfo entity) throws ServicesExceptions;
    Optional<MedicalInfo> updateMedicalInfo(MedicalInfo entity) throws ServicesExceptions;
    Iterable<MedicalInfo> findByBloodTypeMedicalInfo(String bloodType) throws ServicesExceptions;
    Iterable<MedicalInfo> findByRhMedicalInfo(String rh) throws ServicesExceptions;
    Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(String bloodType, String rh) throws ServicesExceptions;

    Resource loadFileAsResource(String username, String filename);
}
