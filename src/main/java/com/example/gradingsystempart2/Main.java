package com.example.gradingsystempart2;

import com.example.gradingsystempart2.DAO.GradeDAO;
import com.example.gradingsystempart2.DAO.UserDAO;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.CourseNotFoundException;
import com.example.gradingsystempart2.Service.CourseService;
import com.example.gradingsystempart2.Service.GradeService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        Database database = Database.getInstance();
        GradeService gradeService = new GradeService();
        CourseService courseService = new CourseService();
        //System.out.println(database.deleteRecord("grade",1,1));
        //System.out.println(database.insertRecord("grade",1,1,95.0));
        //System.out.println(database.insertRecord("course","test"));
        //System.out.println(database.recordExists("grade",1,3));
        System.out.println(gradeService.updateGrade(1,1,45));
        //System.out.println(gradeService.deleteGrade(1,1));
        //System.out.println(gradeService.addGrade(1,1,49));
        //System.out.println(courseService.updateCourseName(7,"worked"));
        UserDAO userDAO = new UserDAO();
        System.out.println(userDAO.getIdByUsername("Jafar"));
    }
}
