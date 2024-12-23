package com.monprojet;
import java.util.Scanner;

import com.monprojet.Colon;
import com.monprojet.Colonie;
public class MenuGestion {

    /**
     * Lance le menu de gestion pour effectuer des opérations sur la colonie.
     */
    public static void lancerMenuGestion(Colonie colonie, Scanner scanner) throws Exception {
        final String RED_TEXT = "\033[31m";
        final String RESET_TEXT = "\033[0m";
        boolean enCours = true;

        while (enCours) {
            System.out.println(" ");
            System.out.println("Ressources attribuées à chaque colon :");
            colonie.affichageRessource();

            System.out.println("\n------ MENU GESTION ------");
            System.out.println("1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
            System.out.println("3) Fin du programme");
            System.out.print("Veuillez choisir une option (1-3) : ");

            int choix;
            String input = scanner.nextLine().trim();
            input = input.replaceAll("\\s+", "");
            if (input.matches("\\d+")) {
                choix = Integer.parseInt(input);
            } else {
                System.out.println(RED_TEXT
                        + "Option invalide : veuillez entrer un nombre entier pour l'option."
                        + RESET_TEXT);
                continue;
            }

            switch (choix) {
                case 1:
                    System.out.print("Nom du premier colon : ");
                    String input1 = scanner.nextLine().trim();

                    Colon colon1 = colonie.getColon(input1);
                    if (colon1 == null) {
                        System.out.println(RED_TEXT + "Erreur : Colon " + input1 + " non trouvé." + RESET_TEXT);
                        break;
                    }

                    System.out.print("Nom du deuxième colon : ");
                    String input2 = scanner.nextLine().trim();

                    Colon colon2 = colonie.getColon(input2);
                    if (colon2 == null) {
                        System.out.println(RED_TEXT + "Erreur : Colon " + input2 + " non trouvé." + RESET_TEXT);
                        break;
                    }

                    try {
                        colonie.echangeRessourceColon(colon1, colon2);
                        System.out.println("Échange réussi entre " + input1 + " et " + input2);
                        colonie.afficheRessourceDesColons();
                    } catch (IllegalArgumentException e) {
                        System.out.println(RED_TEXT + "Erreur : " + e.getMessage() + RESET_TEXT);
                    }
                    break;

                case 2:
                    colonie.affichelisteJaloux();
                    colonie.CalculCoutAffcetation();
                    break;

                case 3:
                    enCours = false;
                    break;

                default:
                    System.out.println(RED_TEXT
                            + "Option invalide. Veuillez choisir entre 1, 2 ou 3."
                            + RESET_TEXT);
            }
        }
    }
}
