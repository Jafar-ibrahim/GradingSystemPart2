package com.example.gradingsystempart2.Servlet;

import Util.PasswordAuthenticator;
import Util.SessionsPoolManager;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/auth")
public class AuthenticationServlet extends HttpServlet {
    UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(SessionsPoolManager.canCreateSession()){
            int userId = userService.authenticateUser(username,password);
            if(userId != -1){
                /* Session Management*/
                HttpSession session = request.getSession(true);
                session.setAttribute("logged_in", true);
                SessionsPoolManager.incrementSessionCounter();
                /* Session Management*/
                UserDTO userDTO = userService.getById(userId);
                String userFullName = userService.getUserFullName(userId);
                request.setAttribute("user_id",userId);
                request.setAttribute("user_fullName",userFullName);
                if(userDTO.getRole() == Role.ADMIN){
                    session.setAttribute("role", "admin");
                    int adminId = userService.getSpecificId(userId,1);
                    request.setAttribute("admin_id",adminId);
                    dispatcher = request.getRequestDispatcher("views/admin_view.jsp");
                }else if(userDTO.getRole() == Role.INSTRUCTOR){
                    session.setAttribute("role", "instructor");
                    int instructorId = userService.getSpecificId(userId,2);
                    request.setAttribute("instructor_id",instructorId);
                    dispatcher = request.getRequestDispatcher("views/instructor_view.jsp");
                }else{
                    session.setAttribute("role", "student");
                    int studentId = userService.getSpecificId(userId,3);
                    request.setAttribute("student_id",studentId);
                    dispatcher = request.getRequestDispatcher("views/student_view.jsp");
                }
            }else {
                request.setAttribute("error","Username or Password are invalid");
                dispatcher = request.getRequestDispatcher("views/index.jsp");
            }
        }else {
            request.setAttribute("error","Sorry , the server is busy currently.");
            dispatcher = request.getRequestDispatcher("views/index.jsp");
        }

        // Forward the request to the JSP
        dispatcher.forward(request, response);
    }
}
