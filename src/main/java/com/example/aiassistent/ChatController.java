package com.example.aiassistent;

import com.example.aiassistent.model.Gebruiker;
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
    }

    private void sendMessage() {

        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();

        if (gebruiker == null) {
            // Handle case when gebruiker is not initialized
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
    private void createChat(ActionEvent event) {
        // moet nog veranderd worden
    }
}
