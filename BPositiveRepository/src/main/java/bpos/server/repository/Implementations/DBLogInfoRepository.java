package bpos.repository.Implementations;

import bpos.model.LogInfo;
import bpos.model.Validators.Implementation.LogInfoValidator;
import bpos.repository.Interfaces.LogInfoRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBLogInfoRepository implements LogInfoRepository {
    private DBUtils dbUtils;

    public DBLogInfoRepository(Properties properties, LogInfoValidator logInfoValidator) {
        this.dbUtils = new DBUtils(properties);
        this.logInfoValidator = logInfoValidator;
    }

    private static final Logger logger= LogManager.getLogger();
    private LogInfoValidator logInfoValidator;
    private Iterable<LogInfo> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_LogInInfo";
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
        List<LogInfo> logInfos=new java.util.ArrayList<>();
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
                    LogInfo logInfo= DBGetters.getLogInfo(resultSet);
                    logInfos.add(logInfo);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return logInfos;
    }
    @Override
    public Optional<LogInfo> findOne(Integer integer) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("id_LogInInfo");
        values.add(integer);
        Iterable<LogInfo> logInfos=findAllUtilitary(attributes,values);
        if(logInfos.iterator().hasNext())
        {
            return Optional.of(logInfos.iterator().next());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<LogInfo> findAll() {
        return findAllUtilitary(null,new java.util.ArrayList<>());
    }

    @Override
    public Optional<LogInfo> save(LogInfo entity) {

    if(entity==null)
            {
                throw new IllegalArgumentException("Entity must not be null");
            }
    if(logInfoValidator!=null)
            logInfoValidator.validate(entity);
            Connection con=dbUtils.getConnection();
            try (java.sql.PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO LogInInfo(username,password,email,seed) VALUES (?,?,?,?)"))
            {
                preparedStatement.setString(1,entity.getUsername());
                preparedStatement.setString(2,entity.getPassword());
                preparedStatement.setString(3,entity.getEmail());
                preparedStatement.setString(4,entity.getSeed());
                preparedStatement.executeUpdate();
            }
            catch (java.sql.SQLException e)
            {
                logger.error(e);
                System.out.println("Error saving entity DB"+ e);
            }
            return Optional.empty();
    }

    @Override
    public Optional<LogInfo> delete(LogInfo entity) {
       if(entity==null)
       {
              throw new IllegalArgumentException("Entity must not be null");

       }
         Connection con=dbUtils.getConnection();
            try (java.sql.PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM LogInInfo WHERE id=?"))
            {
                preparedStatement.setInt(1,entity.getId());
                preparedStatement.executeUpdate();
                return Optional.of(entity);
            }
            catch (java.sql.SQLException e)
            {
                logger.error(e);
                System.out.println("Error deleting entity DB"+ e);
            }
            return Optional.empty();
    }

    @Override
    public Optional<LogInfo> update(LogInfo entity) {
        if(entity==null)
        {
            throw new IllegalArgumentException("Entity must not be null");
        }
        if(logInfoValidator!=null)
        logInfoValidator.validate(entity);
        Connection con=dbUtils.getConnection();
        try (java.sql.PreparedStatement preparedStatement=con.prepareStatement("UPDATE LogInInfo SET username=?,password=?,email=?,seed=? WHERE id=?"))
        {
            preparedStatement.setString(1,entity.getUsername());
            preparedStatement.setString(2,entity.getPassword());
            preparedStatement.setString(3,entity.getEmail());
            preparedStatement.setString(4,entity.getSeed());
            preparedStatement.setInt(5,entity.getId());
            preparedStatement.executeUpdate();
            return Optional.of(entity);
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
            System.out.println("Error updating entity DB"+ e);
        }
        return Optional.empty();
    }

    @Override
    public LogInfo findByUsername(String username) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("username_LogInInfo");
        values.add(username);
        Iterable<LogInfo> logInfos=findAllUtilitary(attributes,values);
        if(logInfos.iterator().hasNext())
        {
            return logInfos.iterator().next();
        }
        return null;
    }

    @Override
    public LogInfo findByEmail(String email) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("email_LogInInfo");
        values.add(email);
        Iterable<LogInfo> logInfos=findAllUtilitary(attributes,values);
        if(logInfos.iterator().hasNext())
        {
            return logInfos.iterator().next();
        }
        return null;
    }
}
