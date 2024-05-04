package bpos.repository.Implementations;

import bpos.model.Student;
import bpos.model.Validators.Implementation.StudentValidator;
import bpos.repository.Interfaces.StudentRepository;
import bpos.repository.Utils.DBGetters;
import bpos.repository.Utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBStudentRepository implements StudentRepository {
    private DBUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private final StudentValidator   studentValidator;

    public DBStudentRepository(Properties properties, StudentValidator studentValidator) {
        this.dbUtils = new DBUtils(properties);
        this.studentValidator = studentValidator;
    }

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
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Student entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Student> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public Iterable<Student> findByLastName(String lastName) {
        return null;
    }

    @Override
    public Iterable<Student> findByCnp(String cnp) {
        return null;
    }

    @Override
    public Student findByEmail(String email) {
        return null;
    }

    @Override
    public Iterable<Student> findByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Student findByUsername(String username) {
        return null;
    }
}
