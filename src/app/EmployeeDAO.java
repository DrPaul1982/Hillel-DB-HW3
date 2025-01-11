package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public void addEmployees(Connection connection) {
        String query = "INSERT INTO employees (name, age, position, salary) " +
                "VALUES ('Mike', 35, 'director', 8000), ('John', 25, 'manager', 3000), " +
                "('Helen', 30, 'developer', 2800), ('Bob', 23, 'driver', 1200)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Employees added: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println("Error while adding employees: " + e.getMessage());
        }
    }

    public void updateEmployee(Connection connection, String name, float updatedSalary) {
        String query = "UPDATE employees SET salary = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setFloat(1, updatedSalary);
            preparedStatement.setString(2, name);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Employee's salary updated: " + rowsUpdated);
        } catch (SQLException e) {
            System.err.println("Error while updating employee's salary: " + e.getMessage());
        }
    }

    public void deleteEmployee(Connection connection, String name) {
        String query = "DELETE FROM employees WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println("Employee deleted: " + rowsDeleted);
        } catch (SQLException e) {
            System.err.println("Error while deleting employee: " + e.getMessage());
        }
    }

    public List<String> getAllEmployees(Connection connection) {
        String query = "SELECT * FROM employees";
        List<String> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String employee = resultSet.getInt("id") + ") " +
                        resultSet.getString("name") + ", " +
                        resultSet.getInt("age") + " y.o., " +
                        resultSet.getString("position") + " position, " +
                        resultSet.getFloat("salary") + "$ salary.";
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}