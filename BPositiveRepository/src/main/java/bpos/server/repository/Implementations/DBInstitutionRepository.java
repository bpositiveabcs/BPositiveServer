package bpos.repository.Implementations;

import bpos.model.Institution;
import bpos.model.Validators.Implementation.InstitutionValidator;
import bpos.repository.Interfaces.InstitutionRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBInstitutionRepository implements InstitutionRepository {
    private DBUtils dbUtils;

    public DBInstitutionRepository(Properties properties, InstitutionValidator institutionValidator) {
        this.dbUtils = new DBUtils(properties);
        this.institutionValidator = institutionValidator;
    }

    private static final Logger logger= LogManager.getLogger();
    private InstitutionValidator institutionValidator;
    private Iterable<Institution> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection connection=dbUtils.getConnection();
        String sql="SELECT * FROM View_Institutie";
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
        List<Institution> institutions=new ArrayList<>();
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
            for(int i=0;i<values.size();i++)
            {
                preparedStatement.setObject(i+1,values.get(i));
            }
            try(java.sql.ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    Institution institution= DBGetters.getInstitution(resultSet);
                    institutions.add(institution);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return institutions;
    }
    @Override
    public Optional<Institution> findOne(Integer integer) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("id_Institutie");
        values.add(integer);
        Iterable<Institution> institutions=findAllUtilitary(attributes,values);
        if(institutions.iterator().hasNext())
        {
            return Optional.of(institutions.iterator().next());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Institution> findAll() {
        return findAllUtilitary(null,new ArrayList<>());
    }

    @Override
    public Optional<Institution> save(Institution entity) {

        Connection connection=dbUtils.getConnection();
        if(entity==null)
        {
            logger.error("Entity must not be null");
            throw new IllegalArgumentException("Entity must not be null");
        }
        if(institutionValidator!=null)
        {
            institutionValidator.validate(entity);
        }
        try(PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO Institutie(nume,email,adresa) VALUES (?,?,?)"))
        {
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getEmail());
            preparedStatement.setString(3,entity.getAddress());
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
    public Optional<Institution> delete(Institution entity) {

        Connection connection=dbUtils.getConnection();
        if(entity==null)
        {
            logger.error("Entity must not be null");
            throw new IllegalArgumentException("Entity must not be null");
        }
        try(PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM Institutie WHERE id=?"))
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
    public Optional<Institution> update(Institution entity) {

        Connection connection=dbUtils.getConnection();
        if(entity==null)
        {
            logger.error("Entity must not be null");
            throw new IllegalArgumentException("Entity must not be null");
        }
        if(institutionValidator==null)
        {
            institutionValidator.validate(entity);
        }
        try(PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Institutie SET nume=?,email=?,adresa=? WHERE id=?"))
        {
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getEmail());
            preparedStatement.setString(3,entity.getAddress());
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
    public Iterable<Institution> findByName(String name) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("nume_Institutie");
        values.add(name);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Institution> findByAddress(String address) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("adresa_Institutie");
        values.add(address);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Institution> findByEmail(String email) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("email_Institutie");
        values.add(email);
        return findAllUtilitary(attributes,values);
    }
}
