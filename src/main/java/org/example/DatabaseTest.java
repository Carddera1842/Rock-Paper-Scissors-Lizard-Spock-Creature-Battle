package org.example;

import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            System.out.println(
                    "Successfully connected to the database!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database connection failed."
            );

            System.out.println(e.getMessage());
        }
    }
}