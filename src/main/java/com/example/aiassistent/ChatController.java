package com.example.aiassistent;

import com.example.aiassistent.model.Chatsessie;
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
    private Gebruiker gebruiker;
    private String onderwerp;

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

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        if (gebruiker != null) {
            System.out.println("Gebruiker set: " + gebruiker.getNaam());
        }
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
        chatArea.appendText("Onderwerp: " + onderwerp + "\n");
    }

    private void sendMessage() {
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
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/chat.fxml"));
            root = loader.load();

            ChatController controller = loader.getController();
            controller.setGebruiker(gebruiker);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

            controller.setOnderwerp("Nieuw onderwerp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
