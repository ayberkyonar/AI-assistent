package com.example.aiassistent.model;

import com.example.aiassistent.utils.DatabaseController;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataSearch extends Antwoord {
    private static final String JSON_FILE = "src/main/java/com/example/aiassistent/utils/package.json";


    public DataSearch(int antwoordID, String tekst, int vraagID) {
        super(antwoordID, tekst, vraagID);
    }

    @Override
    public String maakAntwoord(String prompt) {
        //DatabaseController.getInstance().insertAntwoordData(antwoord, "42Data",  vraagID);
        return "antwoord";
    }

    @Override
    public boolean checkAntwoord(String tekst) {
        return false;
    }

    public List<String> zoekAntwoord(String gebruikerBericht) {
        // Lees het JSON-bestand
        String jsonContent = readFile(JSON_FILE);

        // Parse het JSON-bestand
        JSONObject jsonObject = new JSONObject(jsonContent);

        // Zoek naar een overeenkomstig antwoord
        List<String> antwoorden = zoekAntwoordInJSON(jsonObject, gebruikerBericht);

        return antwoorden;
    }

    private List<String> zoekAntwoordInJSON(JSONObject jsonObject, String gebruikerBericht) {
        List<String> antwoorden = new ArrayList<>();

        String[] words = gebruikerBericht.split("\\s+");

        for (String word : words) {
            for (String key : jsonObject.keySet()) {
                if (key.equalsIgnoreCase(word)) {
                    String value = jsonObject.getString(key);
                    antwoorden.add(value);
                }
            }
        }

        return antwoorden;
    }

    private String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "{}"; // Return an empty JSON object if there's an error
        }
    }
}