package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.CourseDAO;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.CourseNotFoundException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class CourseService {

    private static final Database database = Database.getInstance();
    private final CourseDAO courseDAO;

    public CourseService() {
        courseDAO = new CourseDAO();
    }

    public boolean addCourse(String courseName){
         return courseDAO.insertCourse(courseName);
    }
    public boolean deleteCourse(int courseId) throws CourseNotFoundException{
        CourseDAO.checkCourseExists(courseId);
       return courseDAO.deleteCourse(courseId);
    }
    public boolean updateCourseName(int courseId,String name) throws CourseNotFoundException {
        CourseDAO.checkCourseExists(courseId);
        return courseDAO.updateCourseName(courseId,name);
    }
    public static boolean courseExists(int courseId){
        return CourseDAO.courseExists(courseId);
    }
    public static void checkCourseExists(int courseId) throws CourseNotFoundException {
        CourseDAO.checkCourseExists(courseId);
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
    public List<String> getColumnsNames(){
        return courseDAO.getColumnsNames();
    }
}
