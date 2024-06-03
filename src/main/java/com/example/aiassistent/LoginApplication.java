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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inloggen op 42");

        // Main container
        VBox mainContainer = new VBox();
        mainContainer.setStyle("-fx-background-color: #0a192f; -fx-padding: 20;");
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(10);

        // Title
        Label title = new Label("Inloggen op 42");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);
        title.setPadding(new Insets(0, 0, 20, 0));

        // Form
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("E-mailadres");
        emailLabel.setTextFill(Color.WHITE);
        emailLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        TextField emailField = new TextField();
        emailField.setPromptText("Vul hier je e-mailadres in");
        emailField.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-prompt-text-fill: #a0aec0;");
        emailField.setPrefWidth(450);  // 1.5 times wider
        emailField.setPrefHeight(24 * 1.2);  // 1.2 times higher

        Label passwordLabel = new Label("Wachtwoord");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Vul hier je wachtwoord in");
        passwordField.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-prompt-text-fill: #a0aec0;");
        passwordField.setPrefWidth(450);  // 1.5 times wider
        passwordField.setPrefHeight(24 * 1.2);  // 1.2 times higher

        form.add(emailLabel, 0, 0);
        form.add(emailField, 0, 1);
        form.add(passwordLabel, 0, 2);
        form.add(passwordField, 0, 3);

        // Login button
        Button loginButton = new Button("Inloggen");
        loginButton.setStyle("-fx-background-color: #1e40af; -fx-text-fill: white; -fx-background-radius: 5;");
        loginButton.setPrefWidth(450);  // Same width as input fields
        loginButton.setPrefHeight(24 * 1.2);  // Same height as input fields
        loginButton.setOnMouseEntered(event -> loginButton.setStyle("-fx-background-color: #1e3a8a; -fx-text-fill: white; -fx-background-radius: 5;"));
        loginButton.setOnMouseExited(event -> loginButton.setStyle("-fx-background-color: #1e40af; -fx-text-fill: white; -fx-background-radius: 5;"));

        // Add components to main container
        mainContainer.getChildren().addAll(title, form, loginButton);

        // Scene
        Scene scene = new Scene(mainContainer, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}