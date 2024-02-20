package com.example.gradingsystempart2.Model;

public class Instructor extends User {
    private int instructorId;
    public Instructor(int id,String username, String password, String firstName, String lastName) {
        super(id,username, password,firstName,lastName, Role.INSTRUCTOR);
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
}
