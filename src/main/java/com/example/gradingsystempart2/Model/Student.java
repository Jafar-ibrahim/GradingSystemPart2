package com.example.gradingsystempart2.Model;



public class Student extends User {
    private int studentId;
    public Student(int id,String username, String password, String firstName, String lastName) {
        super(id,username, password,firstName,lastName, Role.STUDENT);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
