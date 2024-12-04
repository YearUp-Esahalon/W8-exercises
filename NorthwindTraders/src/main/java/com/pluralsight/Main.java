package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Check if user provided username and password
        if (args.length != 2) {
            System.out.println("Usage: java Main <username> <password>");
            System.exit(1);  // Exit if not correct arguments
        }

        // Get username and password from the command line arguments
        String username = args[0];
        String password = args[1];

        // Initialize Scanner to get input from the user
        Scanner scanner = new Scanner(System.in);

        try {
            // Start a menu loop
            while (true) {
                // Display options to the user
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                int choice = 0;
                try {
                    choice = scanner.nextInt();  // Get the user's choice
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();  // Clear the buffer
                    continue;  // Restart the loop
                }

                // Handle user choice
                if (choice == 1) {
                    // Query for displaying products
                    String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
                    displayData(query, username, password);  // Display products
                } else if (choice == 2) {
                    // Query for displaying customers
                    String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers";
                    displayData(query, username, password);  // Display customers
                } else if (choice == 3) {
                    // Query for displaying categories
                    String query = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";
                    displayData(query, username, password);  // Display categories
                } else if (choice == 0) {
                    System.out.println("Exiting...");
                    break;  // Exit the program
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            // Close the scanner when done
            scanner.close();
        }
    }

    public static void displayData(String query, String username, String password) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet results = null;

        try {
            // Establish a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);
            pStatement = connection.prepareStatement(query);
            results = pStatement.executeQuery();  // Execute the query

            // Loop through the results and print them
            while (results.next()) {
                if (query.contains("customers")) {
                    System.out.printf("Contact Name: %s, Company: %s, City: %s, Country: %s, Phone: %s%n",
                            results.getString("ContactName"),
                            results.getString("CompanyName"),
                            results.getString("City"),
                            results.getString("Country"),
                            results.getString("Phone"));
                } else if (query.contains("products")) {
                    System.out.printf("Product ID: %d, Name: %s, Price: %.2f, Units in Stock: %d%n",
                            results.getInt("ProductID"),
                            results.getString("ProductName"),
                            results.getDouble("UnitPrice"),
                            results.getInt("UnitsInStock"));
                } else if (query.contains("categories")) {
                    System.out.printf("Category ID: %d, Category Name: %s%n",
                            results.getInt("CategoryID"),
                            results.getString("CategoryName"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while accessing the database: " + e.getMessage());
        } finally {
            // Close all resources in the finally block
            try {
                if (results != null) results.close();
                if (pStatement != null) pStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
