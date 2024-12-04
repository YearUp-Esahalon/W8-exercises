package com.pluralsight;

import java.sql.*;


public class Main {
    public static void main(String[] args) {

        String username = "root";
        String password = "yearup";

        // load the MySQL Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot continue, cannot load SQL driver...");
            System.exit(1);
        }

        // 1. open a connection to the database
        // use the database URL to point to the correct database
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);

            // create statement
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();

            // define your query
            String query = "SELECT * FROM Products ";

            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);

            // process the results
            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }

            // 3. Close the connection

            results.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}