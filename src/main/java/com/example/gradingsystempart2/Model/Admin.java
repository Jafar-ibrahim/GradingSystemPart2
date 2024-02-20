package com.example.gradingsystempart2.Model;

public class Admin extends User {
    private int adminId;
    public Admin(int id,String username, String password, String firstName, String lastName) {
        super(id,username, password,firstName,lastName, Role.INSTRUCTOR);
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }


}
