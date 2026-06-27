package com.gstsystem.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static synchronized Connection getConnection() {
        if (connection == null) {
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
                Properties prop = new Properties();
                if (input == null) {
                    throw new RuntimeException("Unable to find application.properties");
                }
                prop.load(input);

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                    prop.getProperty("db.url"),
                    prop.getProperty("db.username"),
                    prop.getProperty("db.password")
                );
                System.out.println("[INFO] MySQL connection established cleanly.");
            } catch (Exception e) {
                System.err.println("[ERROR] Database initialization failed: " + e.getMessage());
            }
        }
        return connection;
    }
}