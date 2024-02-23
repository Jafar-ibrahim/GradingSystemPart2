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

@WebServlet("/instructor/sections")
public class InstructorSectionsServlet extends HttpServlet {
    UserService userService = new UserService();
    EnrollmentService enrollmentService = new EnrollmentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int instructorId = Integer.parseInt(request.getParameter("instructor_id"));
        UserDTO userDTO = userService.getById(userId);
        String instructorName = userDTO.getFirstName()+" "+userDTO.getLastName();
        List<Section> sections = enrollmentService.getInstructorSections(instructorId);
        request.setAttribute("instructor_sections", sections);
        request.setAttribute("instructor_name", instructorName);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/instructorSections.jsp");
        dispatcher.forward(request, response);
    }
}
