package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.UserDTO;
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

@WebServlet("/gradeReport")
public class GradeReportServlet extends HttpServlet {
    UserService userService = new UserService();
    GradeService gradeService = new GradeService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming the studentId is passed as a parameter in the request
        int userId = 5/*Integer.parseInt(request.getParameter("student_id"))*/;
        int studentId = userService.getSpecificId(userId,3);
        UserDTO userDTO = userService.getById(userId);
        String studentName = userDTO.getFirstName()+" "+userDTO.getLastName();
        // Call the service method to get the grade report
        List<Pair<Grade, Double>> gradeReport = gradeService.getGradeReport(studentId);
        double studentAverage = gradeService.getStudentAverage(studentId);
        // Set the gradeReport as an attribute in the request
        request.setAttribute("gradeReport", gradeReport);
        request.setAttribute("student_name", studentName);
        request.setAttribute("student_average", studentAverage);

        // Forward the request to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/GradeReport.jsp");
        dispatcher.forward(request, response);
    }
}
