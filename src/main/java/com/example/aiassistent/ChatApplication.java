package com.example.aiassistent;

import com.example.aiassistent.model.Chatsessie;
import com.example.aiassistent.model.Gebruiker;
import com.example.aiassistent.utils.DatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApplication extends Application {

    private Gebruiker huidigeGebruiker;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseController dbController = DatabaseController.getInstance();
        huidigeGebruiker = dbController.getGebruikerById(1);

        System.out.println("Huidige gebruiker: " + huidigeGebruiker);

        openChatSession(primaryStage, "Welkom!");
    }

    public void openChatSession(Stage primaryStage, String onderwerp) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aiassistent/chat.fxml"));
        Parent root = loader.load();

        ChatController controller = loader.getController();
        controller.setGebruiker(huidigeGebruiker);
        controller.setOnderwerp(onderwerp);

        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
