package com.example.aiassistent.utils;

import com.example.aiassistent.model.Gebruiker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObserverOndersteuning {

    private List<Observer> observers = new ArrayList<>();
    private Map<Integer, Integer> gebruikerChatsessieCounts = new HashMap<>(); // Map gebruikerID naar chatsessieCount

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int gebruikerID) {
        for (Observer observer : observers) {
            observer.update(gebruikerID, gebruikerChatsessieCounts.get(gebruikerID));
        }
    }

    public void incrementChatsessieCount(Gebruiker gebruiker) {
        int gebruikerID = gebruiker.getGebruikerID();
        gebruikerChatsessieCounts.put(gebruikerID, gebruikerChatsessieCounts.getOrDefault(gebruikerID, 0) + 1);
        notifyObservers(gebruikerID);
    }

    public int getChatsessieCount(int gebruikerID) {
        return gebruikerChatsessieCounts.getOrDefault(gebruikerID, 0);
    }

    public void setChatsessieCount(int gebruikerID, int chatsessieCount) {
        gebruikerChatsessieCounts.put(gebruikerID, chatsessieCount);
        notifyObservers(gebruikerID);
    }
}
