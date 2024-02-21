package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.DuplicateRecordException;
import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Section;
import javafx.util.Pair;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDAO {

    private static final Database database = Database.getInstance();
    private final SectionDAO sectionDAO = new SectionDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public int insertGrade(int studentId, int sectionId, double grade) throws SQLException {
        String sql = "INSERT INTO grade(student_id, section_id, grade) VALUES (?, ?, ?)";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, sectionId);
            preparedStatement.setDouble(3, grade);
            return preparedStatement.executeUpdate();
        }
    }
    public int updateGrade(int studentId, int sectionId, double newGrade) throws SQLException {
        String sql = "UPDATE grade SET grade = ? WHERE student_id = ? AND section_id = ?";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, newGrade);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setInt(3, sectionId);
            return preparedStatement.executeUpdate();
        }
    }
    public int deleteGrade(int studentId, int sectionId) throws SQLException {
        String sql = "DELETE FROM grade WHERE student_id = ? AND section_id = ?";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, sectionId);
            return preparedStatement.executeUpdate();
        }
    }
    public List<Pair<Grade,Double>> getStudentGrades(int studentId) throws SQLException {
        String sql = "WITH SectionAverage AS ( " +
                "    SELECT ss.section_id, AVG(g.grade) AS average_grade " +
                "    FROM student_section ss " +
                "    JOIN grade g ON ss.section_id = g.section_id " +
                "    WHERE ss.student_id = ? " +
                "    GROUP BY ss.section_id " +
                ") " +
                "SELECT c.course_name, s.section_id, g.grade, sa.average_grade " +
                "FROM grade g " +
                "JOIN section s ON g.section_id = s.section_id " +
                "JOIN course c ON s.course_id = c.course_id " +
                "JOIN SectionAverage sa ON s.section_id = sa.section_id " +
                "WHERE g.student_id = ?";

        try (ResultSet resultSet = database.executeQuery(sql,studentId,studentId)){
            List<Pair<Grade,Double>> grades = new ArrayList<>();
            while (resultSet.next()) {
                int sectionId = resultSet.getInt("section_id");
                Section section = sectionDAO.getById(sectionId);
                String studentName = studentDAO.getStudentFullName(studentId);
                double gradeValue = resultSet.getDouble("grade");
                Grade grade = new Grade(studentId,studentName,section,gradeValue);
                double section_average = resultSet.getDouble("average_grade");
                Pair<Grade, Double> gradePair = new Pair<>(grade,section_average);
                grades.add(gradePair);
            }

            resultSet.close();
            return grades;
        }
    }


    public double getStudentAverage( int studentId) throws SQLException {
        String sql = "SELECT AVG(grade) AS overall_average " +
                "FROM grade " +
                "WHERE student_id = ?";

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getDouble("overall_average");
            }
        }
        return 0.0;
    }

    public List<Grade> getGradesBySectionId(int sectionId) throws SQLException {
        String sql = "SELECT g.student_id, g.grade " +
                "FROM grade g " +
                "WHERE g.section_id = ?";

        List<Grade> gradesList = new ArrayList<>();

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, sectionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("student_id");
                    double grade = resultSet.getDouble("grade");
                    Section section = sectionDAO.getById(sectionId);
                    String studentName = studentDAO.getStudentFullName(studentId);
                    gradesList.add(new Grade(studentId,studentName,section,grade));
                }
            }
        }

        return gradesList;
    }
    private double calculateSectionAverage(int sectionId) throws SQLException {
        String sql = "SELECT AVG(grade) AS average_grade " +
                "FROM grade " +
                "WHERE section_id = ?";

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, sectionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("average_grade");
                }
            }
        }
        // Default value if there is an issue or no data is found
        return 0.0;
    }

    public static boolean gradeExists(int studentId , int sectionId){
        return database.recordExists("grade",studentId,sectionId);
    }
    public static void checkGradeExists(int studentId , int sectionId) throws DuplicateRecordException{
        if(gradeExists(studentId,sectionId))
            throw new DuplicateRecordException();
    }
}
