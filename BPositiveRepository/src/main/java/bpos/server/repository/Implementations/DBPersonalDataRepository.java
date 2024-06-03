package bpos.server.repository.Implementations;


import bpos.common.model.PersonalData;
import bpos.common.model.Validators.Implementation.PersonalDataValidator;
import bpos.server.repository.Interfaces.PersonalDataRepository;
import bpos.server.repository.Utils.DBGetters;
import bpos.server.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBPersonalDataRepository implements PersonalDataRepository {
    private DBUtils dbUtils;

    public DBPersonalDataRepository(Properties properties, PersonalDataValidator personalDataValidator) {
        this.dbUtils = new DBUtils(properties);
        this.personalDataValidator = personalDataValidator;
    }
    public DBPersonalDataRepository(){}
    private static final Logger logger = LoggerFactory.getLogger(DBPersonalDataRepository.class);
    private PersonalDataValidator personalDataValidator;
    private Iterable<PersonalData> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_DatePersonale";
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
        List<PersonalData> personalData=new java.util.ArrayList<>();
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
                    PersonalData personalData1= DBGetters.getPersonalData(resultSet);
                    personalData.add(personalData1);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e.getMessage());
        }
        return personalData;
    }
    @Override
    public Optional<PersonalData> findOne(Integer integer) {
       List<String> attributes=new java.util.ArrayList<>();
         List<Object> values=new java.util.ArrayList<>();
            attributes.add("id_DatePersonale");
            values.add(integer);
            Iterable<PersonalData> personalData=findAllUtilitary(attributes,values);
            if(personalData.iterator().hasNext())
            {
                return Optional.of(personalData.iterator().next());
            }
            return Optional.empty();
    }

    @Override
    public Iterable<PersonalData> findAll() {
        return findAllUtilitary(null,new java.util.ArrayList<>());
    }

    @Override
    public Optional<PersonalData> save(PersonalData entity) {
        if(personalDataValidator!=null)
            personalDataValidator.validate(entity);
        Connection con=dbUtils.getConnection();
        String sql="INSERT INTO DatePersonale ( id,id_adresa, telefon, nume, prenume, cnp, sex, data_nasterii) VALUES (?,?,?,?,?,?,?,?)";
                try(java.sql.PreparedStatement preparedStatement=con.prepareStatement(sql))
                {
                    preparedStatement.setInt(1,entity.getId());
                    preparedStatement.setInt(2,entity.getAddress().getId());
                    preparedStatement.setString(3,entity.getPhoneNumber());
                    preparedStatement.setString(4,entity.getFirstName());
                    preparedStatement.setString(5,entity.getLastName());
                    preparedStatement.setString(6,entity.getCnp());
                    preparedStatement.setString(7,entity.getSex());
                    preparedStatement.setString(8,entity.getBirthDate().toString());
                    preparedStatement.executeUpdate();
                    return Optional.of(entity);
                }
                catch (java.sql.SQLException e)
                {
                    logger.error(e.getMessage());
                }
                  return Optional.empty();}

    @Override
    public Optional<PersonalData> delete(PersonalData entity) {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entity must not be null");
        }
        Connection con=dbUtils.getConnection();
        String sql="DELETE FROM DatePersonale WHERE id=?";
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setInt(1,entity.getId());
            int affected=preparedStatement.executeUpdate();
            if(affected==1)
            {
                return Optional.of(entity);
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<PersonalData> update(PersonalData entity) {

        if(entity==null)
        {
            throw new IllegalArgumentException("Entity must not be null");
        }
        if(entity.getId()==null)
        {
            throw new IllegalArgumentException("Entity must have an id");
        }
        if(entity.getId()<0)
        {
            throw new IllegalArgumentException("Entity id must be positive");
        }
        if(personalDataValidator!=null)
            personalDataValidator.validate(entity);
        Connection con=dbUtils.getConnection();
        String sql="UPDATE DatePersonale SET  telefon=?, nume=?, prenume=?, cnp=?,sex=?,data_nasterii=?";
        try (PreparedStatement statement=con.prepareStatement(sql))
        {
            statement.setString(1,entity.getPhoneNumber());
            statement.setString(2,entity.getFirstName());
            statement.setString(3,entity.getLastName());
            statement.setString(4,entity.getCnp());
            statement.setString(5,entity.getSex());
            statement.setString(6,entity.getBirthDate().toString());
            statement.executeUpdate();
            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<PersonalData> findByFirstName(String firstName) {
        return findAllUtilitary(List.of("prenume"),List.of(firstName));
    }

    @Override
    public Iterable<PersonalData> findByLastName(String lastName) {
        return findAllUtilitary(List.of("nume"),List.of(lastName));
    }

    @Override
    public PersonalData findByCnp(String cnp) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("cnp");
        values.add(cnp);
        Iterable<PersonalData> personalData=findAllUtilitary(attributes,values);
        if(personalData.iterator().hasNext())
        {
            return personalData.iterator().next();
        }
        return null;
    }
}
