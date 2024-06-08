package com.example.aiassistent.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;
import com.example.aiassistent.model.Gebruiker;

public class Security {
    private static Security instance = null;
    private Gebruiker actieveGebruiker;

    private Security() {
        setActieveGebruiker (null);
    }

    public static Security getInstance () {

        if (instance == null) {
            instance = new Security();
        }

        return instance;
    }

    public Gebruiker getActieveGebruiker () {
        return actieveGebruiker;
    }

    private void setActieveGebruiker (Gebruiker gebruiker) {
        this.actieveGebruiker = gebruiker;
    }

    public boolean isIngelogd () {
        return getActieveGebruiker () != null;
    }

    private static String hashWachtwoord(String wachtwoord) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(wachtwoord.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Gebruiker login(String email, String wachtwoord) {
        String hashedPassword = hashWachtwoord(wachtwoord);
        for (Gebruiker gebruiker : DatabaseController.fetchAllGebruikers()) {
            if (gebruiker.getEmail().equals(email) && gebruiker.getWachtwoord().equals(hashedPassword)) {
                setActieveGebruiker(gebruiker);
                return getActieveGebruiker();
            }
        }
        return null;
    }

    public void logout () {
        setActieveGebruiker (null);
    }
}
