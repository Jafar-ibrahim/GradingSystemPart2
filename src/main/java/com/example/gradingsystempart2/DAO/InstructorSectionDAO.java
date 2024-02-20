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

public class InstructorSectionDAO {

    private static final Database database = Database.getInstance();
    private final SectionDAO sectionDAO = new SectionDAO();


    public void insertInstructorSection(int instructorId, int sectionId) throws SQLException {
        String sql = "INSERT INTO instructor_section(instructor_id, section_id) VALUES (?, ?)";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, instructorId);
            preparedStatement.setInt(2, sectionId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteInstructorSection(int instructorId, int sectionId) throws SQLException {
        String sql = "DELETE FROM instructor_section WHERE instructor_id = ? AND section_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, instructorId);
            preparedStatement.setInt(2, sectionId);
            preparedStatement.executeUpdate();
        }
    }

    public boolean InstructorSectionExists(int instructorId, int sectionId) {
        return database.recordExists("instructor_section",instructorId,sectionId);
    }
    public List<Section> getInstructorSections(int instructorId) throws SQLException {
        List<Section> sectionsList = new ArrayList<>();
        try(ResultSet resultSet = database.readRecord("instructor_section",instructorId)) {
            while (resultSet.next()){
                int section_id = resultSet.getInt("section_id");
                Section section = sectionDAO.getById(section_id);
                sectionsList.add(section);
            }
        }
        return sectionsList;
    }

}
