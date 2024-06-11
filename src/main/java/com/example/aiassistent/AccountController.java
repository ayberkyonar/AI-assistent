package com.example.aiassistent;

import com.example.aiassistent.model.Gebruiker;
import com.example.aiassistent.utils.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Gebruiker gebruiker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button chat;

    @FXML
    private Button uitloggen;

    // Voeg hier event handlers toe voor de knoppen als dat nodig is

    @FXML
    private void initialize() {
        chat.setOnAction(this::handleChat);
        uitloggen.setOnAction(this::handleLogout);
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
    private void handleChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/chat.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
