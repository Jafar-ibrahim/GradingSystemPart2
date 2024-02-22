package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.RecordNotFoundException;
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
    private static final String TABLE_NAME = "instructor_section";



    public boolean insertInstructorSection(int instructorId, int sectionId) throws SQLException {
        return database.insertRecord("instructor_section",instructorId,sectionId);
    }

    public boolean deleteInstructorSection(int instructorId, int sectionId) throws SQLException {
        return database.deleteRecord(TABLE_NAME,instructorId,sectionId);
    }

    public static boolean InstructorSectionExists(int instructorId, int sectionId) {
        return database.recordExists("instructor_section",instructorId,sectionId);
    }
    public static void checkStudentSectionExists( int instructorId, int sectionId) throws RecordNotFoundException {
        if(!InstructorSectionExists(instructorId,sectionId))
            throw new RecordNotFoundException();
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
