package com.example.gradingsystempart2.Service;

import com.example.gradingsystempart2.DAO.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class RoleService {

    private final RoleDAO roleDAO;

    public RoleService() {
        roleDAO = new RoleDAO();
    }
    public void addRole(int roleId,String roleName){
        try{
            roleDAO.insertRole(roleId,roleName);
        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }

    public void deleteRole(int roleId){
        try{
            roleDAO.deleteRole(roleId);
        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }
}
