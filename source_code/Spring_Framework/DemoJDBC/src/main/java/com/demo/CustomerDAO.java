package com.demo;

import java.sql.*;

public class CustomerDAO {

    private String driver;
    private String url;
    private String userName;
    private String password;

    // Setter methods for dependency injection
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // fetch customer records
    public void selectAllRows() throws ClassNotFoundException, SQLException {
        System.out.println("Retrieving customer data..");

        // driver is loading
        Class.forName(driver);

        // connection establishment is done here
        Connection con = DriverManager.getConnection(url, userName, password);

        // Executing our query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM CustomerDb.CustomerInfo");

        while (rs.next()) {
            int customerId = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            String email = rs.getString(4);
            String phone = rs.getString(5);
            String city = rs.getString(6);

            System.out.println(customerId + " " 
                    + firstName + " " 
                    + lastName + " " 
                    + email + " " 
                    + phone + " " 
                    + city);
        }

        // Close connection
        con.close();
    }
}
