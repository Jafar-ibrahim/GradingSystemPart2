package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Database.Database;
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
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/admin/crud")
public class AdminCrudServlet extends HttpServlet {
    Database db = Database.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String table = request.getParameter("table");
        List<String> columns = db.getTableColumnsNames(table);
        List<String[]> content = db.getTableContent(table);
        request.setAttribute("columns",columns);
        request.setAttribute("content",content);
        request.setAttribute("table",table);
        request.setAttribute("tableCapitalized",table.substring(0, 1).toUpperCase() + table.substring(1));


        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin_crud_view.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String table = request.getParameter("table");
        RequestDispatcher dispatcher;
        HttpServletRequest newRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getMethod() {
                return "GET";
            }
        };

        switch (table) {
            case "course":
                dispatcher = newRequest.getRequestDispatcher("/admin/crud/course");
                break;
            case "user":
                dispatcher = newRequest.getRequestDispatcher("/admin/crud/user");
                break;
            case "student_section":
                dispatcher = newRequest.getRequestDispatcher("/admin/crud/student_section");
                break;
            default:
                /*"instructor_section"*/
                dispatcher = newRequest.getRequestDispatcher("/admin/crud/instructor_section");
                break;
        }
        dispatcher.forward(newRequest, response);
    }

}
