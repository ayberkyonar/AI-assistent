package com.example.aiassistent;

import com.example.aiassistent.model.*;
import com.example.aiassistent.utils.DatabaseController;
import com.example.aiassistent.utils.Security;
import javafx.fxml.FXML;
import com.example.aiassistent.utils.ObserverOndersteuning;
import com.example.aiassistent.utils.ChatsessieCounter;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.ArrayList;

public class ChatController  {

    private Stage stage;
    private Parent root;
    private ObserverOndersteuning observerOndersteuning;
    private ChatsessieCounter chatsessieCounter;

    @FXML
    private VBox chatHistoryArea;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button chatAanmaken;

    @FXML
    private Button verzend;

    @FXML
    private Button account;

    @FXML
    private Button uitloggen;

    @FXML
    private ScrollPane chatHistoryScrollPane;

    private int chatsessieID;


    @FXML
    private void initialize() {
        verzend.setOnAction(event -> sendMessage());
        account.setOnAction(this::handleAccount);
        uitloggen.setOnAction(this::handleLogout);
        chatAanmaken.setOnAction(this::createChat);

        observerOndersteuning = new ObserverOndersteuning();
        chatsessieCounter = new ChatsessieCounter(observerOndersteuning);
        observerOndersteuning.registerObserver(chatsessieCounter);
        loadChatHistory();
        updateChatsessieCountForCurrentUser();

    }

    private void sendMessage() {

        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();

        if (gebruiker == null) {
            chatArea.appendText("User is not logged in.\n");
            return;
        }
        String message = messageField.getText();
        if (!message.isEmpty()) {
            DatabaseController databaseController = DatabaseController.getInstance();

            // Nodig om onderwerp te veranderen
            ArrayList<Vraag> vragen = databaseController.getVragen(this.chatsessieID);
            Chatsessie chatsessie = databaseController.getChatsessie(this.chatsessieID);

            Vraag vraag = databaseController.insertVraagData(message, chatsessieID);

            System.out.println("Message sent: " + message);

            // Onderwerp veranderen
            if (vragen.isEmpty()) {

                String prompt = vraag.getPrompt();
                if (prompt.length() > 20) {
                    prompt = prompt.substring(0, 20);
                }

                chatsessie.setOnderwerp(prompt);
                DatabaseController.updateChatsessieData(chatsessie);
            }

            vraag.createAntwoord();
        }

        loadChat();
        loadChatHistory();
    }
    private void loadChat(){
        chatArea.clear();
        Security security = Security.getInstance();
        DatabaseController databaseController = DatabaseController.getInstance();

        Gebruiker gebruiker = security.getActieveGebruiker();
        ArrayList<Vraag> vragen = databaseController.getVragen(this.chatsessieID);

        for (Vraag vraag : vragen) {
            chatArea.appendText(gebruiker.getNaam() + ": " + vraag.getPrompt() + "\n");

            Antwoord antwoord = databaseController.getAntwoord(vraag.getVraagID());
            if (antwoord != null) {
                chatArea.appendText(antwoord.getHerkomst() + ": " + antwoord.getTekst() + "\n");
            }
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Security security = Security.getInstance();
            security.logout();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/login.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/account.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChatHistory() {
        DatabaseController databaseController = DatabaseController.getInstance();
        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();
        int gebruikerID = gebruiker.getGebruikerID();

        chatHistoryArea.getChildren().clear();

        ArrayList<Chatsessie> chatsessies = databaseController.getChatsessies(gebruikerID);
        for (Chatsessie chatsessie : chatsessies) {
            Button chatButton = new Button(chatsessie.getOnderwerp());
            chatButton.setOnAction(event -> handleChatButton(chatsessie));

            chatButton.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-border-width: 5px; -fx-border-color: #0a192f; -fx-min-height: 50px;");
            chatHistoryArea.getChildren().add(chatButton);
        }

        chatHistoryScrollPane.setContent(chatHistoryArea);
    }

    private void handleChatButton(Chatsessie chatsessie) {
        this.chatsessieID = chatsessie.getChatsessieID();
        loadChat();
    }


    private void createChat(ActionEvent event) {
        try {
            DatabaseController databaseController = DatabaseController.getInstance();
            Security security = Security.getInstance();

            Gebruiker gebruiker = security.getActieveGebruiker();
            int gebruikerID = gebruiker.getGebruikerID();

            databaseController.insertChatsessieData(gebruiker, "Nieuwe Chat");

            ArrayList<Chatsessie> chatsessies = databaseController.getChatsessies(gebruikerID);
            this.chatsessieID = chatsessies.get(chatsessies.size() - 1).getChatsessieID();
            observerOndersteuning.incrementChatsessieCount(gebruiker);

            this.loadChatHistory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateChatsessieCountForCurrentUser() {
        DatabaseController databaseController = DatabaseController.getInstance();
        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();
        int gebruikerID = gebruiker.getGebruikerID();

        int chatsessieCount = databaseController.getChatsessies(gebruikerID).size();
        observerOndersteuning.setChatsessieCount(gebruikerID, chatsessieCount);
    }
}
