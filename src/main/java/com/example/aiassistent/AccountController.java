package com.example.aiassistent;

import com.example.aiassistent.model.Gebruiker;
import com.example.aiassistent.utils.DatabaseController;
import com.example.aiassistent.utils.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private Button confirmButton;

    @FXML
    private Button chat;

    @FXML
    private Button uitloggen;

    @FXML
    public void initialize() {
        gebruiker = Security.getInstance().getActieveGebruiker();

        if (gebruiker != null) {
            emailField.setText(gebruiker.getEmail());
            usernameField.setText(gebruiker.getNaam());
        }

        chat.setOnAction(this::handleChat);
        uitloggen.setOnAction(this::handleLogout);
        confirmButton.setOnAction(this::handleConfirm);
    }

    private void handleConfirm(ActionEvent event) {
        String newEmail = emailField.getText();
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();

        if (newEmail.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Waarschuwing");
            alert.setHeaderText(null);
            alert.setContentText("Gebruikersnaam, e-mail en wachtwoord mogen niet leeg zijn!");

            alert.showAndWait();
            return;
        }

        String hashedPassword = DatabaseController.hashWachtwoord(newPassword);

        Gebruiker newGebruiker = new Gebruiker(gebruiker.getGebruikerID(), newUsername, newEmail, hashedPassword);

        DatabaseController.getInstance().updateGebruiker(newGebruiker);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bevestiging");
        alert.setHeaderText(null);
        alert.setContentText("Gegevens zijn succesvol bevestigd!");

        alert.showAndWait();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/login.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
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
