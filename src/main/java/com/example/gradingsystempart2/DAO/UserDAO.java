package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.*;
import com.example.gradingsystempart2.Model.*;

import javax.sql.DataSource;
import java.sql.*;

public class UserDAO {

    private static final Database database = Database.getInstance();
    private final RoleDAO roleDAO = new RoleDAO();

    public int insertUser(String username, String password,
                                  String firstName, String lastName, int roleId) throws SQLException {
        int userId = -1;
        String sql = "INSERT INTO user(username, password, first_name, last_name, role_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, roleId);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("User creation failed, no ID obtained.");
                }
            }
        }

        return userId;
    }
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM user WHERE user_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public static void checkUserExists(Connection connection, int userId) throws SQLException {
        if(!database.recordExists("user",userId))
            throw new UserNotFoundException();
    }
    public String getFullName(int userId) throws SQLException {
        String sql = "SELECT first_name, last_name FROM user WHERE user_id = ?";
        String fullName = null; // Default value if user not found

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    fullName = firstName + " " + lastName;
                }
            }

        }

        return fullName;
    }
    public int getRole(int userId) throws SQLException {
        String sql = "SELECT role_id FROM user WHERE user_id = ?";
        int roleId = -1; // Default value if user not found or role not set

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roleId = resultSet.getInt("role_id");
                }
            }

        }
        return roleId;
    }
    public int authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT user_id FROM user WHERE username = ? AND password = ?";

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // User authenticated, return user_id
                    return resultSet.getInt("user_id");
                } else {
                    // User not authenticated
                    return -1;
                }
            }

        }
    }

    public UserDTO getById(int userId) throws SQLException {
        try(ResultSet resultSet = database.readRecord("user",userId)){
            if(resultSet.next()) {
                System.out.println("inside");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Role role = roleDAO.getById(resultSet.getInt("role_id"));
                return new UserDTO(username,firstName,lastName,role);
            }
            throw new UserNotFoundException();
        }
    }
}
