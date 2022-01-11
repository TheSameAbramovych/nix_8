package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.GroupDao;
import ua.com.alevel.jpa.JpaConfig;
import ua.com.alevel.persistence.DataTableRequest;
import ua.com.alevel.persistence.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDaoImpl implements GroupDao {

    public static final String COUNT = "SELECT COUNT(*) FROM students_group";
    public static final String FIND_ALL_QUERY = "SELECT * FROM students_group ORDER BY :sort :order LIMIT ? OFFSET ?";
    public static final String UPDATE_QUERY = "UPDATE students_group SET name = ?,headman = ?  WHERE id = ?";
    public static final String DELETE_QUERY = "DELETE FROM students_group WHERE id = ?";
    public static final String DELETE_STUDENT_QUERY = "DELETE FROM students_to_groups WHERE group_id = ? AND student_id = ?";
    public static final String ADD_STUDENT_QUERY = "INSERT INTO students_to_groups (group_id, student_id) VALUES(?, ?)";
    private static final String CREATE_QUERY = "INSERT INTO students_group VALUES(default, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM students_group WHERE id = ";
    private static final String FIND_BY_STUDENT_ID_QUERY = """
            SELECT * FROM students_group
                     JOIN students_to_groups ON students_group.id = students_to_groups.group_id
            WHERE students_to_groups.student_id = ?
            """;
    public static final String ADD_HEADMAN_QUERY = "INSERT INTO students_group (headman) VALUES(?)";


    private final JpaConfig jpaConfig;

    public GroupDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }


    @Override
    public void create(Group entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_QUERY)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getHeadman());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void update(Group entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getHeadman());
            preparedStatement.setLong(3, entity.getId());
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
    public Group findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_BY_ID_QUERY + id)) {
            while (resultSet.next()) {
                return convertResultSetToGroup(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        List<Group> groups = new ArrayList<>();
        String findAllQuery = FIND_ALL_QUERY.replace(":sort", request.getSort()).replace(":order", request.getOrder());
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(findAllQuery)) {
            preparedStatement.setInt(1, request.getPageSize());
            preparedStatement.setInt(2, (request.getCurrentPage() - 1) * request.getPageSize());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groups.add(convertResultSetToGroup(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(groups);
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

    @Override
    public DataTableResponse<Group> findByStudent(Long studentId) {
        List<Group> groups = new ArrayList<>();

        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(FIND_BY_STUDENT_ID_QUERY)) {
            preparedStatement.setLong(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groups.add(convertResultSetToGroup(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(groups);
        return tableResponse;
    }

    @Override
    public void deleteStudent(Long id, Long studentId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_STUDENT_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void addStudent(Long id, Long studentId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(ADD_STUDENT_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void addHeadman(Long headmanId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(ADD_HEADMAN_QUERY)) {
            preparedStatement.setLong(1, headmanId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    private Group convertResultSetToGroup(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        long headmanId = resultSet.getLong("headman");
        Group group = new Group();
        group.setId(id);
        group.setName(name);
        group.setHeadman(headmanId);
        return group;
    }
}
