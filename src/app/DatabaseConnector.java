package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    public static Connection getConnection() {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load properties file", ex);
        }

        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to obtain connection", e);
        }
    }

    public static void createTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "age INT NOT NULL, " +
                "position VARCHAR(100) NOT NULL, " +
                "salary FLOAT NOT NULL)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Employees added: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println("Error while adding employees: " + e.getMessage());
        }
    }
}
