package com.example.gradingsystempart2.Exceptions;

import java.sql.SQLException;

public class RecordNotFoundException extends SQLException {
    public RecordNotFoundException() {
        super("No record with the given (id)s is found , please try again with different input");
    }
}
