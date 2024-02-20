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


    public void insertRole(int roleId , String roleName) throws SQLException {
        String sql = "INSERT INTO role(role_id, name) VALUES (?,?)";
        try (Connection connection = database.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roleId);
            preparedStatement.setString(2, roleName);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteRole(int roleId) throws SQLException {
        String sql = "DELETE FROM role WHERE role_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roleId);
            preparedStatement.executeUpdate();
        }
    }
    public Role getById(int roleId) throws SQLException {
        if(roleId -1 <= Role.values().length)
            return Role.values()[roleId-1];
        throw new IllegalArgumentException();
    }

}
