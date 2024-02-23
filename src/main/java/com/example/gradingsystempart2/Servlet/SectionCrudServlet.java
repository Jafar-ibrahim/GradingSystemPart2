package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.DAO.GradeDAO;
import com.example.gradingsystempart2.DAO.SectionDAO;
import com.example.gradingsystempart2.DAO.StudentDAO;
import com.example.gradingsystempart2.DAO.UserDAO;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.CourseNotFoundException;
import com.example.gradingsystempart2.Exceptions.DuplicateRecordException;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Exceptions.UserNotFoundException;
import com.example.gradingsystempart2.Model.*;
import com.example.gradingsystempart2.Service.*;
import javafx.util.Pair;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;

@WebServlet("/admin/crud/section")
public class SectionCrudServlet extends HttpServlet {
    Database database = Database.getInstance();
    SectionService sectionService = new SectionService();

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
        String stringCourseId = request.getParameter("course_id");
        if(filled(stringCourseId)){
            int courseId = Integer.parseInt(stringCourseId);
            if(CourseService.courseExists(courseId)){
                sectionService.addSection(courseId);
                request.setAttribute("success", "Section added successfully");
            }else{
                request.setAttribute("error", "No Course exists with this ID");
            }
        }else{
            request.setAttribute("error", "Please enter all necessary info");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringSectionId = request.getParameter("section_id");
        String stringCourseId = request.getParameter("course_id");
        if(filled(stringSectionId)){
            int sectionId = Integer.parseInt(stringSectionId);
            if (filled(stringCourseId)){
                int courseId = Integer.parseInt(stringCourseId);
                if(CourseService.courseExists(courseId)) {
                    database.updateRecord("section", "course_id", courseId, sectionId);
                    request.setAttribute("success", "Section updated successfully");
                }else {
                    request.setAttribute("error", "Course does not exist");
                }
            }else{
                request.setAttribute("success", "No changes were made.");
            }
        }else {
            request.setAttribute("error", "Please enter the Section ID");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringSectionId = request.getParameter("section_id");
        if(filled(stringSectionId)){
            int sectionId = Integer.parseInt(stringSectionId);
            if(SectionService.sectionExists(sectionId)) {
                sectionService.deleteSection(sectionId);
                request.setAttribute("success", "Section deleted successfully");
            }else {
                request.setAttribute("error", "Section does not exist");
            }
        }else {
            request.setAttribute("error", "Please enter the Section ID");
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
