package app;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        app.EmployeeDAO employeeDAO = new app.EmployeeDAO();

        try (Connection connection = app.DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {

            app.DatabaseConnector.createTable(connection);
            System.out.println("Table created.");

            employeeDAO.addEmployees(connection);
            System.out.println("Employee(s) added.");

            System.out.println("Table's content:");
            List<String> employees = employeeDAO.getAllEmployees(connection);
            employees.forEach(System.out::println);

            employeeDAO.updateEmployee(connection, "Bob", 1900);

            System.out.println("Table's content after updating salary:");
            employees = employeeDAO.getAllEmployees(connection);
            employees.forEach(System.out::println);

            employeeDAO.deleteEmployee(connection, "Helen");

            employees = employeeDAO.getAllEmployees(connection);

            System.out.println("Table's content after deleting employee:");
            employees.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
