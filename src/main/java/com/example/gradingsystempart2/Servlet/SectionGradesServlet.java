package com.example.gradingsystempart2.Servlet;

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

@WebServlet("/section_grades")
public class SectionGradesServlet extends HttpServlet {
    GradeService gradeService = new GradeService();
    SectionService sectionService = new SectionService();
    EnrollmentService enrollmentService = new EnrollmentService();
    UserService userService = new UserService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming the sectionId is passed as a parameter in the request
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int instructor_id = Integer.parseInt(request.getParameter("instructor_id"));
        UserDTO userDTO = userService.getById(userId);
        String instructorName = userDTO.getFirstName() +" "+userDTO.getLastName();
        String section_id = request.getParameter("section_id");
        if(!Objects.equals(section_id, "") && section_id != null){
            int sectionId = Integer.parseInt(section_id);
            Section section = sectionService.getById(sectionId);
            String courseName = section.getCourse().getName();
            List<Grade> sectionGrades = gradeService.getSectionGrades(sectionId);
            List<Integer> studentsIds = enrollmentService.getStudentIdsBySection(sectionId);
            request.setAttribute("section_id", sectionId);
            request.setAttribute("students_ids", studentsIds);
            request.setAttribute("section_grades", sectionGrades);
            request.setAttribute("course_name", courseName);

        }else{
            request.setAttribute("section_id", null);
            request.setAttribute("students_ids", null);
            request.setAttribute("section_grades", null);
            request.setAttribute("course_name", null);
        }
        // Call the service method to get section grades
        List<Section> sections = enrollmentService.getInstructorSections(instructor_id);

        // Set the sectionGrades as an attribute in the request
        request.setAttribute("sections", sections);
        request.setAttribute("user_id", userId);
        request.setAttribute("instructor_id", instructor_id);
        request.setAttribute("instructor_name", instructorName);

        // Forward the request to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/instructor_grades_view.jsp");
        dispatcher.forward(request, response);
    }
}
