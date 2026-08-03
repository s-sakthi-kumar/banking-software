package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/mydb";

    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";


    public static Connection getConnection() 
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}