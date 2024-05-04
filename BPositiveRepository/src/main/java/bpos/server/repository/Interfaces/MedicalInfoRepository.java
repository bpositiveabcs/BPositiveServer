package bpos.server.repository.Interfaces;


import bpos.common.model.MedicalInfo;

public interface MedicalInfoRepository  extends IRepository<Integer, MedicalInfo>{
    Iterable<MedicalInfo> findByBloodType(String bloodType);
    Iterable<MedicalInfo> findByRh(String rh);
    Iterable<MedicalInfo> findByBloodTypeAndRh(String bloodType, String rh);

}
