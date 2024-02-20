package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.*;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Model.Grade;
import javafx.util.Pair;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradeService {

    private static final Database database = Database.getInstance();
    private final GradeDAO gradeDAO;
    private final StudentDAO studentDAO;

    public GradeService() {
        gradeDAO = new GradeDAO();
        studentDAO = new StudentDAO();
    }

    public String addGrade(int studentId, int grade, int sectionId){
        try{
            StudentDAO.checkStudentExists(studentId);
            SectionDAO.checkSectionExists(sectionId);
            gradeDAO.insertGrade(studentId,grade,sectionId);
            return "Grade added successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Grade addition failed.";
        }
    }

    public String deleteGrade(int studentId, int sectionId){
        int affectedRows = 0;
        try{
            affectedRows = gradeDAO.deleteGrade(studentId,sectionId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        if (affectedRows > 0) {
            return "Grade deleted successfully.";
        } else {
            return "No matching record found for the specified student and section.";
        }
    }
    public String updateGrade(int studentId, int sectionId, int newGrade){
        int affectedRows = 0;
        try{
            StudentDAO.checkStudentExists(studentId);
            SectionDAO.checkSectionExists(sectionId);
            affectedRows = gradeDAO.updateGrade(studentId,sectionId,newGrade);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        if (affectedRows > 0) {
            return "Grade updated successfully.";
        } else {
            return "No matching record found for the specified student and section.";
        }
    }

    public List<Pair<Grade,Double>> getGradeReport(int studentId){
        List<Pair<Grade,Double>> grades = null;
        try {
            grades = gradeDAO.getStudentGrades(studentId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return grades;
    }

    public double getStudentAverage(int studentId) {
        double overallAvg = 0;
        try {
            overallAvg = gradeDAO.getStudentAverage(studentId);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return overallAvg ;
    }
    public String getCombinedInformation(int studentId){
        System.out.println("Combined Information for Student " + studentId + ":\n");

        return getGradeReport(studentId)+"\n"+ getStudentAverage(studentId);

    }



    public List<Pair<Integer,Double>> getSectionGrades(int sectionId) {
        List<Map<String, Object>> gradesList;
        try {
            return gradeDAO.getGradesBySectionId(sectionId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


}
