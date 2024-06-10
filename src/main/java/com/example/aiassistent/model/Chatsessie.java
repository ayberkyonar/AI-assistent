package com.example.aiassistent.model;

public class Chatsessie {
    private int chatsessieID;
    private int gebruikerID;
    private String onderwerp;

    public Chatsessie(int chatsessieID, int gebruikerID, String onderwerp) {
        this.chatsessieID = chatsessieID;
        this.gebruikerID = gebruikerID;
        this.onderwerp = onderwerp;
    }

    public int getChatsessieID() {
        return chatsessieID;
    }

    public void setChatsessieID(int chatsessieID) {
        this.chatsessieID = chatsessieID;
    }

    public int getGebruikerID() {
        return gebruikerID;
    }

    public void setGebruikerID(int gebruikerID) {
        this.gebruikerID = gebruikerID;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }
}
