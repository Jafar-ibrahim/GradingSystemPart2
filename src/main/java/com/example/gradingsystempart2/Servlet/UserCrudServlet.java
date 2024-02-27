package com.example.gradingsystempart2.Servlet;

import Util.PasswordAuthenticator;
import com.example.gradingsystempart2.DAO.GradeDAO;
import com.example.gradingsystempart2.DAO.SectionDAO;
import com.example.gradingsystempart2.DAO.StudentDAO;
import com.example.gradingsystempart2.DAO.UserDAO;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.CourseNotFoundException;
import com.example.gradingsystempart2.Exceptions.DuplicateRecordException;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Exceptions.UserNotFoundException;
import com.example.gradingsystempart2.Model.*;
import com.example.gradingsystempart2.Service.*;
import javafx.util.Pair;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

@WebServlet("/admin/crud/user")
public class UserCrudServlet extends HttpServlet {
   UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("add")){
            doPost(request,response);
        } else if (action.equals("delete")) {
            doDelete(request,response);
        }else if(action.equals("update")) {
            doPut(request,response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String stringRoleId = request.getParameter("role_id");

        if(filled(username) && filled(password) && filled(firstName) && filled(lastName) && filled(stringRoleId)){
            int roleId = Integer.parseInt(stringRoleId);

            if(userService.getIdByUsername(username) == -1){
                if(roleId == 2){
                    userService.addInstructor(username,password,firstName,lastName);
                    request.setAttribute("success", "Instructor added successfully");
                }else if (roleId == 3){
                    userService.addStudent(username,password,firstName,lastName);
                    request.setAttribute("success", "Student added successfully");
                }else {
                    request.setAttribute("error", "Invalid role ID , enter 2 or 3");
                }
            }else{
                request.setAttribute("error", "a user exists with this username");
            }

        }else {
            request.setAttribute("error", "Please enter all necessary info");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database database = Database.getInstance();
        List<String> columns = userService.getUserColumnsNames();
        String stringUserId = request.getParameter("user_id");
        if(filled(stringUserId)){
            int userId = Integer.parseInt(stringUserId);
            if(UserService.userExists(userId)) {
                boolean updated = false;
                for (String column : columns){
                    if(!column.equals("user_id")) {
                        String newValue = request.getParameter(column);
                        if (filled(newValue)) {
                            if (column.equals("password")){
                                newValue = PasswordAuthenticator.hashPassword(newValue);
                            }
                            database.updateRecord("user", column, newValue, userId);
                            updated = true;
                        }
                    }
                }
                if (updated){
                    request.setAttribute("success", "User updated successfully");
                }else
                    request.setAttribute("success", "No changes were made.");
            }else {
                request.setAttribute("error", "User does not exist");
            }
        }else {
            request.setAttribute("error", "Please enter the user ID");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);

    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringUserId = request.getParameter("user_id");
        if(filled(stringUserId)){
            int userId = Integer.parseInt(stringUserId);
            if(UserService.userExists(userId)) {
                userService.deleteUser(userId);
                request.setAttribute("success", "User deleted successfully");
            }else {
                request.setAttribute("error", "User does not exist");
            }
        }else {
            request.setAttribute("error", "Please enter the user ID");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/crud");
        dispatcher.forward(request, response);
    }

    private boolean filled(Object o){
        if (o instanceof String)
            return !((String)o).isEmpty();
        return false;
    }
}
