package com.example.gradingsystempart2.Exceptions;

import java.sql.SQLException;

public class SectionNotFoundException extends SQLException {
    public SectionNotFoundException() {
        super("Section does not exist , try again with a valid id.");
    }
}
