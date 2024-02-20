package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Exceptions.*;
import com.example.gradingsystempart2.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorDAO {

    private static final Database database = Database.getInstance();

    public void insertInstructor( int userId) throws SQLException {
        String sql = "INSERT INTO instructor(user_id) VALUES (?)";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public String getInstructorFullName( int instructorId) {
        String sql = "SELECT CONCAT(u.first_name, ' ', u.last_name) AS full_name " +
                "FROM user u " +
                "JOIN instructor i ON u.user_id = i.user_id " +
                "WHERE i.instructor_id = ?";

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, instructorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("full_name");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public void deleteInstructor(int instructorId) throws SQLException {
        String sql = "DELETE FROM instructor WHERE instructor_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, instructorId);
            preparedStatement.executeUpdate();
        }
    }

    public static void checkInstructorExists(int instructorId) throws SQLException {
        if(!database.recordExists("instructor",instructorId))
            throw new UserNotFoundException();
    }
    public int getInstructorId(int userId) throws SQLException {
        String sql = "SELECT instructor_id FROM instructor WHERE user_id = ?";
        int instructorId = -1; // Default value if instructor not found

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    instructorId = resultSet.getInt("instructor_id");
                }
            }

        }
        return instructorId;
    }

}
