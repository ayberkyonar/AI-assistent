package com.example.aiassistent;

import com.example.aiassistent.model.Chatsessie;
import com.example.aiassistent.model.Gebruiker;
import com.example.aiassistent.utils.DatabaseController;
import com.example.aiassistent.utils.Security;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ChatController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private StackPane rootPane;

    @FXML
    private TextArea chatHistoryArea;

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
    private void initialize() {
        verzend.setOnAction(event -> sendMessage());
        account.setOnAction(this::handleAccount);
        uitloggen.setOnAction(this::handleLogout);
        chatAanmaken.setOnAction(this::createChat);
        loadChatHistory();
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
            chatArea.appendText(gebruiker.getNaam() + ": " + message + "\n");
            messageField.clear();
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

        // Clear the previous text
        chatHistoryArea.clear();

        ArrayList<Chatsessie> chatsessies = databaseController.getChatsessies(gebruikerID);
        for (Chatsessie chatsessie : chatsessies) {
            chatHistoryArea.appendText(chatsessie.getOnderwerp() + "\n");
        }
    }

    private void createChat(ActionEvent event) {
        try {

            DatabaseController databaseController = DatabaseController.getInstance();
            Security security = Security.getInstance();

            Gebruiker gebruiker = security.getActieveGebruiker();
            int gebruikerID = gebruiker.getGebruikerID();

            databaseController.insertChatsessieData(gebruiker, "test onderwerp");

            this.loadChatHistory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
