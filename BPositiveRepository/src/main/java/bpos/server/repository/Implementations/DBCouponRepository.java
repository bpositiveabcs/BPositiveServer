package bpos.repository.Implementations;

import bpos.model.Coupon;
import bpos.model.Validators.Implementation.CouponValidator;
import bpos.repository.Interfaces.CouponRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBCouponRepository implements CouponRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private CouponValidator couponValidator;
    public DBCouponRepository(Properties properties,CouponValidator couponValidator){
        this.couponValidator=couponValidator;
        this.dbUtils=new DBUtils(properties);
    }

    private Iterable<Coupon> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM VIEW_Cupon";
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
        List<Coupon> coupons=new java.util.ArrayList<>();
        if (values==null)
        {
            values=new java.util.ArrayList<>();
        }
        try (PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            for(int i=0;i<values.size();i++)
            {
                preparedStatement.setObject(i+1,values.get(i));
            }
            try(java.sql.ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    Coupon coupon= DBGetters.getCoupon(resultSet);
                    coupons.add(coupon);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error finding all elements DB"+ e);
        }
        return coupons;
    }
    @Override
    public Iterable<Coupon> findByCodeCoupon(String code) {

        return findAllUtilitary(List.of("serieCupon_Cupon"),List.of(code));
    }

    @Override
    public Iterable<Coupon> findByProvider(String provider){
        return findAllUtilitary(List.of("provider_Cupon"),List.of(provider));
    }

    @Override
    public Iterable<Coupon> findByNume(String nume) {
        return findAllUtilitary(List.of("nume_Cupon"),List.of(nume));
    }

    @Override
    public Iterable<Coupon> findByEndDate(LocalDate endDate) {

        return findAllUtilitary(List.of("unavailable_to_claim_from_Cupon"),List.of(endDate));
    }

    @Override
    public Optional<Coupon> findOne(Integer integer) {

        List<Coupon> cupons=(List<Coupon>)findAllUtilitary(List.of("id_Cupon"),List.of(integer));
        if(cupons.size()==0)
        {
            return Optional.empty();
        }
        return Optional.of(cupons.get(0));
    }

    @Override
    public Iterable<Coupon> findAll() {

        return findAllUtilitary(null,null);
    }

    @Override
    public Optional<Coupon> save(Coupon entity) {
        if(couponValidator!=null)
        couponValidator.validate(entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO Cupon(puncte_necesare,provider,oferta,timp_valabilitate,unavailable_to_claim_from,nume,serieCupon) VALUES (?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1,entity.getNecessaryPoints());
            preparedStatement.setString(2,entity.getProvider());
            preparedStatement.setString(3,entity.getOffer());
            preparedStatement.setInt(4,entity.getValidityPeriod());
            preparedStatement.setString(5, String.valueOf(entity.getUnavailableToClaimFrom()));
            preparedStatement.setString(6,entity.getName());
            preparedStatement.setString(7,entity.getSeries());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error saving element DB"+ e);
        }
        return Optional.empty();

    }

    @Override
    public Optional<Coupon> delete(Coupon entity) {

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
        try (PreparedStatement preparedStatement=con.prepareStatement("delete from Cupon where id=?"))
        {
            preparedStatement.setInt(1,entity.getId());
            result=preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.error(e);
            System.out.println("Error deleting element in  DB"+ e);
        }
        if(result==0)
        {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Coupon> update(Coupon entity) {
        if (entity == null || entity.getId() == null) {
            String m = "Cannot update spectacol if entity is null!\n";
            logger.traceExit("Sent error from repo {}", m);
            throw new IllegalArgumentException(m);
        }
        if (couponValidator != null) {
            couponValidator.validate(entity);
        }
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE Cupon SET puncte_necesare=?,provider=?,oferta=?,timp_valabilitate=?,unavailable_to_claim_from=? WHERE id=?")) {
            preparedStatement.setInt(1, entity.getNecessaryPoints());
            preparedStatement.setString(2, entity.getProvider());
            preparedStatement.setString(3, entity.getOffer());
            preparedStatement.setInt(4, entity.getValidityPeriod());
            preparedStatement.setString(5, String.valueOf(entity.getUnavailableToClaimFrom()));
            preparedStatement.setInt(6, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error updating element in  DB" + e);
        }
        return Optional.empty() ;
    }
}
