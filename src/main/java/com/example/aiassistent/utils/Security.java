package com.example.aiassistent.utils;

public class Security {
    private static Security instance = null;
    private Gebruiker actieveGebruiker;

    private Security() {
        setActieveGebruiker (null);
    }

    public static Security getInstance () {

        if (instance == null) {
            instance = new Security();
        }

        return instance;
    }

    public Gebruiker getActieveGebruiker () {
        return actieveGebruiker;
    }

    private void setActieveGebruiker (Gebruiker gebruiker) {
        this.actieveGebruiker = gebruiker;
    }

    public boolean isIngelogd () {
        return getActieveGebruiker () != null;
    }

    public Gebruiker login () {

        Scanner scanner = new Scanner (System.in);
        DataSeeder seeder = DataSeeder.getInstance ();
        boolean ingelogd = false;

        System.out.print ("Voer een gebruikersnaam in: ");

        while (!ingelogd) {

            String gebruikersnaam = scanner.nextLine ();
            Gebruiker gebruiker = seeder.getGebruiker (gebruikersnaam);

            if (gebruiker != null) {
                setActieveGebruiker (gebruiker);
                return gebruiker;
            }

            System.out.print ("Voer opnieuw in (onbekende gebruikersnaam): ");
        }

        return null;
    }

    public void logout () {
        setActieveGebruiker (null);
    }
}
