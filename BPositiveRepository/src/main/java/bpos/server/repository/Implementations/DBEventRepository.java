package bpos.repository.Implementations;

import bpos.model.Event;
import bpos.model.Validators.Implementation.EventValidator;
import bpos.repository.Interfaces.EventRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBEventRepository implements EventRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private EventValidator eventValidator;

    public DBEventRepository(Properties properties, EventValidator eventValidator) {
        this.dbUtils = new DBUtils(properties);
        this.eventValidator = eventValidator;
    }

    private Iterable<Event> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_Eveniment";
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
            values=new java.util.ArrayList<>();
        }
        List<Event> events=new java.util.ArrayList<>();
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
                    Event event= DBGetters.getEvent(resultSet);
                    events.add(event);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return events;
    }
    @Override
    public Iterable<Event> findByNume(String nume) {

        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("nume_Eveniment");
        values.add(nume);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Event> findByDataAnunt(LocalDate data) {

        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("data_Anunt_Eveniment");
        values.add(data.toString());
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Event> findByCentruId(Integer centruId) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("id_Centru");
        values.add(centruId);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Event> findByDataInceput(LocalDate data) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("data_Incepere_Eveniment");
        values.add(data.toString());
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Optional<Event> findOne(Integer integer) {

            List<String> attributes=new java.util.ArrayList<>();
            List<Object> values=new java.util.ArrayList<>();
            attributes.add("id_Eveniment");
            values.add(integer);
            Iterable<Event> events=findAllUtilitary(attributes,values);
            return events.iterator().hasNext()?Optional.of(events.iterator().next()):Optional.empty();
    }

    @Override
    public Iterable<Event> findAll() {

        return findAllUtilitary(null,new java.util.ArrayList<>());
    }
    @Override
    public Optional<Event> save(Event entity) {

        Connection con=dbUtils.getConnection();
        if(entity==null)
        {
            logger.traceExit("Entity must not be null");
            throw new IllegalArgumentException("Entity must not be null");

        }
        if(eventValidator!=null)
        {
            eventValidator.validate(entity);
        }
        try (java.sql.PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO Eveniment(nume,data_incepere,data_anunt,data_sfarsit,max_participanti,cerinte,descriere,id_Centru) VALUES (?,?,?,?,?,?,?,?)",java.sql.Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1,entity.getEventName());
            preparedStatement.setString(2,entity.getEventStartDate().toString());
            preparedStatement.setString(4,entity.getEventEndDate().toString());
            preparedStatement.setString(3,entity.getEventAnnouncementDate().toString());
            preparedStatement.setInt(5,entity.getMaxParticipants());
            preparedStatement.setString(7,entity.getEventDescription());
            preparedStatement.setString(6,entity.getEventRequirements());
            preparedStatement.setInt(8,entity.getCenter().getId());
            preparedStatement.executeUpdate();
            try(java.sql.ResultSet rs=preparedStatement.getGeneratedKeys())
            {
                if(rs.next())
                {
                    entity.setId(rs.getInt(1));
                }
            }
            return Optional.of(entity);

        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Event> delete(Event entity) {
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
        try (java.sql.PreparedStatement preparedStatement = con.prepareStatement("delete from Eveniment where id=?")) {
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
    public Optional<Event> update(Event entity) {

      Connection con=dbUtils.getConnection();
        if(entity==null)
        {
            String m="Entity must not be null";
            logger.traceExit(m);
            throw new IllegalArgumentException(m);
        }
        if(eventValidator!=null)
        {
            eventValidator.validate(entity);
        }
        int result=0;
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement("UPDATE Eveniment SET nume=?,data_incepere=?,data_sfarsit=?,data_Anunt=?,max_participanti=?,descriere=?,cerinte=?,id_Centru=? WHERE id=?"))
        {
            preparedStatement.setString(1,entity.getEventName());
            preparedStatement.setString(2,entity.getEventStartDate().toString());
            preparedStatement.setString(3,entity.getEventEndDate().toString());
            preparedStatement.setString(4,entity.getEventAnnouncementDate().toString());
            preparedStatement.setInt(5,entity.getMaxParticipants());
            preparedStatement.setString(6,entity.getEventDescription());
            preparedStatement.setString(7,entity.getEventRequirements());
            preparedStatement.setInt(8,entity.getCenter().getId());
            preparedStatement.setInt(9,entity.getId());
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
