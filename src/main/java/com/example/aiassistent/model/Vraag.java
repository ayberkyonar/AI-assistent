package com.example.aiassistent.model;

public class Vraag {
    private int vraagID;
    private String prompt;

    public Vraag(int vraagID, String prompt) {
        this.vraagID = vraagID;
        this.prompt = prompt;
    }

    public int getVraagID() {
        return vraagID;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setVraagID(int vraagID) {
        this.vraagID = vraagID;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}