package com.example.aiassistent.model;

public class AISearch extends Antwoord {

    public AISearch(int antwoordID, String tekst, int vraagID) {
            super(antwoordID, tekst, vraagID);
        }

    @Override
    public String getHerkomst() {
        return "42AI";
    }

    @Override
    public String maakAntwoord(String prompt) {
        return null;
    }

}
