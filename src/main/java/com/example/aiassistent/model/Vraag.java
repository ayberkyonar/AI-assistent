package com.example.aiassistent.model;

import com.example.aiassistent.utils.DatabaseController;

public class Vraag {
    private int vraagID;
    private String prompt;
    private int chatsessieID;

    public Vraag(int vraagID, String prompt,int chatsessieID
    ) {
        this.vraagID = vraagID;
        this.prompt = prompt;
        this.chatsessieID= chatsessieID;
    }

    public void createAntwoord (){

        DatabaseController databaseController = DatabaseController.getInstance();
        Antwoord antwoord = databaseController.insertAntwoordData( null, null, this);

        if (antwoord != null) {
            antwoord = databaseController.updateAntwoord(antwoord);
        }

    }

    public int getVraagID() {
        return vraagID;
    }

    public String getPrompt() {
        return prompt;
    }

    public int getChatsessieID() {
        return chatsessieID;
    }

    public void setVraagID(int vraagID) {
        this.vraagID = vraagID;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setChatsessieID(int chatsessieID) {
        this.chatsessieID = chatsessieID;
    }
}