package com.example.aiassistent.model;

public class DataSearch extends Antwoord {

    public DataSearch(int antwoordID, String tekst, int vraagID) {
        super(antwoordID, tekst, vraagID);
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
