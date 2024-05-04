package bpos.repository.Implementations;

import bpos.model.Address;
import bpos.model.Validators.Implementation.AddressValidator;
import bpos.repository.Exceptions.RepositoryException;
import bpos.repository.Interfaces.AddressRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBAddressRepository implements AddressRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private AddressValidator addressValidator;
    public DBAddressRepository(Properties properties,AddressValidator addressValidator){
        dbUtils=new DBUtils(properties);
        this.addressValidator=addressValidator;
    }
    private Iterable<Address> findAllUtilitary(List<String > attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        List<Address> addresses=new ArrayList<>();
        if (attributes==null)
        {
            attributes=new ArrayList<>();
        }
        if(values==null)
        {
            values=new ArrayList<>();
        }
        String sql="SELECT * from View_Adresa";
        if(attributes!=null && attributes.size()>0)
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
                    Address address= DBGetters.getAddress(resultSet);
                    addresses.add(address);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        logger.traceExit(addresses);
        return addresses;
    }
    @Override
    public Optional<Address> findOne(Integer integer) {
        if(integer==null)
        {
            throw new IllegalArgumentException("Id must not be null");
        }
        if(integer<0)
        {
            throw new IllegalArgumentException("Id must be positive");
        }
        Connection con=dbUtils.getConnection();
        Address address=null;
        try (PreparedStatement preparedStatement=con.prepareStatement("SELECT * from View_Adresa where id_Adresa=?"))
        {
            preparedStatement.setInt(1,integer);
            try(ResultSet resultSet=preparedStatement.executeQuery())
            {
                if(resultSet.next())
                {
                    address= DBGetters.getAddress(resultSet);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }     logger.traceExit(address);
        return Optional.ofNullable(address);

    }

    @Override
    public Iterable<Address> findAll() {

        return findAllUtilitary(null,null);
    }

    @Override
    public Optional<Address> save(Address entity) {

        logger.traceEntry("Saving task {} ",entity);
        if(entity==null)
        {
            String m="Cannot save spectacol if entity is null!\n";
            logger.traceExit("Sent error from repo {}",m);
            throw new RepositoryException(m);

        }
        int result=0;
        Connection con=dbUtils.getConnection();
        if(addressValidator!=null)
            addressValidator.validate(entity);
        try (PreparedStatement preparedStatement=con.prepareStatement("insert into Adresa(tara,judet,localitate,strada,numar,bloc,etaj,apartament) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1,entity.getCountry());
            preparedStatement.setString(2,entity.getCounty());
            preparedStatement.setString(3,entity.getCity());
            preparedStatement.setString(4,entity.getStreet());
            preparedStatement.setString(5,entity.getNumberStreet());
            preparedStatement.setString(6,entity.getBlock());
            preparedStatement.setInt(7,entity.getFloor());
            preparedStatement.setString(8,entity.getApartment());
            result=preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                entity.setId(rs.getInt(1));
            }

        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error saving element in  DB"+ e);
        }
        logger.traceExit();
        if(result==0)
        {
            return null;
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Address> delete(Address entity) {
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
        Connection con=dbUtils.getConnection();
        int result=0;
        try (PreparedStatement preparedStatement=con.prepareStatement("delete from Adresa where id=?"))
        {
            preparedStatement.setInt(1,entity.getId());
            result=preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error deleting element in  DB"+ e);
        }   logger.traceExit(entity);
        if(result==0)
        {
            return Optional.empty();
        }
        return Optional.of(entity);

    }

    @Override
    public Optional<Address> update(Address entity) {

logger.traceEntry("Update task {} ",entity);
        if(entity==null || entity.getId()==null)
        {
            String m="Cannot update spectacol if entity is null!\n";
            logger.traceExit("Sent error from repo {}",m);
            throw new RepositoryException(m);

        }
        int result=0;
        Connection con=dbUtils.getConnection();
        if(addressValidator!=null)
            addressValidator.validate(entity);
        try (PreparedStatement preparedStatement=con.prepareStatement("update Adresa set tara=?,judet=?,localitate=?,strada=?,numar=?,bloc=?,etaj=?,apartament=? where id=?"))
        {
            preparedStatement.setString(1,entity.getCountry());
            preparedStatement.setString(2,entity.getCounty());
            preparedStatement.setString(3,entity.getCity());
            preparedStatement.setString(4,entity.getStreet());
            preparedStatement.setString(5,entity.getNumberStreet());
            preparedStatement.setString(6,entity.getBlock());
            preparedStatement.setInt(7,entity.getFloor());
            preparedStatement.setString(8,entity.getApartment());
            preparedStatement.setInt(9,entity.getId());
            result=preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error updating element in  DB"+ e);
        }
        logger.traceExit();
        if(result==0)
        {
            return Optional.empty();
        }
        return Optional.of(entity);
    }
}
