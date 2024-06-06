package com.example.aiassistent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseController {

    public static void main(String[] args) {
        Properties config = readConfig();

        String url = config.getProperty("url");
        String databaseName = config.getProperty("databaseName");
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Check if the database exists
            if (!databaseExists(url, databaseName, username, password)) {
                createDatabase(url, databaseName, username, password);
                createTables(url, databaseName, username, password);
            }

            // Connect to database
            Connection connection = DriverManager.getConnection(url + databaseName, username, password);

            //Start of select statement
            Statement statement = connection.createStatement();

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static boolean databaseExists(String url, String databaseName, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES LIKE '" + databaseName + "'");
            return resultSet.next();
        } catch (Exception e) {
            return false;
        }
    }

    private static void createDatabase(String url, String databaseName, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + databaseName);
            connection.close();
        } catch (Exception e) {
            System.out.println("Error creating database: " + e);
        }
    }

    private static void createTables(String url, String databaseName, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(url + databaseName, username, password);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE `gebruiker` (" +
                    "`gebruikerID` INT AUTO_INCREMENT PRIMARY KEY, " +
                    "`naam` varchar(45) NOT NULL, " +
                    "`email` varchar(45) NOT NULL, " +
                    "`wachtwoord` varchar(45) NOT NULL)");

            connection.close();

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e);
        }
    }

    private static Properties readConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(".env")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}