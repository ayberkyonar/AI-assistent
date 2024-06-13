package com.example.aiassistent;

import com.example.aiassistent.model.Gebruiker;
import com.example.aiassistent.utils.Security;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;



import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        Security security = Security.getInstance();
        Gebruiker currentUser = security.login(email, password);

        if (currentUser != null) {
            currentUser.logGebruiker();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/chat.fxml"));
            Parent root = loader.load();
            ChatController controller = loader.getController();

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Inlogfout");
            alert.setHeaderText(null);
            alert.setContentText("Onjuist e-mailadres of wachtwoord.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleMouseEnter() {
        Button loginButton = (Button) emailField.getScene().lookup(".button");
        loginButton.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-background-radius: 5;");
    }

    @FXML
    private void handleMouseExit() {
        Button loginButton = (Button) emailField.getScene().lookup(".button");
        loginButton.setStyle("-fx-background-color: #1e40af; -fx-text-fill: white; -fx-background-radius: 5;");
    }
}