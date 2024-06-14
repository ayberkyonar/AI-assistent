package com.example.aiassistent.utils;

import com.example.aiassistent.model.*;

import java.sql.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import com.example.aiassistent.utils.Security;

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

            if (!databaseExists(url, databaseName, username, password)) {
                createDatabase(url, databaseName, username, password);
                createTables(url, databaseName, username, password);
            }

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
                    "`wachtwoord` varchar(65) NOT NULL, " +
                    "`taal` varchar(65) NOT NULL)");

            statement.executeUpdate("CREATE TABLE `chatsessie` (" +
                    "`chatsessieID` INT AUTO_INCREMENT PRIMARY KEY, " +
                    "`gebruikerID` INT NOT NULL, " +
                    "`onderwerp` varchar(255) NOT NULL, " +
                    "FOREIGN KEY (`gebruikerID`) REFERENCES `gebruiker`(`gebruikerID`))");

            statement.executeUpdate("CREATE TABLE `vraag` (" +
                    "`vraagID` INT AUTO_INCREMENT PRIMARY KEY, " +
                    "`chatsessieID` INT NOT NULL, " +
                    "`vraag` TEXT, " +
                    "FOREIGN KEY (`chatsessieID`) REFERENCES `chatsessie`(`chatsessieID`))");

            statement.executeUpdate("CREATE TABLE `antwoord` (" +
                    "`antwoordID` INT AUTO_INCREMENT PRIMARY KEY, " +
                    "`tekst` TEXT, " +
                    "`herkomst` varchar(255), " +
                    "`vraagID` INT NOT NULL, " +
                    "FOREIGN KEY (`vraagID`) REFERENCES `vraag`(`vraagID`))");

            insertGebruikerData(connection, "gebruiker", "gebruiker@gmail.com", "gebruiker", "Nederlands");
            insertGebruikerData(connection, "daniel", "daniel@gmail.com", "daniel", "Engels");
            insertGebruikerData(connection, "ayberk", "ayberk@gmail.com", "ayberk", "Nederlands");
            insertGebruikerData(connection, "denvey", "denvey@gmail.com", "denvey", "Nederlands");
            insertGebruikerData(connection, "danish", "danish@gmail.com", "danish", "Nederlands");
            insertGebruikerData(connection, "mykyta", "mykyta@gmail.com", "mykyta", "Nederlands");

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e);
        }
    }

    public static String hashWachtwoord(String wachtwoord) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(wachtwoord.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkAvailableEmail(String email) {
        Connection connection = DatabaseController.getInstance().getConnection();
        try {
            String query = "SELECT email FROM gebruiker WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking email availability: " + e);
            return false;
        }
    }

    private static void insertGebruikerData(Connection connection, String naam, String email, String wachtwoord, String taal) {

        System.out.println("Inserting data");
        System.out.println("Taal: " + taal);

        try {
            String hashedPassword = hashWachtwoord(wachtwoord);
            String query = "INSERT INTO gebruiker (naam, email, wachtwoord, taal) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, naam);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setString(4, taal);
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
                String taal = resultSet.getString("taal");
                Gebruiker gebruiker = new Gebruiker(gebruikerID, naam, email, wachtwoord, taal);
                gebruikers.add(gebruiker);
            }
        } catch (Exception e) {
            System.out.println("Error fetching gebruikers: " + e);
        }
        return gebruikers;
    }

    public static void insertChatsessieData(Gebruiker gebruiker, String onderwerp) {

        Connection connection = DatabaseController.getInstance().getConnection();

        int gebruikerID = gebruiker.getGebruikerID();

        try {
            String query = "INSERT INTO chatsessie (gebruikerID, onderwerp) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gebruikerID);
            preparedStatement.setString(2, onderwerp);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error inserting data: " + e);
        }
    }

    public static ArrayList<Chatsessie> getChatsessies(int gebruikerID) {

        Connection connection = DatabaseController.getInstance().getConnection();
        ArrayList<Chatsessie> chatsessies = new ArrayList<>();

        try {
            String query = "SELECT * FROM chatsessie WHERE gebruikerID = ? ORDER BY chatsessieID DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gebruikerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int chatsessieID = resultSet.getInt("chatsessieID");
                String onderwerp = resultSet.getString("onderwerp");
                Chatsessie chatsessie = new Chatsessie(chatsessieID, gebruikerID, onderwerp);
                chatsessies.add(chatsessie);
            }
        } catch (Exception e) {
            System.out.println("Error fetching chatsessies: " + e);
        }
        return chatsessies;
    }

    public static Chatsessie getChatsessie(int chatsessieID) {
        Connection connection = DatabaseController.getInstance().getConnection();
        Chatsessie chatsessie = null;

        try {
            String query = "SELECT * FROM chatsessie WHERE chatsessieID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, chatsessieID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int gebruikerID = resultSet.getInt("gebruikerID");
                String onderwerp = resultSet.getString("onderwerp");
                chatsessie = new Chatsessie(chatsessieID, gebruikerID, onderwerp);
            }
        } catch (Exception e) {
            System.out.println("Error fetching chatsessie: " + e);
        }
        return chatsessie;
    }

    public static void updateChatsessieData(Chatsessie chatsessie) {
        DatabaseController databaseController = DatabaseController.getInstance();
        Connection connection = databaseController.getConnection();

        int chatsessieID = chatsessie.getChatsessieID();
        String onderwerp = chatsessie.getOnderwerp();

        try {
            String query = "UPDATE chatsessie SET onderwerp = ? WHERE chatsessieID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, onderwerp);
            preparedStatement.setInt(2, chatsessieID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating data: " + e);
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

    public void updateGebruiker(Gebruiker gebruiker) {

        try {
            String query = "UPDATE gebruiker SET naam = ?, email = ?, wachtwoord = ?, taal = ? WHERE gebruikerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gebruiker.getNaam());
            preparedStatement.setString(2, gebruiker.getEmail());
            preparedStatement.setString(3, gebruiker.getWachtwoord());
            preparedStatement.setString(4, gebruiker.getTaal());
            preparedStatement.setInt(5, gebruiker.getGebruikerID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating gebruiker: " + e);
        }
    }

    public ArrayList<Vraag> getVragen(int chatsessieID) {
        ArrayList<Vraag> vragen = new ArrayList<>();
        Connection connection = getConnection();

        try {
            String query = "SELECT * FROM vraag WHERE chatsessieID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, chatsessieID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int vraagID = resultSet.getInt("vraagID");
                String vraagTekst = resultSet.getString("vraag");
                Vraag vraag = new Vraag(vraagID, vraagTekst,chatsessieID);
                vragen.add(vraag);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching vragen: " + e);
        }

        return vragen;
    }


    public Vraag insertVraagData(String vraag, int chatsessieID) {
        int vraagID = 0;
        try {
            String query = "INSERT INTO vraag (vraag, chatsessieID) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, vraag);
            preparedStatement.setInt(2, chatsessieID);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                vraagID = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error inserting vraag: " + e);
        }

        return new Vraag(vraagID, vraag, chatsessieID);
    }

    public Antwoord insertAntwoordData(String antwoord, String herkomst, Vraag vraag) {
        int vraagID = vraag.getVraagID();
        int antwoordID = 0;
        try {
            String query = "INSERT INTO antwoord (tekst, herkomst, vraagID) VALUES (?, ? , ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, antwoord);
            preparedStatement.setString(2, herkomst);
            preparedStatement.setInt(3, vraagID);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                antwoordID = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error inserting antwoord: " + e);
        }

        AISearch aiSearch = new AISearch(antwoordID, antwoord, vraagID);
        String aiAntwoord = aiSearch.maakAntwoord(vraag.getPrompt());
        if (aiAntwoord != null) {
            aiSearch.setTekst(aiAntwoord);
            return aiSearch;
        }

        DataSearch dataSearch = new DataSearch(antwoordID, antwoord, vraagID);
        String dataAntwoord = dataSearch.maakAntwoord(vraag.getPrompt());
        if (dataAntwoord != null) {
            dataSearch.setTekst(dataAntwoord);
            return dataSearch;
        }

        return null;
    }

    public Antwoord updateAntwoord (Antwoord antwoord) {

        System.out.println(antwoord.getAntwoordID());

        try {
            String query = "UPDATE antwoord SET tekst = ?, herkomst = ?, vraagID = ? WHERE antwoordID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antwoord.getTekst());
            preparedStatement.setString(2, antwoord.getHerkomst());
            preparedStatement.setInt(3, antwoord.getVraagID());
            preparedStatement.setInt(4, antwoord.getAntwoordID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating antwoord: " + e);
        }

        return antwoord;
    }

    public Antwoord getAntwoord(int vraagID) {
        Antwoord antwoord = null;
        try {
            String query = "SELECT * FROM antwoord WHERE vraagID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vraagID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int antwoordID = resultSet.getInt("antwoordID");
                String tekst = resultSet.getString("tekst");
                String herkomst = resultSet.getString("herkomst");

                if (herkomst.equals("42AI")) {
                    antwoord = new AISearch(antwoordID, tekst, vraagID);
                } else if (herkomst.equals("42Data")) {
                    antwoord = new DataSearch(antwoordID, tekst, vraagID);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching antwoord: " + e);
            e.printStackTrace(); // Add this line to print the stack trace
        }
        return antwoord;
    }

}