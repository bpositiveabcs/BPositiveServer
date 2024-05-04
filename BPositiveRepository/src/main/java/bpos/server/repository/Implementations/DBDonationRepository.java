package bpos.repository.Implementations;

import bpos.model.Donation;
import bpos.model.Validators.Implementation.DonationValidator;
import bpos.repository.Exceptions.RepositoryException;
import bpos.repository.Interfaces.DonationRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBDonationRepository implements DonationRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private DonationValidator donationValidator;

    public DBDonationRepository(Properties properties, DonationValidator donationValidator) {
        this.dbUtils = new DBUtils(properties);
        this.donationValidator = donationValidator;
    }

    private Iterable<Donation> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_Donatie";
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
        List<Donation> donations=new ArrayList<>();
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
                    Donation donation= DBGetters.getDonation(resultSet);
                    donations.add(donation);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return donations;
    }
    @Override
    public Iterable<Donation> findByTipDonatie(String tipDonatie) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("nume_TipDonatie");
        values.add(tipDonatie);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Donation> findByIdTipDonatie(Integer idTipDonatie) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("id_TipDonatie");
        values.add(idTipDonatie);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Optional<Donation> findOne(Integer integer) {

        List<String> attributes=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        attributes.add("id_Donatie");
        values.add(integer);
        Iterable<Donation> donations=findAllUtilitary(attributes,values);
        if(donations.iterator().hasNext())
        {
            return Optional.of(donations.iterator().next());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Donation> findAll() {

        return findAllUtilitary(null,new ArrayList<>());
    }

    @Override
    public Optional<Donation> save(Donation entity) {
        logger.traceEntry("saving donation {}",entity);
        if(entity==null)
        {
            String m="Entity must not be null";
            logger.traceExit(m);
            throw new RepositoryException(m);
        }
        int result=0;
        Connection con=dbUtils.getConnection();
        if(donationValidator!=null)
        {
            donationValidator.validate(entity);
        }
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO Donatie(id_TipDonatie,puncte) VALUES (?,?)"))
        {
            preparedStatement.setInt(1,entity.getDonationType().getId());
            preparedStatement.setInt(2,entity.getPoints());
            result=preparedStatement.executeUpdate();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        if(result==0)
        {
            logger.traceExit("No donation saved");
            return Optional.empty();
        }
        logger.traceExit("Donation saved");
        return Optional.of(entity);
    }

    @Override
    public Optional<Donation> delete(Donation entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity must have an id");
        }
        if (entity.getId() < 0) {
            throw new IllegalArgumentException("Entity id must be positive");
        }
        Connection con = dbUtils.getConnection();
        int result = 0;
        try (java.sql.PreparedStatement preparedStatement = con.prepareStatement("delete from Donatie where id=?")) {
            preparedStatement.setInt(1, entity.getId());
            result = preparedStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            logger.error(e);
        }
        if (result == 0) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Donation> update(Donation entity) {

        logger.traceEntry("Update task {} ",entity);
        if(entity==null || entity.getId()==null)
        {
            String m="Cannot update spectacol if entity is null!\n";
            logger.traceExit("Sent error from repo {}",m);
            throw new RepositoryException(m);

        }
        int result=0;
        Connection con=dbUtils.getConnection();
        if(donationValidator!=null)
            donationValidator.validate(entity);
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement("UPDATE Donatie SET id_TipDonatie=?,puncte=? WHERE id=?"))
        {
            preparedStatement.setInt(1,entity.getDonationType().getId());
            preparedStatement.setInt(2,entity.getPoints());
            preparedStatement.setInt(3,entity.getId());
            result=preparedStatement.executeUpdate();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        if(result==0)
        {
            return Optional.empty();
        }
        return Optional.of(entity);
    }
}
