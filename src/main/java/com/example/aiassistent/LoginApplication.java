package com.example.aiassistent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        primaryStage.setTitle("Inloggen op 42");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}