package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Service.GradeService;
import javafx.util.Pair;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/section_grades")
public class SectionGradesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming the sectionId is passed as a parameter in the request
        int sectionId = Integer.parseInt(request.getParameter("section_id"));

        // Call the service method to get section grades
        GradeService gradeService = new GradeService();
        List<Pair<Integer, Double>> sectionGrades = gradeService.getSectionGrades(sectionId);

        // Set the sectionGrades as an attribute in the request
        request.setAttribute("sectionGrades", sectionGrades);

        // Forward the request to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/displaySectionGrades.jsp");
        dispatcher.forward(request, response);
    }
}
