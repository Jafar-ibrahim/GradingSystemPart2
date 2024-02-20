package com.example.gradingsystempart2.DAO;

import com.example.gradingsystempart2.Database.Database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminDAO {

    private static final Database database = Database.getInstance();
    public void insertAdmin(int userId) throws SQLException {
        String sql = "INSERT INTO admin(user_id) VALUES (?)";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteAdmin(int adminId) throws SQLException {
        String sql = "DELETE FROM admin WHERE admin_id = ?";
        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, adminId);
            preparedStatement.executeUpdate();
        }
    }

    public int getAdminId(int userId) throws SQLException {
        String sql = "SELECT admin_id FROM admin WHERE user_id = ?";
        int adminId = -1; // Default value if admin not found

        try (Connection connection = database.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    adminId = resultSet.getInt("admin_id");
                }
            }

        }

        return adminId;
    }

}
