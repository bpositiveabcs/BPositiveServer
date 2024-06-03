package bpos.server.service.Implementation;

import bpos.common.model.BloodTest;
import bpos.common.model.MedicalInfo;
import bpos.other.NotificationRest;
import bpos.server.repository.Interfaces.BloodTestRepository;
import bpos.server.repository.Interfaces.MedicalInfoRepository;
import bpos.server.service.Interface.IMedicalInformationService;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.WebSockets.NotificationService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Path;
import java.util.Optional;

public class MedicalInformationService implements IMedicalInformationService {
    private final NotificationService notificationService;
    private final Path fileStorageLocation= Path.of("user_medicalInfo");
    private BloodTestRepository bloodTestRepository;
    private MedicalInfoRepository medicalInfoRepository;

    public MedicalInformationService(NotificationService notificationService, BloodTestRepository bloodTestRepository, MedicalInfoRepository medicalInfoRepository) {
        this.notificationService = notificationService;
        this.bloodTestRepository = bloodTestRepository;
        this.medicalInfoRepository = medicalInfoRepository;
    }

    @Override
    public Optional<BloodTest> findOneBloodTest(Integer integer) throws ServicesExceptions {
        return bloodTestRepository.findOne(integer);
    }

    @Override
    public Iterable<BloodTest> findAllBloodTest() throws ServicesExceptions {
        return bloodTestRepository.findAll();
    }

    @Override
    public Optional<BloodTest> saveBloodTest(BloodTest entity) throws ServicesExceptions {
        BloodTest bloodTest = bloodTestRepository.save(entity).get();
        notificationService.notifyCenter(NotificationRest.USER_UPDATE+":"+bloodTest.getMedicalInfo().toString());
        return bloodTestRepository.save(entity);

    }

    @Override
    public Optional<BloodTest> deleteBloodTest(BloodTest entity) throws ServicesExceptions {
        return bloodTestRepository.delete(entity);
    }

    @Override
    public Optional<BloodTest> updateBloodTest(BloodTest entity) throws ServicesExceptions {
        return bloodTestRepository.update(entity);
    }

    @Override
    public Optional<MedicalInfo> findOneMedicalInfo(Integer integer) throws ServicesExceptions {
        return medicalInfoRepository.findOne(integer);
    }

    @Override
    public Iterable<MedicalInfo> findAllMedicalInfos() throws ServicesExceptions {
        return medicalInfoRepository.findAll();
    }

    @Override
    public Optional<MedicalInfo> saveMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
        return medicalInfoRepository.save(entity);
    }

    @Override
    public Optional<MedicalInfo> deleteMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
        return medicalInfoRepository.delete(entity);
    }

    @Override
    public Optional<MedicalInfo> updateMedicalInfo(MedicalInfo entity) throws ServicesExceptions {
        return medicalInfoRepository.update(entity);
    }

    @Override
    public Iterable<MedicalInfo> findByBloodTypeMedicalInfo(String bloodType) throws ServicesExceptions {
        return medicalInfoRepository.findByBloodType(bloodType);
    }

    @Override
    public Iterable<MedicalInfo> findByRhMedicalInfo(String rh) throws ServicesExceptions {
        return medicalInfoRepository.findByRh(rh);
    }

    @Override
    public Iterable<MedicalInfo> findByBloodTypeAndRhMedicalInfo(String bloodType, String rh) throws ServicesExceptions {
        return medicalInfoRepository.findByBloodTypeAndRh(bloodType, rh);
    }

    @Override
    public Resource loadFileAsResource(String username, String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(username).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (Exception ex) {
            throw new RuntimeException("File not found " + filename, ex);
        }
    }
}
