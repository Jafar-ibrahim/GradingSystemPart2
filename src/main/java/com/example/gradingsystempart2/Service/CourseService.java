package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.CourseDAO;
import com.example.gradingsystempart2.Database.Database;

import javax.sql.DataSource;
import java.sql.SQLException;

public class CourseService {

    private static final Database database = Database.getInstance();
    private final CourseDAO courseDAO;

    public CourseService() {
        courseDAO = new CourseDAO();
    }

    public String addCourse(String courseName){
        try{
            courseDAO.insertCourse(courseName);
            return "Course added successfully";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Course addition failed";
        }
    }
    public String deleteCourse(int courseId){
        try{
            courseDAO.deleteCourse(courseId);
            return "Course deleted successfully";

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Course deletion failed";   }
    }
    public String getCourseName(int courseId){
        try{
            CourseDAO.checkCourseExists(courseId);
            return courseDAO.getCourseName(courseId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
