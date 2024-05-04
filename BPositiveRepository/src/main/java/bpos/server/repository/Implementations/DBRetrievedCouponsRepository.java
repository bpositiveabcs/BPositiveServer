package bpos.repository.Implementations;

import bpos.model.RetrievedCoupons;
import bpos.model.Validators.Implementation.RetrievedCouponsValidator;
import bpos.repository.Interfaces.RetrievedCouponsRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBRetrievedCouponsRepository implements RetrievedCouponsRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private RetrievedCouponsValidator retrievedCouponsValidator;

    public DBRetrievedCouponsRepository(Properties properties, RetrievedCouponsValidator retrievedCouponsValidator) {
        this.dbUtils = new DBUtils(properties);
        this.retrievedCouponsValidator = retrievedCouponsValidator;
    }

    private Iterable<RetrievedCoupons> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_CupoaneRetrieved";
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
        List<RetrievedCoupons> retrievedCoupons=new java.util.ArrayList<>();
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
                    RetrievedCoupons retrievedCoupon= DBGetters.getRetrievedCoupons(resultSet);
                    retrievedCoupons.add(retrievedCoupon);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return retrievedCoupons;
    }
    @Override
    public Optional<RetrievedCoupons> findOne(Integer integer) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("id_CupoaneRetrieved");
        values.add(integer);
        Iterable<RetrievedCoupons> retrievedCoupons=findAllUtilitary(attributes,values);
        if(retrievedCoupons.iterator().hasNext())
        {
            return Optional.of(retrievedCoupons.iterator().next());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<RetrievedCoupons> findAll() {
        return findAllUtilitary(null,new java.util.ArrayList<>());
    }

    @Override
    public Optional<RetrievedCoupons> save(RetrievedCoupons entity) {
        if(entity==null)
        {
            throw new IllegalArgumentException("entity must not be null");
        }
        if(entity.getId()!=null)
        {
            return Optional.empty();
        }
        if(retrievedCouponsValidator!=null)
        {
            System.out.println("Validator is not null");
            retrievedCouponsValidator.validate(entity);
        }
        Connection connection=dbUtils.getConnection();
        String sql="INSERT INTO CupoaneRetrieved(id_persoana, id_cupon, preluat_la_data_de, expira_la, series_unique) VALUES (?,?,?,?,?)";
            try(java.sql.PreparedStatement preparedStatement=connection.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setInt(1,entity.getId_persoana());
                preparedStatement.setInt(2,entity.getCoupon().getId());
                preparedStatement.setString(3,entity.getReceivedDate().toString());
                preparedStatement.setString(4,entity.getExpirationDate().toString());
                preparedStatement.setString(5,entity.getSeries());
                preparedStatement.executeUpdate();
                return Optional.of(entity);
            }
            catch (java.sql.SQLException e)
            {
                logger.error(e);
            }

        return Optional.empty();
    }

    @Override
    public Optional<RetrievedCoupons> delete(RetrievedCoupons entity) {

        if(entity==null)
        {
            throw new IllegalArgumentException("entity must not be null");
        }
        Connection connection=dbUtils.getConnection();
        try(java.sql.PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM CupoaneRetrieved WHERE id=?"))
        {
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
            return Optional.of(entity);
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<RetrievedCoupons> update(RetrievedCoupons entity) {

        if(entity==null)
        {
            throw new IllegalArgumentException("entity must not be null");
        }
        if(retrievedCouponsValidator!=null)
        {
            retrievedCouponsValidator.validate(entity);
        }
        Connection connection=dbUtils.getConnection();
        try(java.sql.PreparedStatement preparedStatement=connection.prepareStatement("UPDATE CupoaneRetrieved SET preluat_la_data_de=?,expira_la=?,series_unique=? WHERE id=?"))
        {

            preparedStatement.setString(1,entity.getReceivedDate().toString());
            preparedStatement.setString(2,entity.getExpirationDate().toString());
            preparedStatement.setString(3,entity.getSeries());
            preparedStatement.setInt(4,entity.getId());
            preparedStatement.executeUpdate();
            return Optional.of(entity);
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<RetrievedCoupons> findByPersonId(int personId) {
        return findAllUtilitary(List.of("id_CupoaneRetrieved"),List.of(personId));
    }

    @Override
    public Iterable<RetrievedCoupons> findByCouponId(int couponId) {
        return findAllUtilitary(List.of("id_Cupon"),List.of(couponId));
    }

    @Override
    public Iterable<RetrievedCoupons> findByDate(String date) {

        return findAllUtilitary(List.of("preluat_la_data_de_CupoaneRetrieved"),List.of(date));
    }
}
