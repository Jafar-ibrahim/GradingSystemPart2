package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.*;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Model.Section;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EnrollmentService {
    private static final Database database = Database.getInstance();
    private final InstructorSectionDAO instructorSectionDAO;
    private final StudentSectionDAO studentSectionDAO;
    private final InstructorDAO instructorDAO;
    private final CourseDAO courseDAO;

    public EnrollmentService() {
        instructorSectionDAO = new InstructorSectionDAO();
        instructorDAO = new InstructorDAO();
        studentSectionDAO = new StudentSectionDAO();
        courseDAO = new CourseDAO();
    }

    public String addStudentToSection(int studentId , int sectionId){
        try{
            StudentDAO.checkStudentExists(studentId);
            SectionDAO.checkSectionExists(sectionId);
            studentSectionDAO.insertStudentSection(studentId,sectionId);
            return "Student added to section successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Student addition to section failed";
        }
    }
    public String removeStudentFromSection(int studentId , int sectionId){
        try{
            StudentDAO.checkStudentExists(studentId);
            SectionDAO.checkSectionExists(sectionId);
            studentSectionDAO.deleteStudentSection(studentId,sectionId);
            return "Student removed from section successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Student removal from section failed";
        }
    }
    public String addInstructorToSection(int InstructorId , int sectionId){
        try{
            InstructorDAO.checkInstructorExists(InstructorId);
            SectionDAO.checkSectionExists(sectionId);
            instructorSectionDAO.insertInstructorSection(InstructorId,sectionId);
            return "Instructor added to section successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Instructor addition to section failed";
        }
    }
    public String removeInstructorFromSection(int InstructorId , int sectionId){
        try{
            InstructorDAO.checkInstructorExists(InstructorId);
            SectionDAO.checkSectionExists(sectionId);
            instructorSectionDAO.deleteInstructorSection(InstructorId,sectionId);
            return "Instructor removed from section successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Instructor removal from section failed";
        }
    }
    public boolean instructorHasAccessToSection(int instructorId, int sectionId){
        return instructorSectionDAO.InstructorSectionExists(instructorId,sectionId);
    }
    public boolean studentIsInSection(int studentId, int sectionId){
        try{
            return studentSectionDAO.StudentSectionExists(studentId,sectionId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /*public String getInstructorSections(int instructorId) {
        StringBuilder outputBuilder = new StringBuilder();

        try {
            List<Map<String, Object>> courses = courseDAO.getInstructorCourses(instructorId);

            String instructorName = instructorDAO.getInstructorFullName(instructorId);
            outputBuilder.append("Instructor (").append(instructorName).append(")'s sections:\n");

            outputBuilder.append(String.format("%-22s%-15s%n", "Section_id", "Course Name"));
            outputBuilder.append("----------------------------------------\n");

            for (Map<String, Object> course : courses) {
                String courseName = (String) course.get("course_name");
                int sectionNumber = (Integer) course.get("section_id");

                outputBuilder.append(String.format("%-20s%-25s%n", sectionNumber, courseName));
            }
        } catch (SQLException e) {
            outputBuilder.append(e.getMessage()).append("\n");
            e.printStackTrace();
        }

        return outputBuilder.toString();
    }*/
    public List<Section> getStudentSections(int studentId){
        try {
           return studentSectionDAO.getStudentSections(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Section> getInstructorSections(int instructorId){
        try {
            return instructorSectionDAO.getInstructorSections(instructorId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
