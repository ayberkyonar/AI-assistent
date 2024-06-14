package com.example.aiassistent.model;

import com.example.aiassistent.utils.DatabaseController;
import com.example.aiassistent.utils.Security;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataSearch extends Antwoord {

    public DataSearch(int antwoordID, String tekst, int vraagID) {
        super(antwoordID, tekst, vraagID);
    }

    @Override
    public String getHerkomst() {
        return "42Data";
    }

    @Override
    public String maakAntwoord(String prompt) {
        ArrayList<String> antwoorden = zoekAntwoord(prompt);
        String antwoord = String.join(" \n", antwoorden);

        return antwoord;
    }

    @Override
    public boolean checkAntwoord(String tekst) {
        return false;
    }

    public ArrayList<String> zoekAntwoord(String gebruikerBericht) {
        Security security = Security.getInstance();
        Gebruiker gebruiker = security.getActieveGebruiker();

        String currentLanguage = gebruiker.getTaal();

        // Lees het JSON-bestand
        String JSON_FILE_NEDERLANDS = "src/main/java/com/example/aiassistent/utils/nederlands.json";
        String JSON_FILE_ENGLISH = "src/main/java/com/example/aiassistent/utils/engels.json";
        String jsonFile = (currentLanguage.equals("Nederlands")) ? JSON_FILE_NEDERLANDS : JSON_FILE_ENGLISH;

        String jsonContent = readFile(jsonFile);
        JSONObject jsonObject = new JSONObject(jsonContent);

        // Zoek naar een overeenkomstig antwoord
        ArrayList<String> antwoorden = zoekAntwoordInJSON(jsonObject, gebruikerBericht);

        return antwoorden;
    }

    private ArrayList<String> zoekAntwoordInJSON(JSONObject jsonObject, String gebruikerBericht) {
        ArrayList<String> antwoorden = new ArrayList<>();

        // Verwijder leestekens
        String cleanedBericht = gebruikerBericht.replaceAll("[.,!?\"':;()\\[\\]{}<>]", "");

        String[] words = cleanedBericht.split("\\s+");

        for (String word : words) {
            for (String key : jsonObject.keySet()) {
                if (key.equalsIgnoreCase(word)) {
                    String value = jsonObject.getString(key);
                    antwoorden.add(value);
                }
            }
        }

        if (antwoorden.isEmpty()) {
            Security security = Security.getInstance();
            Gebruiker gebruiker = security.getActieveGebruiker();
            String currentLanguage = gebruiker.getTaal();

            if (currentLanguage.equals("Nederlands")) {
                antwoorden.add("Sorry, ik begrijp je vraag niet. Kun je het op een andere manier formuleren?");
            } else if (currentLanguage.equals("English")) {
                antwoorden.add("Sorry, I don't understand your question. Could you phrase it differently?");
            }
        }

        return antwoorden;
    }

    private String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "{}";
        }
    }
}