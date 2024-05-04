package bpos.repository.Implementations;

import bpos.model.DonationType;
import bpos.model.Validators.Implementation.DonationTypeValidator;
import bpos.repository.Interfaces.DonationTypeRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.*;

public class DBDonationTypeRepository implements DonationTypeRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private DonationTypeValidator donationTypeValidator;
    public DBDonationTypeRepository(Properties properties,DonationTypeValidator donationTypeValidator){
        this.donationTypeValidator=donationTypeValidator;
        this.dbUtils=new DBUtils(properties);
    }
    private Iterable<DonationType> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection connection=dbUtils.getConnection();
        String sql="SELECT * FROM View_TipDonatie";
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
        List<DonationType> donationTypes=new ArrayList<>();
        if(values==null)
        {
            values=new ArrayList<>();
        }
        try (java.sql.PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
            for(int i=0;i<values.size();i++)
            {
                preparedStatement.setObject(i+1,values.get(i));
            }
            try(java.sql.ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    DonationType donationType= DBGetters.getDonationType(resultSet);
                    donationTypes.add(donationType);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return donationTypes;
    }
    @Override
    public Optional<DonationType> findOne(Integer integer) {

        List<String> attributes= Collections.singletonList("ID_TipDonatie");
        List<Object> values=Collections.singletonList(integer);
        Iterable<DonationType> donationTypes=findAllUtilitary(attributes,values);
        return donationTypes.iterator().hasNext()?Optional.of(donationTypes.iterator().next()):Optional.empty();
    }

    @Override
    public Iterable<DonationType> findAll() {
        return findAllUtilitary(null,new ArrayList<>());
    }

    @Override
    public Optional<DonationType> save(DonationType entity) {
        if(donationTypeValidator!=null)
        {
           donationTypeValidator.validate(entity);
        }
        Connection connection=dbUtils.getConnection();
        String sql="INSERT INTO TipDonatie (nume,interval_asteptare) VALUES (?,?)";
        try(java.sql.PreparedStatement preparedStatement=connection.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setInt(2,entity.getWaitingInterval());
            preparedStatement.executeUpdate();
            try(java.sql.ResultSet resultSet=preparedStatement.getGeneratedKeys())
            {
                if(resultSet.next())
                {
                    entity.setId(resultSet.getInt(1));
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
            System.out.println("Error saving element DB"+ e);
        }
        return Optional.of(entity);

    }

    @Override
    public Optional<DonationType> delete(DonationType entity) {
        return Optional.empty();
    }

    @Override
    public Optional<DonationType> update(DonationType entity) {
        return Optional.empty();
    }
}
