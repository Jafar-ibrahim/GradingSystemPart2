package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Section;
import com.example.gradingsystempart2.Model.Student;
import com.example.gradingsystempart2.Model.UserDTO;
import com.example.gradingsystempart2.Service.EnrollmentService;
import com.example.gradingsystempart2.Service.GradeService;
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

@WebServlet("/student/sections")
public class StudentSectionsServlet extends HttpServlet {
    UserService userService = new UserService();
    EnrollmentService enrollmentService = new EnrollmentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        UserDTO userDTO = userService.getById(userId);
        String studentName = userDTO.getFirstName()+" "+userDTO.getLastName();
        List<Section> sections = enrollmentService.getStudentSections(studentId);
        request.setAttribute("student_sections", sections);
        request.setAttribute("student_name", studentName);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/studentSections.jsp");
        dispatcher.forward(request, response);
    }
}
