package com.monprojet;

import com.monprojet.Colon;
import com.monprojet.Colonie;

import java.util.Scanner;

public class MenuColonie1 {

    public static void lancerMenuColonie1() {
        Scanner scanner = new Scanner(System.in);
        Colonie colonie = new Colonie();
        final String RED_TEXT = "\033[31m";
        final String RESET_TEXT = "\033[0m";
        int n = 0;
        boolean SaisieValide = false;

        while (!SaisieValide) {
            System.out.println(" ");
            System.out.println("Veuillez fixer le nombre de colons dans la colonie (entre 1 et 26) : ");
            try {
                if (scanner.hasNextInt()) {
                    n = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    throw new IllegalArgumentException(
                            RED_TEXT + "Entrée invalide. Veuillez saisir un nombre entier." + RESET_TEXT
                    );
                }
                if (n < 1 || n > 26) {
                    throw new IllegalArgumentException(
                            RED_TEXT + "Le nombre de colons doit être entre 1 et 26." + RESET_TEXT
                    );
                }
                SaisieValide = true;
            } catch (IllegalArgumentException e) {
                System.out.println(RED_TEXT + "Erreur : " + e.getMessage() + RESET_TEXT);
            }
        }

        System.out.println("Nombre de colons fixé à : " + n);
        colonie = initialiserColonie(n);

        try {
            MenuCreation menucreation = new MenuCreation(colonie, scanner, n);
            menucreation.lancerConfiguration();
        } catch (Exception e) {
            System.out.println("Erreur (MenuCreation) : " + e.getMessage());
        }

        try {
            for (Colon c : colonie.getListeColons()) {
                System.out.println(c.toString());
            }
            System.out.println(" ");
            System.out.println("Liste des Relations Existantes : ");
            if (colonie.getListeRelation().isEmpty()) {
                System.out.println("Aucune relation n'a encore été définie.");
            } else {
                colonie.afficheRelation();
            }
            MenuGestion.lancerMenuGestion(colonie, scanner);
            System.out.println("Programme terminé.");
        } catch (Exception e) {
            System.out.println("Erreur (MenuGestion) : " + e.getMessage());
        }
    }

    public static Colonie initialiserColonie(int n) {
        Colonie colonie = new Colonie();
        char lettre = 'A';
        for (int i = 0; i < n; i++) {
            String nomColon = String.valueOf((char) (lettre + i));
            Colon c = new Colon(nomColon);
            colonie.ajoutColon(c);
            colonie.ajoutRessources("o" + (i + 1));
        }
        System.out.println("Liste de ressources : " + colonie.getRessourceList());
        return colonie;
    }
}