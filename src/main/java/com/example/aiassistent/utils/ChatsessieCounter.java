package com.example.aiassistent.utils;

public class ChatsessieCounter implements Observer {

    private ObserverOndersteuning observerOndersteuning;

    public ChatsessieCounter(ObserverOndersteuning observerOndersteuning) {
        this.observerOndersteuning = observerOndersteuning;
    }

    @Override
    public void update() {
        int chatsessieCount = observerOndersteuning.getChatsessieCount();

        if (chatsessieCount == 5) {
            displayMessage("Er zijn nu 5 chatsessies aangemaakt.");
        } else if (chatsessieCount == 10) {
            displayMessage("Er zijn nu 10 chatsessies aangemaakt.");
        } else if (chatsessieCount == 15) {
            displayMessage("Er zijn nu 15 chatsessies aangemaakt.");
        }
    }

    private void displayMessage(String message) {
        // Hier kun je JavaFX-dialog of melding implementeren in plaats van System.out.println()
        System.out.println(message);
    }
}
