package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Section;
import com.example.gradingsystempart2.Service.EnrollmentService;
import com.example.gradingsystempart2.Service.GradeService;
import com.example.gradingsystempart2.Service.SectionService;
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
    GradeService gradeService = new GradeService();
    SectionService sectionService = new SectionService();
    EnrollmentService enrollmentService = new EnrollmentService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming the sectionId is passed as a parameter in the request
        int instructor_id = 2;
        int sectionId = Integer.parseInt(request.getParameter("section_id"));
        Section section = sectionService.getById(sectionId);
        String courseName = section.getCourse().getName();
        // Call the service method to get section grades
        List<Grade> sectionGrades = gradeService.getSectionGrades(sectionId);
        List<Section> sections = enrollmentService.getInstructorSections(instructor_id);

        // Set the sectionGrades as an attribute in the request
        request.setAttribute("section_id", sectionId);
        request.setAttribute("sections", sectionId);
        request.setAttribute("section_grades", sectionGrades);
        request.setAttribute("course_name", courseName);

        // Forward the request to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/instructor_grades_view.jsp");
        dispatcher.forward(request, response);
    }
}
