package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Model.Section;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentSectionDAO {

    private static final Database database = Database.getInstance();
    private final SectionDAO sectionDAO = new SectionDAO();

    public void insertStudentSection(int studentId, int sectionId) throws SQLException {
        String sql = "INSERT INTO student_section(student_id, section_id) VALUES (?, ?)";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, sectionId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteStudentSection(int studentId, int sectionId) throws SQLException {
        String sql = "DELETE FROM student_section WHERE student_id = ? AND section_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, sectionId);
            preparedStatement.executeUpdate();
        }
    }

    public boolean StudentSectionExists( int studentId, int sectionId) throws SQLException {
        return database.recordExists("instructor",sectionId,sectionId);
    }
    public List<Section> getStudentSections(int studentId) throws SQLException {
        List<Section> sectionsList = new ArrayList<>();
        try(ResultSet resultSet = database.readRecord("student_section",studentId)) {
            while (resultSet.next()){
                int section_id = resultSet.getInt("section_id");
                Section section = sectionDAO.getById(section_id);
                sectionsList.add(section);
            }
        }
        return sectionsList;
    }

}