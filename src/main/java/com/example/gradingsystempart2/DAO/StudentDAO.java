package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Exceptions.UserNotFoundException;
import com.example.gradingsystempart2.Model.Course;
import com.example.gradingsystempart2.Model.Section;
import com.example.gradingsystempart2.Model.UserDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    private static final Database database = Database.getInstance();
    private final UserDAO userDAO = new UserDAO();

    public void insertStudent(int userId) throws SQLException {
        String sql = "INSERT INTO student(user_id) VALUES (?)";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        }
    }

    public static void checkStudentExists( int studentId) throws UserNotFoundException {
        if(!database.recordExists("student",studentId))
            throw new UserNotFoundException();
    }

    public int getStudentId(int userId) throws SQLException {
        String sql = "SELECT student_id FROM student WHERE user_id = ?";
        int studentId = -1; // Default value if student not found

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    studentId = resultSet.getInt("student_id");
                }
            }

        }
        return studentId;
    }

    public String getStudentFullName( int studentId) throws SQLException {
        String sql = "SELECT CONCAT(u.first_name, ' ', u.last_name) AS full_name " +
                "FROM user u " +
                "JOIN student i ON u.user_id = i.user_id " +
                "WHERE i.student_id = ?";

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("full_name");
                }
            }

        }
        return null;
    }
    public UserDTO getById(int studentId) throws SQLException {
        try(ResultSet resultSet = database.readRecord("student",studentId)){
            if(resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                return userDAO.getById(userId);
            }
            throw new SectionNotFoundException();
        }
    }


}
