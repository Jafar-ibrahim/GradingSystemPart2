package com.example.gradingsystempart2.Model;

public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String firstName;
    protected String LastName;
    protected Role role;

    public User(int id,String username, String password, String firstName, String lastName, Role role) {
        this.username = username;
        this.firstName = firstName;
        LastName = lastName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}