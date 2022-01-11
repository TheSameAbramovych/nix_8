package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.jpa.JpaConfig;
import ua.com.alevel.persistence.DataTableRequest;
import ua.com.alevel.persistence.DataTableResponse;
import ua.com.alevel.persistence.entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDaoImpl implements StudentDao {

    private static final String COUNT = "SELECT COUNT(*) FROM students";
    private static final String FIND_ALL_QUERY = "SELECT * FROM students ORDER BY :sort :order   LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE students SET first_name = ?, last_name = ?, age = ?, email = ?  WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM students WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO students VALUES(default, ?,?,?,?)";
    private static final String FIND_STUDENT_BY_ID_QUERY = "SELECT * FROM students WHERE id = ";
    private static final String FIND_BY_GROUP_ID_QUERY = """
            SELECT * FROM students
                     JOIN students_to_groups ON students.id = students_to_groups.student_id
            WHERE students_to_groups.group_id = ?
            """;

    private final JpaConfig jpaConfig;

    public StudentDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }


    @Override
    public void create(Student entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_QUERY)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public DataTableResponse<Student> findByGroup(Long groupId) {
        List<Student> students = new ArrayList<>();

        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(FIND_BY_GROUP_ID_QUERY)) {
            preparedStatement.setLong(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(convertResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Student> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(students);
        return tableResponse;
    }

    @Override
    public void update(Student entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Student findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_STUDENT_BY_ID_QUERY + id)) {
            while (resultSet.next()) {
                return convertResultSetToStudent(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        List<Student> students = new ArrayList<>();

        String findAllQuery = FIND_ALL_QUERY.replace(":sort", request.getSort()).replace(":order", request.getOrder());
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(findAllQuery)) {
            preparedStatement.setInt(1, request.getPageSize());
            preparedStatement.setInt(2, (request.getCurrentPage() - 1) * request.getPageSize());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(convertResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Student> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(students);
        return tableResponse;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(COUNT)) {
            while (resultSet.next()) {
                return resultSet.getLong("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private Student convertResultSetToStudent(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");

        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setAge(age);
        return student;
    }


    private static class StudentResultSet {

        private final Student student;

        private StudentResultSet(Student student) {
            this.student = student;
        }

        public Student getStudent() {
            return student;
        }

    }
}
