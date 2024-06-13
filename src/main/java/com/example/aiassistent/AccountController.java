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
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.IOException;

public class AccountController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Gebruiker gebruiker;

    @FXML
    private MenuButton TaalKnop;

    @FXML
    private MenuItem nederlandsItem;

    @FXML
    private MenuItem englishItem;

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

        DatabaseController databaseController = DatabaseController.getInstance();
        boolean checkAvailableEmail = DatabaseController.checkAvailableEmail(newEmail);
        if (!checkAvailableEmail) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Waarschuwing");
            alert.setHeaderText(null);
            alert.setContentText("E-mail is al in gebruik!");

            alert.showAndWait();
            return;
        }

        String hashedPassword = DatabaseController.hashWachtwoord(newPassword);

        Gebruiker newGebruiker = new Gebruiker(gebruiker.getGebruikerID(), newUsername, newEmail, hashedPassword, gebruiker.getTaal());

        DatabaseController.getInstance().updateGebruiker(newGebruiker);
        Security security = Security.getInstance();
        security.login(newEmail, newPassword);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bevestiging");
        alert.setHeaderText(null);
        alert.setContentText("Gegevens zijn succesvol bevestigd!");

        System.out.println(security.getActieveGebruiker());
        alert.showAndWait();
    }

    @FXML
    private void selectLanguageNederlands(ActionEvent event) {
        System.out.println("Nederlands geselecteerd");
        TaalKnop.setText("Nederlands");

        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();
        gebruiker.setTaal("Nederlands");

        DatabaseController databaseController = DatabaseController.getInstance();
        databaseController.updateGebruiker(gebruiker);
    }

    @FXML
    private void selectLanguageEnglish(ActionEvent event) {
        System.out.println("English selected");
        TaalKnop.setText("English");

        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();
        gebruiker.setTaal("English");

        DatabaseController databaseController = DatabaseController.getInstance();
        databaseController.updateGebruiker(gebruiker);
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
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
