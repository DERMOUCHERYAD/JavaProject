package com.monprojet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {

    public static void main(String[] args) {
        runApplication(args);
    }

    // Méthode séparée pour faciliter les tests
    public static void runApplication(String[] args) {
        try {
            if (args.length == 1) {
                String nomFichier = args[0];
                Path chemin = Paths.get("./src/test/resources/test_files/", nomFichier);

                if (Files.exists(chemin)) {
                    MenuColonie2 menuColonie = new MenuColonie2(chemin.toString());
                    menuColonie.afficherMenu();
                } else {
                    System.err.println("Erreur : Le fichier spécifié n'existe pas : " + chemin);
                }
            } else {
                MenuColonie1.lancerMenuColonie1();
            }
        } catch (IOException e) {
            System.err.println("Erreur critique : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }
}
