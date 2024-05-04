package bpos.repository.Implementations;

import bpos.model.BloodTest;
import bpos.model.Validators.Implementation.BloodTestValidator;
import bpos.repository.Interfaces.BloodTestRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBBloodTestRepository implements BloodTestRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    BloodTestValidator bloodTestValidator;
    public DBBloodTestRepository(Properties properties,BloodTestValidator bloodTestValidator){
        this.bloodTestValidator=bloodTestValidator;
        this.dbUtils=new DBUtils(properties);
    }
    @Override
    public Optional<BloodTest> findOne(Integer integer) {

        String sql="SELECT * from View_Analiza where id_Analiza=?";
        if(integer==null)
        {
            logger.error("Cannot find an Analiza if id is null!");
            throw new IllegalArgumentException("Cannot find an Analiza if id is null!");
        }
        Connection con=dbUtils.getConnection();
        BloodTest bloodTest=null;
        try (PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setInt(1,integer);
            try(ResultSet resultSet=preparedStatement.executeQuery())
            {
                if(resultSet.next())
                {
                    bloodTest= DBGetters.getBloodTest(resultSet);
                    return Optional.of(bloodTest);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return Optional.empty();
    }
    public Iterable<BloodTest> findAllUtilitary(List<String > attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * from View_Analiza";
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
        if(values==null)
        {
            values=new ArrayList<>();
        }
        List<BloodTest> bloodTests=new ArrayList<>();

        try (PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            for(int i=0;i<values.size();i++)
            {
                preparedStatement.setObject(i+1,values.get(i));
            }
            try(ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    BloodTest bloodTest= DBGetters.getBloodTest(resultSet);
                    bloodTests.add(bloodTest);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return null;
    }
    @Override
    public Iterable<BloodTest> findAll() {

        return findAllUtilitary(null,null);
    }

    @Override
    public Optional<BloodTest> save(BloodTest entity) {

        if(entity==null)
        {
            logger.error("Cannot save blood test if entity is null!");
            throw new IllegalArgumentException("Cannot save blood test if entity is null!");
        }
        if(bloodTestValidator!=null)
        {
            bloodTestValidator.validate(entity);
        }
        String sql="INSERT INTO Analiza(id, nume, cale, id_informatiimedicale) VALUES(?,?,?,?)";
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.setString(2,entity.getName());
            preparedStatement.setString(3,entity.getPath());
            preparedStatement.setInt(4,entity.getMedicalInfo() );
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<BloodTest> delete(BloodTest entity) {

        if(entity==null)
        {
            logger.error("Cannot delete blood test if entity is null!");
            throw new IllegalArgumentException("Cannot delete blood test if entity is null!");
        }
        String sql="DELETE FROM Analiza WHERE id=?";
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<BloodTest> update(BloodTest entity) {

        if(entity==null)
        {
            logger.error("Cannot update blood test if entity is null!");
            throw new IllegalArgumentException("Cannot update blood test if entity is null!");
        }
        if(bloodTestValidator!=null)
        {
            bloodTestValidator.validate(entity);
        }
        String sql="UPDATE Analiza SET nume=?, cale=?, id_informatiimedicale=? WHERE id=?";
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getPath());
            preparedStatement.setInt(3,entity.getMedicalInfo());
            preparedStatement.setInt(4,entity.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return Optional.of(entity);
    }
}
