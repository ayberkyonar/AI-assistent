package com.example.aiassistent;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AccountApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Account");

        // Main container
        StackPane mainContainer = new StackPane();
        mainContainer.setStyle("-fx-background-color: #0a192f; -fx-padding: 20;");
        mainContainer.setAlignment(Pos.CENTER);

        VBox contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setSpacing(10);

        // Top right buttons
        HBox topRightButtons = new HBox();
        topRightButtons.setAlignment(Pos.TOP_RIGHT);
        topRightButtons.setSpacing(10);
        topRightButtons.setPadding(new Insets(10));

        Button chatButton = new Button("Chat");
        chatButton.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-font-weight: bold;");

        Button logoutButton = new Button("Uitloggen");
        logoutButton.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-font-weight: bold;");

        topRightButtons.getChildren().addAll(chatButton, logoutButton);

        // Title
        Label title = new Label("Account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        // Form
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("Nieuwe Email");
        emailLabel.setTextFill(Color.WHITE);
        emailLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: #d1d5db; -fx-prompt-text-fill: #a0aec0;");
        emailField.setPrefWidth(450);
        emailField.setPrefHeight(24 * 1.2);

        Label usernameLabel = new Label("Nieuwe Gebruikersnaam");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Gebruikersnaam");
        usernameField.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: #d1d5db; -fx-prompt-text-fill: #a0aec0;");
        usernameField.setPrefWidth(450);
        usernameField.setPrefHeight(24 * 1.2);

        Label passwordLabel = new Label("Nieuwe Wachtwoord");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Wachtwoord");
        passwordField.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: #d1d5db; -fx-prompt-text-fill: #a0aec0;");
        passwordField.setPrefWidth(450);
        passwordField.setPrefHeight(24 * 1.2);

        Label confirmPasswordLabel = new Label("Bevestig Oude Wachtwoord");
        confirmPasswordLabel.setTextFill(Color.WHITE);
        confirmPasswordLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Nieuw Wachtwoord");
        confirmPasswordField.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: #d1d5db; -fx-prompt-text-fill: #a0aec0;");
        confirmPasswordField.setPrefWidth(450);
        confirmPasswordField.setPrefHeight(24 * 1.2);

        form.add(emailLabel, 0, 0);
        form.add(emailField, 1, 0);
        form.add(usernameLabel, 0, 1);
        form.add(usernameField, 1, 1);
        form.add(passwordLabel, 0, 2);
        form.add(passwordField, 1, 2);
        form.add(confirmPasswordLabel, 0, 3);
        form.add(confirmPasswordField, 1, 3);

        // Confirm button
        Button confirmButton = new Button("Gegevens bevestigen");
        confirmButton.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-font-weight: bold;");
        confirmButton.setPrefWidth(450);
        confirmButton.setPrefHeight(24 * 1.2);

        contentContainer.getChildren().addAll(title, form, confirmButton);

        mainContainer.getChildren().addAll(contentContainer, topRightButtons);
        StackPane.setAlignment(topRightButtons, Pos.TOP_RIGHT);

        Scene scene = new Scene(mainContainer, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}