package com.example.aiassistent.utils;

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

    public Gebruiker login (String email, String wachtwoord) {

        boolean ingelogd = false;

        for (Gebruiker gebruiker : DatabaseController.fetchAllGebruikers()) {
            if (gebruiker.getEmail().equals(email) && gebruiker.getWachtwoord().equals(wachtwoord)) {
                setActieveGebruiker (gebruiker);
                ingelogd = true;
                break;
            }
        }

        return ingelogd ? getActieveGebruiker () : null;
    }

    public void logout () {
        setActieveGebruiker (null);
    }
}
