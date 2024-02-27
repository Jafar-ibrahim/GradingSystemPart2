package com.example.gradingsystempart2;


import Util.PasswordAuthenticator;
import com.example.gradingsystempart2.DAO.GradeDAO;
import com.example.gradingsystempart2.DAO.UserDAO;
import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Database.SchemaManager;
import com.example.gradingsystempart2.Exceptions.CourseNotFoundException;
import com.example.gradingsystempart2.Model.UserDTO;
import com.example.gradingsystempart2.Service.CourseService;
import com.example.gradingsystempart2.Service.GradeService;
import com.example.gradingsystempart2.Service.SectionService;
import com.example.gradingsystempart2.Service.UserService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {

        SchemaManager schemaManager = new SchemaManager();
        schemaManager.dropAllTablesIfExist();
        schemaManager.initializeTables();
        schemaManager.addDummyData();



    }
}
