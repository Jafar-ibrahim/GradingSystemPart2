package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Exceptions.*;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Model.Course;
import com.example.gradingsystempart2.Model.Section;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionDAO {

    private static final Database database = Database.getInstance();
    private final CourseDAO courseDAO = new CourseDAO();


    public void insertSection(int courseId) throws SQLException {

        String sql = "INSERT INTO section(course_id, course_name) VALUES (?, ?)";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setString(2, getCourseName(courseId));
            preparedStatement.executeUpdate();
        }
    }

    public String getCourseName(int courseId) throws SQLException {
        String sql = "SELECT course_name FROM course WHERE course_id = ?";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next())
                    return resultSet.getString("course_name");
            }
        }
        return "N/A";
    }
    public void deleteSection(int sectionId) throws SQLException {
        String sql = "DELETE FROM section WHERE section_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, sectionId);
            preparedStatement.executeUpdate();
        }
    }

    public static void checkSectionExists(int sectionId) throws SQLException {
        if(!database.recordExists("section",sectionId))
            throw new SectionNotFoundException();
    }
    public Section getById(int sectionId) throws SQLException {
        try(ResultSet resultSet = database.readRecord("section",sectionId)){
            if(resultSet.next()) {
                int courseID = resultSet.getInt("course_id");
                Course course = courseDAO.getById(courseID);
                return new Section(sectionId, course);
            }
            throw new SectionNotFoundException();
        }
    }


}
