package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;
import com.example.gradingsystempart2.Exceptions.SectionNotFoundException;
import com.example.gradingsystempart2.Model.Role;
import com.example.gradingsystempart2.Model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class RoleDAO {

    private static final Database database = Database.getInstance();
    private static final String TABLE_NAME = "role";

    public boolean insertRole(int roleId , String roleName) throws SQLException {
        return database.insertRecord("role",roleId,roleName);
    }

    public boolean deleteRole(int roleId) throws SQLException {
        return database.deleteRecord(TABLE_NAME,roleId);
    }
    public boolean updateName(int roleId, String newName){
        return database.updateRecord(TABLE_NAME,"name",newName,roleId);
    }
    public Role getById(int roleId) throws SQLException {
        if(roleId -1 <= Role.values().length)
            return Role.values()[roleId-1];
        throw new IllegalArgumentException();
    }

}
