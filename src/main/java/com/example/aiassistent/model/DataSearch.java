package com.example.aiassistent.model;

public class DataSearch extends Antwoord {

    public DataSearch(int antwoordID, int vraagID, String tekst) {
        super(antwoordID, vraagID, tekst);
    }

    @Override
    public String maakAntwoord() {
        return null;
    }

    @Override
    public boolean checkAntwoord(String tekst) {
        return false;
    }
}
