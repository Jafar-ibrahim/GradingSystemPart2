package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.DAO.GradeDAO;
import com.example.gradingsystempart2.DAO.SectionDAO;
import com.example.gradingsystempart2.DAO.StudentDAO;
import com.example.gradingsystempart2.Exceptions.CourseNotFoundException;
import com.example.gradingsystempart2.Exceptions.DuplicateRecordException;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Exceptions.UserNotFoundException;
import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Instructor;
import com.example.gradingsystempart2.Model.Section;
import com.example.gradingsystempart2.Model.UserDTO;
import com.example.gradingsystempart2.Service.*;
import javafx.util.Pair;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/admin/crud/course")
public class CourseCrudServlet extends HttpServlet {
    CourseService courseService = new CourseService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("add")){
            doPost(request,response);
        } else if (action.equals("delete")) {
            doDelete(request,response);
        }else if(action.equals("update")) {
            doPut(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseName = request.getParameter("course_name");
        if(filled(courseName)){
            courseService.addCourse(courseName);
            request.setAttribute("success", "Course added successfully");
        }else {
            request.setAttribute("error", "Please enter all necessary info");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringCourseId = request.getParameter("course_id");
        String courseName = request.getParameter("course_name");
        try {
            int courseId = Integer.parseInt(stringCourseId);
            if (filled(courseName) && filled(stringCourseId)) {
                try {
                    courseService.updateCourseName(courseId, courseName);
                    request.setAttribute("success", "Course modified successfully");
                } catch (CourseNotFoundException e) {
                    request.setAttribute("error", "No course is found with the given id");
                }
            } else {
                request.setAttribute("error", "Please enter all necessary info");
            }
        }catch (NumberFormatException e){
                request.setAttribute("error", "Please enter numeric values in the numeric fields");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringCourseId = request.getParameter("course_id");
        try{
            int courseId = Integer.parseInt(stringCourseId);
            if(filled(stringCourseId)){
                try {
                    courseService.deleteCourse(courseId);
                    request.setAttribute("success", "Course deleted successfully");
                } catch (CourseNotFoundException e) {
                    request.setAttribute("error", "No course is found with the given id");
                }
            }else {
                request.setAttribute("error", "Please enter the ID");
            }
        }catch (NumberFormatException e){
            request.setAttribute("error", "Please enter numeric values in the numeric fields");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }

    private boolean filled(Object o){
        if (o instanceof String)
            return !((String)o).isEmpty();
        return false;
    }
}
