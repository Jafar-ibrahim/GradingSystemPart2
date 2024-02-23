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

@WebServlet("/admin/crud/instructor_section")
public class InstructorSectionCrudServlet extends HttpServlet {
    EnrollmentService enrollmentService = new EnrollmentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("add")){
            doPost(request,response);
        } else if (action.equals("delete")) {
            doDelete(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringInstructorId = request.getParameter("instructor_id");
        String stringSectionId = request.getParameter("section_id");
        if(filled(stringSectionId) && filled(stringInstructorId)){
            int instructorId = Integer.parseInt(stringInstructorId);
            int sectionId = Integer.parseInt(stringSectionId);
            if(!enrollmentService.instructorSectionExists(instructorId,sectionId)) {
                if(!SectionService.sectionExists(sectionId)){
                    request.setAttribute("error", "No Section exists with the given ID");
                }else if(!UserService.instructorExists(instructorId)){
                    request.setAttribute("error", "No Instructor exists with the given ID");
                }else {
                    enrollmentService.addInstructorToSection(instructorId,sectionId);
                    request.setAttribute("success", "Instructor Added to Section successfully");
                }
            }else {
                request.setAttribute("error", "A record already exists with the given IDs");
            }
        }else {
            request.setAttribute("error", "Please enter all necessary info");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringInstructorId = request.getParameter("instructor_id");
        String stringSectionId = request.getParameter("section_id");
        if(filled(stringSectionId) && filled(stringInstructorId)){
            int instructorId = Integer.parseInt(stringInstructorId);
            int sectionId = Integer.parseInt(stringSectionId);
            if(enrollmentService.instructorSectionExists(instructorId,sectionId)) {
                enrollmentService.removeInstructorFromSection(instructorId,sectionId);
                request.setAttribute("success", "Instructor removed from Section successfully");
            }else {
                request.setAttribute("error", "No record exists with the given IDs");
            }
        }else {
            request.setAttribute("error", "Please enter all necessary info");
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
