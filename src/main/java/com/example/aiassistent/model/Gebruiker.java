package com.example.aiassistent.model;

import com.example.aiassistent.utils.Observer;
import com.example.aiassistent.utils.GebruikerMethode;

public class Gebruiker implements Observer{
    private int gebruikerID;
    private String naam;
    private String email;
    private String wachtwoord;
    private String taal;

    public Gebruiker(int gebruikerID, String naam, String email, String wachtwoord, String taal) {
        this.gebruikerID = gebruikerID;
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
        this.taal = taal;
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

    public String getTaal() {
        return taal;
    }

    public void setTaal(String taal) {
        this.taal = taal;
    }

    @Override
    public void update(int gebruikerID, int chatsessieCount) {

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

    public void logGebruiker() {
        GebruikerMethode gebruikerMethode = new GebruikerMethode();
        gebruikerMethode.toonGebruiker(this);
    }
}
