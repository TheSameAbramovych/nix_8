package ua.com.alevel.jpa.impl;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ua.com.alevel.jpa.DatasourceProperties;
import ua.com.alevel.jpa.JpaConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class MySqlJpaConfig implements JpaConfig {

    private final DatasourceProperties datasourceProperties;
    private Statement statement;
    private Connection connection;

    public MySqlJpaConfig(DatasourceProperties datasourceProperties) {
        this.datasourceProperties = datasourceProperties;
    }

    @SneakyThrows
    @Override
    public void connect() {
        try {
            Class.forName(datasourceProperties.getDriverClassName());
            connection = DriverManager.getConnection(
                    datasourceProperties.getUrl(),
                    datasourceProperties.getUsername(),
                    datasourceProperties.getPassword()
            );
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException, IOException {
        statement = connection.createStatement();
        statement.executeUpdate(getSql("schema/students.sql"));
        statement.executeUpdate(getSql("schema/students_group.sql"));
        statement.executeUpdate(getSql("schema/students_to_groups.sql"));
        statement.executeUpdate(getSql("data/students_data.sql"));
        statement.executeUpdate(getSql("data/groups_data.sql"));
        statement.executeUpdate(getSql("data/students_to_groups_data.sql"));
    }

    private String getSql(final String path) throws IOException {
        File resource = new ClassPathResource("sql/" + path).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }
}
