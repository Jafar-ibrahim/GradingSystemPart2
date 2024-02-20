package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.*;
import com.example.gradingsystempart2.Model.Course;
import com.example.gradingsystempart2.Model.Section;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDAO {

    private static final Database database = Database.getInstance();


    public void insertCourse(String courseName) throws SQLException {
        String sql = "INSERT INTO course(course_name) VALUES (?)";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, courseName);
            preparedStatement.executeUpdate();
        }
    }
    public void deleteCourse(int courseId) throws SQLException {
        String sql = "DELETE FROM course WHERE course_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        }
    }

    public static void checkCourseExists(int courseId) throws SQLException {
        if(!database.recordExists("course",courseId))
            throw new CourseNotFoundException();
    }

    public List<Map<String, Object>> getInstructorCourses(int instructorId) throws SQLException {
        String sql = "SELECT c.course_name, s.section_id " +
                "FROM instructor_section isec " +
                "JOIN section s ON isec.section_id = s.section_id " +
                "JOIN course c ON s.course_id = c.course_id " +
                "WHERE isec.instructor_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, instructorId);

            List<Map<String, Object>> courses = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> courseMap = new HashMap<>();
                courseMap.put("course_name", resultSet.getString("course_name"));
                courseMap.put("section_id", resultSet.getInt("section_id"));
                courses.add(courseMap);
            }

            resultSet.close();
            return courses;
        }
    }
    public Course getById(int courseId) throws SQLException {
        try(ResultSet resultSet = database.readRecord("course",courseId)){
            if(resultSet.next()) {
                String name = resultSet.getString("course_name");
                return new Course(courseId, name);
            }
            throw new CourseNotFoundException();
        }
    }

}