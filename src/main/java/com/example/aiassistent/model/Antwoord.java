package com.example.aiassistent.model;

abstract public class Antwoord {
    private int antwoordID;
    private int vraagID;
    private String tekst;

    public Antwoord(int antwoordID, int vraagID, String tekst) {
        this.antwoordID = antwoordID;
        this.vraagID = vraagID;
        this.tekst = tekst;
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

    abstract public String maakAntwoord();
    abstract public boolean checkAntwoord(String tekst);

}
