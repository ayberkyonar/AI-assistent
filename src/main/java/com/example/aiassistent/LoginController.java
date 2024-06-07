package com.example.aiassistent;

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

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String wachtwoord = passwordField.getText();
        if (validateCredentials(email, wachtwoord)) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/aiassistent/chat.fxml")));
                Scene chatScene = new Scene(loader.load());
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(chatScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private boolean validateCredentials(String email, String wachtwoord) {
        // Simulate database check
        return "gebruiker@voorbeeld.com".equals(email) && "wachtwoord123".equals(wachtwoord);
    }

    private Scene createChatScene() {
        VBox chatBox = new VBox();
        chatBox.setAlignment(Pos.CENTER);
        chatBox.setSpacing(10);
        chatBox.setPadding(new Insets(20));

        Label chatLabel = new Label("Welkom in de chat!");
        chatLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        chatLabel.setTextFill(Color.WHITE);

        chatBox.getChildren().add(chatLabel);

        Scene chatScene = new Scene(chatBox, 1280, 720);
        chatBox.setStyle("-fx-background-color: #0a192f;");

        return chatScene;
    }
}