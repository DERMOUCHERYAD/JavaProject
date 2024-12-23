package com.monprojet;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


import com.monprojet.Colonie;
public class MenuColonie2 {
    private Colonie colonie;
    private ConfigColonie config;
    final String RED_TEXT = "\033[31m";
    final String RESET_TEXT = "\033[0m";

    public MenuColonie2(String fichier) throws IOException {
        this.config = new ConfigColonie(fichier);
        try {
            this.colonie = this.config.LireFichierConfig();
            System.out.println("Liste des colons dans la colonie :");
            this.colonie.afficherListeColons();
        } catch (IOException e) {
            System.out.println(RED_TEXT + e.getMessage() + RESET_TEXT);
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(RED_TEXT + "Erreur de configuration : " + e.getMessage() + RESET_TEXT);
            System.exit(-1);
        } catch (Exception e) {
            System.out.println(RED_TEXT + e.getMessage() + RESET_TEXT);
            System.exit(-1);
        }
    }

    public void afficherMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n------ MENU ------");
            System.out.println("1) Résolution automatique");
            System.out.println("2) Sauvegarder la solution actuelle");
            System.out.println("3) Quitter");
            System.out.print("Veuillez choisir une option (1-3) : ");
            try {
                int choix = scanner.nextInt();
                scanner.nextLine();
                switch (choix) {
                    case 1:
                        resolutionAutomatique();
                        break;
                    case 2:
                        sauvegarderSolution(scanner);
                        break;
                    case 3:
                        System.out.println("Fin du programme.");
                        continuer = false;
                        break;
                    default:
                        System.out.println(RED_TEXT + "Option invalide. Veuillez choisir entre 1, 2 ou 3." + RESET_TEXT);
                }
            } catch (Exception e) {
                System.out.println(RED_TEXT + "Entrée invalide. Veuillez entrer un nombre entier entre 1 et 3" + RESET_TEXT);
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void resolutionAutomatique() {
        System.out.println("Lancement de la résolution automatique...");
        Solver solver = new Solver();
        List<String> solution = solver.solve(this.colonie);
        this.colonie.afficherAffectations();
        System.out.println("Coût final après résolution (Jalousie minimale) : " + this.colonie.getJalousie());
        System.out.println("Résolution terminée. Retour au menu.");
    }

    private void sauvegarderSolution(Scanner scanner) {
        System.out.print("Entrez le nom du fichier pour sauvegarder la solution : ");
        String cheminFichier = scanner.nextLine();
        try {
            this.config.sauvegarderSolution(cheminFichier);

        } catch (IOException e) {
            System.err.println(RED_TEXT + "Erreur lors de la sauvegarde : " + e.getMessage() + RESET_TEXT);
        }
    }
}