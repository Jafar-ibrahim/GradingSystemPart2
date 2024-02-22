package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.DAO.GradeDAO;
import com.example.gradingsystempart2.DAO.SectionDAO;
import com.example.gradingsystempart2.DAO.StudentDAO;
import com.example.gradingsystempart2.Exceptions.DuplicateRecordException;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Exceptions.UserNotFoundException;
import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Instructor;
import com.example.gradingsystempart2.Model.Section;
import com.example.gradingsystempart2.Model.UserDTO;
import com.example.gradingsystempart2.Service.EnrollmentService;
import com.example.gradingsystempart2.Service.GradeService;
import com.example.gradingsystempart2.Service.SectionService;
import com.example.gradingsystempart2.Service.UserService;
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

@WebServlet("/grades_crud")
public class GradeCrudServlet extends HttpServlet {
    GradeService gradeService = new GradeService();
    EnrollmentService enrollmentService = new EnrollmentService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String section_id = request.getParameter("section_id");
        if(!Objects.equals(section_id, "") && section_id != null){
            int sectionId = Integer.parseInt(section_id);
            String action = request.getParameter("grade_action");
            int studentId = Integer.parseInt(section_id);
            request.setAttribute("student_id",studentId);
            request.setAttribute("section_id",sectionId);
            if(action.equals("add") || action.equals("modify")){
                double grade = Double.parseDouble(request.getParameter("grade"));
                request.setAttribute("grade",grade);
                if (action.equals("add")){
                    doPost(request,response);
                }else
                    doPut(request,response);
            } else {
                    doDelete(request,response);
            }

        }else {
            request.setAttribute("error", "Please choose a section and view it before modifying");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/section_grades");
            dispatcher.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int student_id = Integer.parseInt(request.getParameter("student_id"));
        int section_id = Integer.parseInt(request.getParameter("section_id"));
        double grade  = Double.parseDouble(request.getParameter("grade"));

        try {
            StudentDAO.checkStudentExists(student_id);
            GradeDAO.checkGradeExists(student_id,section_id);
            if(!enrollmentService.studentIsInSection(student_id,section_id)){
                request.setAttribute("error", "Student is not enrolled in this section");
            } else if (grade < 0 || grade > 100) {
                request.setAttribute("error", "Grade must be 0-100");
            } else {
                gradeService.addGrade(student_id,section_id,grade);
                request.setAttribute("success", "Grade added successfully");
            }
        } catch (UserNotFoundException e) {
            request.setAttribute("error", "Invalid student ID , try again");
        } catch (DuplicateRecordException e) {
            request.setAttribute("error", "Grade already exists");
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/section_grades");
        dispatcher.forward(request, response);

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int student_id = Integer.parseInt(request.getParameter("student_id"));
        int section_id = Integer.parseInt(request.getParameter("section_id"));
        double grade  = Double.parseDouble(request.getParameter("grade"));

        try {
            StudentDAO.checkStudentExists(student_id);
            if(!GradeDAO.gradeExists(student_id,section_id)){
                request.setAttribute("error", "A grade does not exist for this student , add one instead");
            }else if (grade < 0 || grade > 100) {
                request.setAttribute("error", "Grade must be 0-100");
            }else {
                gradeService.updateGrade(student_id, section_id, grade);
                request.setAttribute("success", "Grade modified successfully");
            }
        } catch (UserNotFoundException e) {
            request.setAttribute("error", "Invalid student ID , try again");
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/section_grades");
        dispatcher.forward(request, response);

    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int student_id = Integer.parseInt(request.getParameter("student_id"));
        int section_id = Integer.parseInt(request.getParameter("section_id"));

        try {
            StudentDAO.checkStudentExists(student_id);
            if(!GradeDAO.gradeExists(student_id,section_id)){
                request.setAttribute("error", "A grade does not exist for this student");
            }else {
                gradeService.deleteGrade(student_id, section_id);
                request.setAttribute("success", "Grade deleted successfully");
            }
        } catch (UserNotFoundException e) {
            request.setAttribute("error", "Invalid student ID , try again");
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/section_grades");
        dispatcher.forward(request, response);

    }
}
