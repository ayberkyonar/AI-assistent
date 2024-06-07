package com.example.aiassistent.utils;

import com.example.aiassistent.model.Gebruiker;

import java.sql.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseController {

    private static DatabaseController instance = null;
    private Connection connection;

    private DatabaseController() {
        Properties config = readConfig();

        String url = config.getProperty("url");
        String databaseName = config.getProperty("databaseName");
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        try {

            // Check if the database exists
            if (!databaseExists(url, databaseName, username, password)) {
                createDatabase(url, databaseName, username, password);
                createTables(url, databaseName, username, password);
            }

            // Connect to database
            connection = DriverManager.getConnection(url + databaseName, username, password);


        } catch (Exception e) {
            System.out.println("Error creating connection: " + e);
        }
    }

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
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

            statement.executeUpdate("INSERT INTO `gebruiker` VALUES " +
                    "(1, 'gebruiker', 'gebruiker@mail.com', 'test123')");

            connection.close();

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e);
        }
    }

    private static void insertGebruikerData(Connection connection, String naam, String email, String wachtwoord) {
        try {
            String query = "INSERT INTO gebruiker (naam, email, wachtwoord) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, naam);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, wachtwoord);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error inserting data: " + e);
        }
    }

    public static ArrayList<Gebruiker> fetchAllGebruikers() {

        Connection connection = DatabaseController.getInstance().getConnection();
        ArrayList<Gebruiker> gebruikers = new ArrayList<>();

        try {
            String query = "SELECT * FROM gebruiker";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int gebruikerID = resultSet.getInt("gebruikerID");
                String naam = resultSet.getString("naam");
                String email = resultSet.getString("email");
                String wachtwoord = resultSet.getString("wachtwoord");
                Gebruiker gebruiker = new Gebruiker(gebruikerID, naam, email, wachtwoord);
                gebruikers.add(gebruiker);
            }
        } catch (Exception e) {
            System.out.println("Error fetching gebruikers: " + e);
        }
        return gebruikers;
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
/*
    // Delete later:
    public static void main(String[] args) {

        DatabaseController databaseController = DatabaseController.getInstance();
        Connection connection = databaseController.getConnection();

    }*/
}