package bpos.repository.Implementations;

import bpos.model.Center;
import bpos.model.Validators.Implementation.CenterValidator;
import bpos.repository.Interfaces.CenterRepository;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBCenterRepository implements CenterRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    CenterValidator centerValidator;
    public DBCenterRepository(Properties properties, CenterValidator centerValidator){
        this.centerValidator=centerValidator;
        this.dbUtils=new DBUtils(properties);
    }
    private Iterable<Center> findAllUtilitary(List<String > attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        List<Center> centers=new java.util.ArrayList<>();
        String sql="SELECT * from View_Centru";
        if(values==null)
        {
            values=List.of();
        }
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
                    Center center= bpos.repository.Utils.DBGetters.getCenter(resultSet);
                    centers.add(center);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return centers;
    }
    @Override
    public Center findByUsername(String username) {
        List<Center>  list= (List<Center>) findAllUtilitary(List.of("username_LogInInfo") ,List.of(username));
        return list.isEmpty()?null:list.get(0);
    }

    @Override
    public Center findByEmail(String email) {

        List<Center>  list= (List<Center>) findAllUtilitary(List.of("email_LogInInfo"),List.of(email));
        return list.isEmpty()?null:list.get(0);
    }


    @Override
    public Iterable<Center> findByName(String name) {

        return findAllUtilitary(List.of("nume_Centru"),List.of(name));
    }

    @Override
    public Optional<Center> findOne(Integer integer) {

        List<Center> list= (List<Center>) findAllUtilitary(List.of("id_Centru") ,List.of(integer));
        return list.isEmpty()?Optional.empty():Optional.of(list.get(0));
    }

    @Override
    public Iterable<Center> findAll(){

        return findAllUtilitary(null,null);
    }

    @Override
    public Optional<Center> save(Center entity) {
        if(entity==null)
        {
            String m="Cannot save center if entity is null!\n";
            logger.traceExit("Sent error from repo {}",m);
            throw new IllegalArgumentException(m);
        }
        if(centerValidator!=null)
        {
            centerValidator.validate(entity);
        }
        int result=0;
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO Centru(nume,informatii,adresa,credentiale_logare) VALUES (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1,entity.getCenterName());
            preparedStatement.setString(2,entity.getInstitutionDetails());
            preparedStatement.setString(3,entity.getAddress());
            preparedStatement.setInt(4,entity.getLogInfo().getId());


            result=preparedStatement.executeUpdate();
            try(ResultSet generatedKeys=preparedStatement.getGeneratedKeys())
            {
                if(generatedKeys.next())
                {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return Optional.of(entity);

    }

    @Override
    public Optional<Center> delete(Center entity) {

        if(entity==null)
        {
            logger.error("Cannot delete center if entity is null!");
            throw new IllegalArgumentException("Cannot delete center if entity is null!");
        }
        String sql="DELETE FROM Centru WHERE credentiale_logare=?";
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
    public Optional<Center> update(Center entity) {

        if(entity==null)
        {
            String m="Cannot update center if entity is null!\n";
            logger.traceExit("Sent error from repo {}",m);
            throw new IllegalArgumentException(m);
        }
        if(centerValidator!=null)
        {
            centerValidator.validate(entity);
        }
        int result=0;
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preparedStatement=con.prepareStatement("UPDATE Centru SET nume=?,informatii=?,adresa=? WHERE credentiale_logare=?"))
        {
            preparedStatement.setString(1,entity.getCenterName());
            preparedStatement.setString(2,entity.getInstitutionDetails());
            preparedStatement.setString(3,entity.getAddress());
            preparedStatement.setInt(4,entity.getLogInfo().getId());
            result=preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return Optional.of(entity);
    }
}
