package bpos.repository.Implementations;

import bpos.model.BloodTest;
import bpos.model.MedicalInfo;
import bpos.model.Validators.Implementation.MedicalInfoValidator;
import bpos.repository.Interfaces.MedicalInfoRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.*;

public class DBMedicalInfoRepository implements MedicalInfoRepository {
    private DBUtils dbUtils;

    public DBMedicalInfoRepository(Properties properties, MedicalInfoValidator medicalInfoValidator) {
        this.dbUtils = new DBUtils(properties);
        this.medicalInfoValidator = medicalInfoValidator;
    }

    private static final Logger logger= LogManager.getLogger();
    private MedicalInfoValidator medicalInfoValidator;
    private Iterable<MedicalInfo> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_MedicalInformation";
        if(attributes!=null)
        {
            sql+=" where ";
            for(int i=0;i<attributes.size();i++)
            {
                sql+=attributes.get(i)+"=?";
                if(i<attributes.size()-1)
                {
                    sql+=" and ";
                }
            }
        }
        HashMap<Integer,MedicalInfo> medicalInfos=new HashMap<>();
        try (java.sql.PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            for(int i=0;i<values.size();i++)
            {
                preparedStatement.setObject(i+1,values.get(i));
            }
            try(java.sql.ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {

                    MedicalInfo medicalInfo= DBGetters.getMedicalInfo(resultSet);
                    if(medicalInfos.containsKey(medicalInfo.getId()))
                    {
                        medicalInfo=addBloodTests(medicalInfo,medicalInfos);
                        medicalInfos.put(medicalInfo.getId(),medicalInfo);
                    }
                    else
                    {
                        medicalInfos.put(medicalInfo.getId(),medicalInfo);
                    }


                }


            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        List<MedicalInfo> medicalInfos1=medicalInfos.values().stream().toList();
        return medicalInfos1;
    }

    private MedicalInfo addBloodTests(MedicalInfo medicalInfo, HashMap<Integer,MedicalInfo> medicalInfos) {
        if(medicalInfo.getMedicalHistory().size()==0)
        {
            return medicalInfo;
        }
        else
        {
            List<BloodTest> bloodTestsExisting=medicalInfos.get(medicalInfo.getId()).getMedicalHistory();
            bloodTestsExisting.add(medicalInfo.getMedicalHistory().get(0));
            medicalInfo.setMedicalHistory(bloodTestsExisting);
            return medicalInfo;
        }

    }

    @Override
    public Optional<MedicalInfo> findOne(Integer integer) {
        List<String> attributes=List.of("id_MedicalInformation");
        List<Object> values=List.of(integer);
        List<MedicalInfo> medicalInfos=(List<MedicalInfo>)findAllUtilitary(attributes,values);
        if(medicalInfos.size()>0)
        {
            return Optional.of(medicalInfos.get(0));
        }
        return Optional.empty();

    }

    @Override
    public Iterable<MedicalInfo> findAll() {
        return findAllUtilitary(null,new ArrayList<>());
    }

    @Override
    public Optional<MedicalInfo> save(MedicalInfo entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");

        }
        if(medicalInfoValidator!=null)
        {
            medicalInfoValidator.validate(entity);
        }
        Connection con=dbUtils.getConnection();
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO InformatiiMedicale (id,eligibilitateDonare,grupaSange,Rh) VALUES (?,?,?,?)"))
        {
            preparedStatement.setInt(1,entity.getId());
            if(entity.getEligibility())
            {
                preparedStatement.setInt(2,1);
            }
            else
            {
                preparedStatement.setInt(2,0);
            }
            preparedStatement.setString(3,entity.getBloodType().toString());
            preparedStatement.setString(4,entity.getRh().toString());
            preparedStatement.executeUpdate();
            return Optional.empty();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<MedicalInfo> delete(MedicalInfo entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");

        }
        Connection con=dbUtils.getConnection();
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM InformatiiMedicale WHERE id=?"))
        {
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
            return Optional.empty();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<MedicalInfo> update(MedicalInfo entity) {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entity must not be null");
        }
        if(medicalInfoValidator!=null)
        {
            medicalInfoValidator.validate(entity);
        }
        Connection con=dbUtils.getConnection();
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement("UPDATE InformatiiMedicale SET eligibilitateDonare=?,grupaSange=?,rh=? WHERE id=?"))
        {
            preparedStatement.setBoolean(1,entity.getEligibility() );
            preparedStatement.setString(2,entity.getBloodType().toString());
            preparedStatement.setString(3,entity.getRh().toString());
            preparedStatement.setInt(4,entity.getId());
            preparedStatement.executeUpdate();
            return Optional.empty();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
            return Optional.of(entity);
        }
    }

    @Override
    public Iterable<MedicalInfo> findByBloodType(String bloodType) {
        return findAllUtilitary(Arrays.asList("grupaSange_MedicalInformation"),Arrays.asList(bloodType));
    }

    @Override
    public Iterable<MedicalInfo> findByRh(String rh) {
        return findAllUtilitary(Arrays.asList("rh_MedicalInformation"),Arrays.asList(rh));
    }

    @Override
    public Iterable<MedicalInfo> findByBloodTypeAndRh(String bloodType, String rh) {
        return findAllUtilitary(Arrays.asList("grupaSange_MedicalInformation","rh_MedicalInformation"),Arrays.asList(bloodType,rh));
    }
}
