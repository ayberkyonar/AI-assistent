package com.example.aiassistent.utils;

import com.example.aiassistent.model.Gebruiker;

public class GebruikerMethode {
    public void toonGebruiker(Gebruiker gebruiker) {

        String gebruikersnaam = gebruiker.getNaam();
        String email = gebruiker.getEmail();
        String taal = gebruiker.getTaal();

        System.out.println("Naam: " + gebruikersnaam);
        System.out.println("Email: " + email);
        System.out.println("Taal: " + taal);
        System.out.println();
    }
}
