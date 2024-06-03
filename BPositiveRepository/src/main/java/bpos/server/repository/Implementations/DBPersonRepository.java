package bpos.server.repository.Implementations;

import bpos.common.model.*;
import bpos.common.model.Validators.Implementation.PersonValidator;
import bpos.server.repository.Interfaces.PersonRepository;
import bpos.server.repository.Utils.DBGetters;
import bpos.server.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class DBPersonRepository implements PersonRepository {
    private DBUtils dbUtils;
    private static final Logger logger = LoggerFactory.getLogger(DBPersonRepository.class);
    private PersonValidator personValidator;

    public DBPersonRepository(Properties properties, PersonValidator personValidator) {
        this.dbUtils = new DBUtils(properties);
        this.personValidator = personValidator;
    }

    public DBPersonRepository() {}

    private Iterable<Person> findAllUtilitary(List<String> attributes, List<Object> values) {
        Connection con = dbUtils.getConnection();
        String sql = "SELECT * FROM View_Persoana";
        if (attributes != null && !attributes.isEmpty()) {
            sql += " WHERE ";
            for (int i = 0; i < attributes.size(); i++) {
                sql += attributes.get(i) + "=?";
                if (i < attributes.size() - 1) {
                    sql += " AND ";
                }
            }
        }
        Map<Integer, Person> persons = new HashMap<>();
        try (java.sql.PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Person person = DBGetters.getPerson(resultSet);
                    if (!persons.containsKey(person.getId())) {
                        persons.put(person.getId(), person);
                    } else {
                        person = addToMedicalInfo(person, persons.get(person.getId()));
                        persons.put(person.getId(), person);
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
        List<Person> personsList = new ArrayList<>(persons.values());
        System.out.println(personsList);
        addEvents(personsList);
        addDonations(personsList);
        return personsList;
    }

    private List<Donation> findDonations(Integer id_persoana) {
        List<Donation> donations = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        String sql = "SELECT * FROM View_DonationParticipation WHERE id_persoana=?";
        try (java.sql.PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_persoana);
            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Donation donation = DBGetters.getDonation(resultSet);
                    donations.add(donation);
                }
            }
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
        return donations;
    }

    private List<Event> findEvents(Integer id_persoana) {
        List<Event> events = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        String sql = "SELECT * FROM View_EventParticipation WHERE id_persoana=?";
        try (java.sql.PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_persoana);
            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = DBGetters.getEvent(resultSet);
                    events.add(event);
                }
            }
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
        return events;
    }

    private List<Person> addDonations(List<Person> personsList) {
        if (personsList != null) {
            for (Person person : personsList) {
                List<Donation> donations = findDonations(person.getId());
                person.setDonations(donations);
            }
        }
        return personsList;
    }

    private List<Person> addEvents(List<Person> personsList) {
        if (personsList != null) {
            for (Person person : personsList) {
                List<Event> events = findEvents(person.getId());
                person.setEvents(events);
            }
        }
        return personsList;
    }

    private Person addToMedicalInfo(Person person, Person old_person) {
        if (person.getMedicalInfo() != null) {
            MedicalInfo medicalInfo = old_person.getMedicalInfo();
            List<BloodTest> bloodTests = old_person.getMedicalInfo().getMedicalHistory();
            BloodTest to_add = person.getMedicalInfo().getMedicalHistory().get(0);
            bloodTests.add(to_add);
            medicalInfo.setMedicalHistory(bloodTests);
            person.setMedicalInfo(medicalInfo);
            return person;
        }
        return old_person;
    }

    @Override
    public Optional<Person> findOne(Integer integer) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("ID_Persoana");
        values.add(integer);
        Iterable<Person> persons = findAllUtilitary(attributes, values);
        List<Person> personList = (List<Person>) persons;
        if (persons.iterator().hasNext()) {
            return Optional.of(personList.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Person> findAll() {
        return findAllUtilitary(null, new ArrayList<>());
    }

    @Override
    public Optional<Person> save(Person entity) {
        Connection connection = dbUtils.getConnection();
        if (entity == null) {
            logger.error("Entity is null");
            return Optional.empty();
        }
        if (personValidator != null) {
            personValidator.validate(entity);
        }
        String sql = "INSERT INTO Persoana (id,puncte,id_institutie) VALUES (?,?,?)";
        try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getPoints());
            if (entity.getInstitution() == null) {
                preparedStatement.setNull(3, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(3, entity.getInstitution().getId());
            }
            preparedStatement.executeUpdate();
            return Optional.of(entity);
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> delete(Person entity) {
        if (entity == null) {
            logger.error("Entity is null");
            return Optional.empty();
        }
        String sql = "DELETE FROM Persoana WHERE id=?";
        Connection connection = dbUtils.getConnection();

        try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();
            return Optional.of(entity);
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> update(Person entity) {
        if (entity == null) {
            logger.error("Entity is null");
            return Optional.empty();
        }
        if (personValidator != null) {
            personValidator.validate(entity);
        }
        String sql = "UPDATE Persoana SET puncte=?,id_institutie=? WHERE id=?";
        Connection connection = dbUtils.getConnection();
        Person found = findOne(entity.getId()).orElse(null);
        if (found == null) {
            logger.error("Entity not found");
            return Optional.empty();
        }

        try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getPoints());
            if (entity.getInstitution() == null) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, entity.getInstitution().getId());
            }
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
            entity = seeIfAnythingNeedsInserted(entity, found);
            return Optional.of(entity);
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    private Person seeIfAnythingNeedsInserted(Person entity, Person found) {
        if (entity.getEvents() != null) {
            for (Event event : entity.getEvents()) {
                if (!found.getEvents().contains(event)) {
                    insertEvent(entity.getId(), event.getId());
                }
            }
        }
        if (entity.getDonations() != null) {
            for (Donation donation : entity.getDonations()) {
                if (!found.getDonations().contains(donation)) {
                    insertDonation(entity.getId(), donation.getId());
                }
            }
        }
        return entity;
    }

    private void insertDonation(Integer id, Integer id1) {
        Connection connection = dbUtils.getConnection();
        String sql = "INSERT INTO InscriereDonatie (id_persoana,id_donatie) VALUES (?,?)";
        try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id1);
            preparedStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private void insertEvent(Integer id, Integer id1) {
        Connection connection = dbUtils.getConnection();
        String sql = "INSERT INTO InscriereEveniment (id_persoana,id_eveniment) VALUES (?,?)";
        try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id1);
            preparedStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Iterable<Person> findByFirstName(String firstName) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("prenume_DatePersonale");
        values.add(firstName);
        return findAllUtilitary(attributes, values);
    }

    @Override
    public Iterable<Person> findByLastName(String lastName) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("nume_DatePersonale");
        values.add(lastName);
        return findAllUtilitary(attributes, values);
    }

    @Override
    public Iterable<Person> findByCnp(String cnp) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("cnp_DatePersonale");
        values.add(cnp);
        return findAllUtilitary(attributes, values);
    }

    @Override
    public Person findByEmail(String email) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("email_LogInInfo");
        values.add(email);
        List<Person> persons = (List<Person>) findAllUtilitary(attributes, values);
        if (persons.size() != 0) {
            return persons.get(0);
        }

        return null;
    }

    @Override
    public Iterable<Person> findByPhoneNumber(String phoneNumber) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("telefon_DatePersonale");
        values.add(phoneNumber);
        return findAllUtilitary(attributes, values);
    }

    @Override
    public Person findByUsername(String username) {
        List<String> attributes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        attributes.add("username_LogInInfo");
        values.add(username);
        Iterable<Person> persons = findAllUtilitary(attributes, values);
        if (persons != null && persons.iterator().hasNext()) {
            return persons.iterator().next();
        }
        return null;
    }
}
