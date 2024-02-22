package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.CourseDAO;
import com.example.gradingsystempart2.DAO.SectionDAO;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Model.Section;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SectionService {
    private final SectionDAO sectionDAO;

    public SectionService() {
        sectionDAO = new SectionDAO();
    }

    public String addSection(int courseId){
        try{
            CourseDAO.checkCourseExists(courseId);
            sectionDAO.insertSection(courseId);
            return "Section added successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Section addition failed";}
    }

    public String deleteSection(int sectionId){
        try{
            SectionDAO.checkSectionExists(sectionId);
            sectionDAO.deleteSection(sectionId);
            return "Section deleted successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Section deletion failed";
        }
    }
    public boolean updateCourseId(int sectionId, int newCourseId ){
        return sectionDAO.updateCourseId(newCourseId,sectionId);
    }

    public Section getById(int sectionId){
        try {
            return sectionDAO.getById(sectionId);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
