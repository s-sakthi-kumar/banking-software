package com.example;

import java.sql.*;

public class App {

    public static void main(String[] args) {


        String query = "SELECT id, name, age FROM public.customer LIMIT 10";


        try(Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {


            while(rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");


                System.out.println(
                    id + " | " +
                    name + " | " +
                    age
                );
            }


        } catch(SQLException e) {

            e.printStackTrace();
        }
    }
}