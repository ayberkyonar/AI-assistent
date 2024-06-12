package com.example.aiassistent.model;

abstract public class Antwoord {
    private int antwoordID;
    private String tekst;
    private String herkomst;
    private int vraagID;

    public Antwoord(int antwoordID, String tekst, int vraagID) {
        this.antwoordID = antwoordID;
        this.tekst = tekst;
        this.vraagID = vraagID;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getAntwoordID() {
        return antwoordID;
    }

    public void setAntwoordID(int antwoordID) {
        this.antwoordID = antwoordID;
    }

    public int getVraagID() {
        return vraagID;
    }

    public void setVraagID(int vraagID) {
        this.vraagID = vraagID;
    }

    public String getHerkomst() {
        return "Antwoord";
    }

    abstract public String maakAntwoord(String prompt);
    abstract public boolean checkAntwoord(String tekst);

}
