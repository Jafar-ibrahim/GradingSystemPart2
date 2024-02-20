package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.*;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Model.Role;
import com.example.gradingsystempart2.Model.UserDTO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private final UserDAO userDAO;
    private final AdminDAO adminDAO;
    private final InstructorDAO instructorDAO;
    private final StudentDAO studentDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.adminDAO = new AdminDAO();
        this.instructorDAO = new InstructorDAO();
        this.studentDAO = new StudentDAO();
    }

    public String addAdmin(String username, String password, String firstName, String lastName){
        try {
            int userId = userDAO.insertUser(username, password, firstName, lastName,1);
            adminDAO.insertAdmin(userId);
            return "Admin added successfully.";
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Admin addition failed.";
        }

    }
    public String addStudent( String username, String password,
                                   String firstName, String lastName) {
        try {
            int userId = userDAO.insertUser(username, password, firstName, lastName,3);
            studentDAO.insertStudent(userId);
            return "Student added successfully.";
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Student addition failed.";
        }
    }

    public String addInstructor( String username, String password, String firstName, String lastName) {
        try {
            int userId = userDAO.insertUser(username, password, firstName, lastName,2);
            instructorDAO.insertInstructor(userId);
            return "Instructor added successfully.";
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Instructor addition failed.";
        }
    }
    public String deleteAdmin(int adminId) {
        try {
            adminDAO.deleteAdmin(adminId);
            userDAO.deleteUser(adminId);
            return "Admin deleted successfully.";
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Admin deleted failed.";
        }
    }
    public String deleteInstructor(int instructorId) {
        try {
            instructorDAO.deleteInstructor(instructorId);
            userDAO.deleteUser(instructorId);
            return "Instructor deleted successfully.";
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Instructor deleted failed.";
        }

    }
    public String deleteStudent(int studentId) {
        try {
            studentDAO.deleteStudent( studentId);
            userDAO.deleteUser(studentId);
            return "Student deleted successfully.";
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Student deleted failed.";
        }

    }
    public int authenticateUser(String username, String password) {
        try {
            return userDAO.authenticateUser(username,password);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public int getUserRole(int userId){
        try {
            return userDAO.getRole(userId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    public String getUserFullName(int userId){
        try {
            return userDAO.getFullName(userId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public int getSpecificId(int userId , int roleId){
        try{
            if (roleId == 1) return adminDAO.getAdminId(userId);
            else if(roleId == 2) return instructorDAO.getInstructorId(userId);
            else if(roleId == 3) return studentDAO.getStudentId(userId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    public UserDTO getById(int userId) {
        try {
            return userDAO.getById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
