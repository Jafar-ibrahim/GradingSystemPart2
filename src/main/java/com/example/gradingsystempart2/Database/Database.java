package com.example.gradingsystempart2.Database;



import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class Database {
    private static final String SERVER_NAME = "localhost";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "qwerty";
    private static final String DB_DATABASE = "grading_system";
    private static final DataSource DATA_SOURCE = getDataSource();


    private static Database instance;
    private Database() {

    }

    public synchronized static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }
    public static DataSource getDataSource(){
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setServerName(SERVER_NAME);
            ds.setDatabaseName(DB_DATABASE);
            ds.setUser(DB_USERNAME);
            ds.setPassword(DB_PASSWORD);
            ds.setUseSSL(false);
            ds.setAllowPublicKeyRetrieval(true);

            return ds;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public Connection getDatabaseConnection() throws SQLException {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new SQLException("Couldn't establish connection", e);
        }
    }
    private int executeUpdate(String query, int... params) throws SQLException {
        try (Connection connection = getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeUpdate();
        }
    }
    private int executeUpdate(String query, Object... params) throws SQLException {
        try (Connection connection = getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeUpdate();
        }
    }

    public ResultSet executeQuery(String query, int... params) throws SQLException {
        Connection connection = getDatabaseConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    public List<String> getTableColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();

        try (Connection connection = getDatabaseConnection()) {
            String query = "SELECT COLUMN_NAME " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, connection.getCatalog()); // Get the current database/schema
                preparedStatement.setString(2, tableName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String columnName = resultSet.getString("COLUMN_NAME");
                        columnNames.add(columnName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return columnNames;
    }
    public List<String[]> getTableContent(String tableName) {
        String query = "SELECT * FROM " + tableName;
        List<String[]> tableContent = new ArrayList<>();
        List<String> columns = getTableColumnNames(tableName);
        try (ResultSet resultSet = executeQuery(query)) {
            while (resultSet.next()) {
                String[] row = new String[columns.size()];
                for (int i = 0; i < columns.size(); i++) {
                    row[i] = resultSet.getString(columns.get(i));
                }
                tableContent.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableContent;
    }

    public boolean insertRecord(String tableName, Object... inputData) {
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholder = new StringJoiner(", ");
        List<String> columnNames = getTableColumnNames(tableName);
        // primary keys are not an input , they are auto generated
        if(!(tableName.equals("student_section")
                || tableName.equals("instructor_section") || tableName.equals("grade"))){
            columnNames.remove(0);
        }
        for (String field : columnNames) {
            columns.add(field);
            placeholder.add("?");
        }
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholder + ")";
        try {
            executeUpdate(sql, inputData);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRecord(String tableName, String columnToUpdate,
                                Object newValue,int... pk) {
        System.out.println(columnToUpdate);
        List<String> columns = getTableColumnNames(tableName);
        String newValueString = String.valueOf(newValue);
        String query = "UPDATE " + tableName + " SET " + columnToUpdate + " = '" +newValueString+ "' WHERE " + columns.get(0) + " = ?";
        if (pk.length == 2)
            query += " AND "+ columns.get(1) + "= ?";
        try {
            int rowsUpdated = executeUpdate(query, pk);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRecord(String tableName, int... idToDelete) {
        List<String> columns = getTableColumnNames(tableName);
        String primaryKeyColumn = columns.get(0);
        String deleteSQL = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        if (idToDelete.length == 2)
            deleteSQL += " AND "+ columns.get(1) + "= ?";
        try {
            int rowsDeleted = executeUpdate(deleteSQL, idToDelete);
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ResultSet readRecord(String tableName, int... idToRead){
        List<String> columns = getTableColumnNames(tableName);
        String primaryKeyColumn = columns.get(0);
        String readSQL = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        if (idToRead.length == 2)
            readSQL += " AND "+ columns.get(1) + "= ?";
        try {
            return executeQuery(readSQL,idToRead);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean recordExists(String tableName,int... ids) {
        String query = pkQuery(tableName);

        try(ResultSet resultSet = executeQuery(query, ids)){
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String pkQuery(String tableName) {
        switch (tableName) {
            case "section":
                return "SELECT section_id FROM " + tableName + " WHERE section_id = ?";
            case "student_section":
            case "grade":
                return "SELECT student_id FROM " + tableName + " WHERE student_id = ? AND section_id = ?";
            case "instructor_section":
                return "SELECT instructor_id FROM " + tableName + " WHERE instructor_id = ? AND section_id = ?";
            case "student":
                return "SELECT student_id FROM " + tableName + " WHERE student_id = ?";
            case "instructor":
                return "SELECT instructor_id FROM " + tableName + " WHERE instructor_id = ?";
            case "course":
                return "SELECT course_id FROM " + tableName + " WHERE course_id = ?";

            default:
                throw new IllegalArgumentException();
        }
    }
}