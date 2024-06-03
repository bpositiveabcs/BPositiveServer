package bpos.server.repository.Implementations;


import bpos.common.model.Student;
import bpos.common.model.Validators.Implementation.StudentValidator;
import bpos.server.repository.Interfaces.StudentRepository;
import bpos.server.repository.Utils.DBGetters;
import bpos.server.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBStudentRepository implements StudentRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private  StudentValidator studentValidator;

    public DBStudentRepository(Properties properties, StudentValidator studentValidator) {
        this.dbUtils = new DBUtils(properties);
        this.studentValidator = studentValidator;
    }
    public DBStudentRepository(){}

    private Iterable<Student> findAllUtilitary(List<String> attributes, List<Object> values)
    {
        Connection con=dbUtils.getConnection();
        String sql="SELECT * FROM View_Student";
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
        List<Student> students=new java.util.ArrayList<>();
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
                    Student student= DBGetters.getStudent(resultSet);
                    students.add(student);
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return students;
    }
    @Override
    public Optional<Student> findOne(Integer integer) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("ID_Student");
        values.add(integer);
        Iterable<Student> students=findAllUtilitary(attributes,values);
        if(students.iterator().hasNext())
        {
            return Optional.of(students.iterator().next());
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Student> findAll() {
        return findAllUtilitary(null,new java.util.ArrayList<>());
    }

    @Override
    public Optional<Student> save(Student entity) {

        logger.traceEntry("saving student {}",entity);
        studentValidator.validate(entity);
        Connection con=dbUtils.getConnection();
        String sql="INSERT INTO Student (id,departament,facultate,grupa,an) VALUES (?,?,?,?,?)";
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.setString(2,entity.getDepartment());
            preparedStatement.setString(3,entity.getFaculty());
            preparedStatement.setString(4,entity.getGroup());
            preparedStatement.setInt(5,entity.getYear());

            preparedStatement.executeUpdate();
        }

        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Student entity) {
        logger.traceEntry("deleting student {}",entity);
        Connection con=dbUtils.getConnection();
        String sql="DELETE FROM Student WHERE id=?";
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return Optional.empty();

    }

    @Override
    public Optional<Student> update(Student entity) {

logger.traceEntry("updating student {}",entity);
        studentValidator.validate(entity);
        Connection con=dbUtils.getConnection();
        String sql="UPDATE Student SET departament=?,facultate=?,grupa=?,an=? WHERE id=?";
        try(java.sql.PreparedStatement preparedStatement=con.prepareStatement(sql))
        {
            preparedStatement.setString(1,entity.getDepartment());
            preparedStatement.setString(2,entity.getFaculty());
            preparedStatement.setString(3,entity.getGroup());
            preparedStatement.setInt(4,entity.getYear());
            preparedStatement.setInt(5,entity.getId());
            preparedStatement.executeUpdate();
        }
        catch (java.sql.SQLException e)
        {
            logger.error(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Student> findByFirstName(String firstName) {

        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("prenume_DatePersonale");
        values.add(firstName);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Student> findByLastName(String lastName) {

        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("nume_DatePersonale");
        values.add(lastName);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Iterable<Student> findByCnp(String cnp) {

        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("cnp_DatePersonale");
        values.add(cnp);
        return findAllUtilitary(attributes,values);
    }

    @Override
    public Student findByEmail(String email) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("email_LogInInfo");
        values.add(email);
        Iterable<Student> students=findAllUtilitary(attributes,values);
        if(students.iterator().hasNext())
        {
            return students.iterator().next();
        }
        throw new RuntimeException("Student not found");

    }

    @Override
    public Iterable<Student> findByPhoneNumber(String phoneNumber) {

            List<String> attributes=new java.util.ArrayList<>();
            List<Object> values=new java.util.ArrayList<>();
            attributes.add("telefon_DatePersonale");
            values.add(phoneNumber);
            return findAllUtilitary(attributes,values);
    }

    @Override
    public Student findByUsername(String username) {
        List<String> attributes=new java.util.ArrayList<>();
        List<Object> values=new java.util.ArrayList<>();
        attributes.add("username_LogInInfo");
        values.add(username);
        Iterable<Student> students=findAllUtilitary(attributes,values);
        if(students.iterator().hasNext())
        {
            return students.iterator().next();
        }
        throw new RuntimeException("Student not found");
    }
}
