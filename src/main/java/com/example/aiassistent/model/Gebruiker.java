package com.example.aiassistent.model;

import com.example.aiassistent.utils.Observer;

public class Gebruiker implements Observer{
    private int gebruikerID;
    private String naam;
    private String email;
    private String wachtwoord;

    public Gebruiker(int gebruikerID, String naam, String email, String wachtwoord) {
        this.gebruikerID = gebruikerID;
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public boolean checkCorrectCredentials (String naam, String wachtwoord) {
        return this.naam.equals(naam) && this.wachtwoord.equals(wachtwoord);
    }

    public int getGebruikerID() {
        return gebruikerID;
    }

    public void setGebruikerID(int gebruikerID) {
        this.gebruikerID = gebruikerID;
    }

    public String getNaam() {
        return naam;
    }

    public String getEmail() {
        return email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    @Override
    public void update() {

    }

    @Override
    public String toString() {
        return "Gebruiker{" +
                "gebruikerID=" + gebruikerID +
                ", naam='" + naam + '\'' +
                ", email='" + email + '\'' +
                ", wachtwoord='" + wachtwoord + '\'' +
                '}';
    }
}
