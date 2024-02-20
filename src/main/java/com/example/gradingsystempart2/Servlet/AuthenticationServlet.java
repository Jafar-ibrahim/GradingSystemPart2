package com.example.gradingsystempart2.Servlet;

import com.example.gradingsystempart2.Model.Grade;
import com.example.gradingsystempart2.Model.Role;
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

@WebServlet("/auth")
public class AuthenticationServlet extends HttpServlet {
    UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userId = userService.authenticateUser(username,password);
        RequestDispatcher dispatcher;
        if(userId != -1){
            UserDTO userDTO = userService.getById(userId);
            request.setAttribute("user_dto",userDTO);
            if(userDTO.getRole() == Role.ADMIN){
                int adminId = userService.getSpecificId(userId,1);
                request.setAttribute("admin_id",adminId);
                dispatcher = request.getRequestDispatcher("views/admin_view.jsp");
            }else if(userDTO.getRole() == Role.INSTRUCTOR){
                int instructorId = userService.getSpecificId(userId,2);
                request.setAttribute("instructor_id",instructorId);
                dispatcher = request.getRequestDispatcher("views/instructor_view.jsp");
            }else{
                int studentId = userService.getSpecificId(userId,3);
                request.setAttribute("student_id",studentId);
                dispatcher = request.getRequestDispatcher("views/student_view.jsp");
            }
            request.setAttribute("error","");
        }else {
            request.setAttribute("error","Username or Password are invalid");
            dispatcher = request.getRequestDispatcher("views/GradeReport.jsp");
        }

        // Forward the request to the JSP
        dispatcher.forward(request, response);
    }
}
