package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Section;
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

@WebServlet("/studentSections")
public class StudentSectionsServlet extends HttpServlet {
    UserService userService = new UserService();
    EnrollmentService enrollmentService = new EnrollmentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming the studentId is passed as a parameter in the request
        int studentId = 1/*Integer.parseInt(request.getParameter("student_id"))*/;
        int userId = userService.getSpecificId(studentId,3);
        UserDTO userDTO = userService.getById(userId);
        String studentName = userDTO.getFirstName()+" "+userDTO.getLastName();
        // Call the service method to get the grade report
        List<Section> sections = enrollmentService.getStudentSections(studentId);
        // Set the gradeReport as an attribute in the request
        request.setAttribute("student_sections", sections);
        request.setAttribute("student_name", studentName);


        // Forward the request to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/studentSections.jsp");
        dispatcher.forward(request, response);
    }
}
