package com.example.gradingsystempart2.Exceptions;

import java.sql.SQLException;

public class CourseNotFoundException extends SQLException {
    public CourseNotFoundException() {
        super("Course does not exist , try again with a valid id.");
    }
}
