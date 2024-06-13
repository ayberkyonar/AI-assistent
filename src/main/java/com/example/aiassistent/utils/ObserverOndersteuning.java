package com.example.aiassistent.utils;

import java.util.ArrayList;
import java.util.List;

public class ObserverOndersteuning {

    private List<Observer> observers = new ArrayList<>();
    private int chatsessieCount = 0;

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void incrementChatsessieCount() {
        chatsessieCount++;
        notifyObservers();
    }

    public int getChatsessieCount() {
        return chatsessieCount;
    }
}
