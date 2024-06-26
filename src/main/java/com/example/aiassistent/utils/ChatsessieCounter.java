package com.example.aiassistent.utils;

public class ChatsessieCounter implements Observer {

    private ObserverOndersteuning observerOndersteuning;

    public ChatsessieCounter(ObserverOndersteuning observerOndersteuning) {
        this.observerOndersteuning = observerOndersteuning;
    }

    @Override
    public void update(int gebruikerID, int chatsessieCount) {
        String bericht = generateMessage(gebruikerID, chatsessieCount);
        System.out.println(bericht);
    }

    public String generateMessage(int gebruikerID, int chatsessieCount) {
        if (chatsessieCount == 5) {
            return "Er zijn nu 5 chatsessies aangemaakt.";
        } else if (chatsessieCount == 10) {
            return "Er zijn nu 10 chatsessies aangemaakt.";
        } else if (chatsessieCount == 15) {
            return "Er zijn nu 15 chatsessies aangemaakt.";
        }
        return "";
    }
}
