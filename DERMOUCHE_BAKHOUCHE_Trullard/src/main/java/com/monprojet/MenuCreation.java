package com.monprojet;
import java.util.ArrayList;
import java.util.Scanner;

import com.monprojet.Colon;
import com.monprojet.Colonie;
public class MenuCreation {
    private Colonie colonie;
    private Scanner scanner;
    private int nombrecolons;

    public MenuCreation(Colonie colonie, Scanner scanner, int n) {
        this.colonie = colonie;
        this.scanner = scanner;
        this.nombrecolons = n;
    }

    boolean enCours = true;

    /**
     * Lance le menu pour ajouter des relations entre colons et configurer leurs préférences.
     */
    public void lancerConfiguration() throws Exception {
        final String RED_TEXT = "\033[31m";
        final String RESET_TEXT = "\033[0m";

        while (enCours) {
            System.out.println(" ");
            System.out.println("Liste des colons :");
            colonie.afficherListeColons();
            System.out.println("Liste de ressources : " + colonie.getRessourceList());
            System.out.println(" ");

            System.out.println("\n------ MENU CRÉATION ------");
            System.out.println("1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d un colon");
            System.out.println("3) Fin du programme");
            System.out.print("Veuillez choisir une option (1-3) : ");

            if (!scanner.hasNextLine()) {
                System.out.println(RED_TEXT + "Erreur : Aucune entrée disponible. Fin du programme." + RESET_TEXT);
                break;
            }

            String input = scanner.nextLine().trim();
            input = input.replaceAll("\\s+", "");
            int choix;

            if (input.matches("\\d+")) {
                choix = Integer.parseInt(input);
            } else {
                System.out.println(RED_TEXT + "Option invalide : veuillez entrer un nombre entier pour l'option." + RESET_TEXT);
                continue;
            }

            switch (choix) {
                case 1:
                    ajouterRelation();
                    break;

                case 2:
                    ajouterPreferences();
                    break;

                case 3:
                    terminerConfiguration();
                    break;

                default:
                    System.out.println(RED_TEXT + "Option invalide. Veuillez choisir entre 1, 2 ou 3." + RESET_TEXT);
            }
        }
    }

    private void ajouterRelation() {
        final String RED_TEXT = "\033[31m";
        final String RESET_TEXT = "\033[0m";

        try {
            System.out.print("Nom du premier colon : ");
            if (!scanner.hasNextLine()) {
                System.out.println(RED_TEXT + "Erreur : Entrée insuffisante pour le premier colon." + RESET_TEXT);
                return;
            }
            String nom1 = scanner.nextLine().trim();
            Colon colon1 = colonie.getColon(nom1);

            if (colon1 == null) {
                System.out.println(RED_TEXT + "Erreur : Colon " + nom1 + " non trouvé." + RESET_TEXT);
                return;
            }

            System.out.print("Nom du deuxième colon : ");
            if (!scanner.hasNextLine()) {
                System.out.println(RED_TEXT + "Erreur : Entrée insuffisante pour le deuxième colon." + RESET_TEXT);
                return;
            }
            String nom2 = scanner.nextLine().trim();
            Colon colon2 = colonie.getColon(nom2);

            if (colon2 == null) {
                System.out.println(RED_TEXT + "Erreur : Colon " + nom2 + " non trouvé." + RESET_TEXT);
                return;
            }

            if (colon1.equals(colon2)) {
                System.out.println(RED_TEXT + "Erreur : Les deux colons doivent être différents." + RESET_TEXT);
                return;
            }

            Relation relation = new Relation(colon1, colon2);
            colonie.ajoutListeRelation(relation);
            colonie.afficheRelation();
        } catch (Exception e) {
            System.out.println(RED_TEXT + "Erreur inattendue : " + e.getMessage() + RESET_TEXT);
        }
    }

    private void ajouterPreferences() {
        final String RED_TEXT = "\033[31m";
        final String RESET_TEXT = "\033[0m";

        try {
            System.out.print("Veuillez saisir le nom du colon suivi de ses préférences exemple (A o1 o2..) : ");
            if (!scanner.hasNextLine()) {
                System.out.println(RED_TEXT + "Erreur : Entrée insuffisante pour les préférences." + RESET_TEXT);
                return;
            }

            String ligne = scanner.nextLine().trim();
            String[] liste = ligne.split(" ");

            if (liste.length != nombrecolons + 1) {
                System.out.println(RED_TEXT + "Erreur : Vous devez entrer le nom du colon suivi de " + nombrecolons + " ressource(s)." + RESET_TEXT);
                return;
            }

            String colName = liste[0];
            Colon colon = colonie.getColon(colName);

            if (colon == null) {
                System.out.println(RED_TEXT + "Erreur : Colon " + colName + " non trouvé." + RESET_TEXT);
                return;
            }

            if (colon.getListePreferences() != null && !colon.getListePreferences().isEmpty()) {
                System.out.println(RED_TEXT + "Erreur : Le colon " + colName + " a déjà une liste de préférences." + RESET_TEXT);
                return;
            }

            ArrayList<String> ressourcesValidees = new ArrayList<>();
            for (int i = 1; i < liste.length; i++) {
                String ressource = liste[i];
                if (!colonie.getRessourceList().contains(ressource)) {
                    System.out.println(RED_TEXT + "Erreur : La ressource " + ressource + " n'existe pas." + RESET_TEXT);
                    return;
                }
                if (ressourcesValidees.contains(ressource)) {
                    System.out.println(RED_TEXT + "Erreur : La ressource " + ressource + " est déjà présente dans la liste." + RESET_TEXT);
                    return;
                }
                ressourcesValidees.add(ressource);
            }

            colon.ajoutPreferences(ressourcesValidees);
            System.out.println("Préférences ajoutées avec succès pour " + colName);
        } catch (Exception e) {
            System.out.println(RED_TEXT + "Erreur inattendue lors de l'ajout des préférences : " + e.getMessage() + RESET_TEXT);
        }
    }

    private void terminerConfiguration() {
        if (colonie.verifierListePreference()) {
            colonie.affecterRessourcesNaivement();
            enCours = false;
        } else {
            System.out.println("Certaines préférences ne sont pas valides. Veuillez vérifier.");
        }
    }
}