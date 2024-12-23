package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MenuColonie2Test {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Redirige la sortie standard pour capturer l'output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testAfficherMenu_ResolutionAutomatique() throws Exception {
        // Prépare un fichier de configuration valide dans src/test/resources/test_files
        String fichierConfig = "src/test/resources/test_files/colonie1";
        File configFile = new File(fichierConfig);
        assertTrue(configFile.exists(), "Le fichier de configuration n'existe pas : " + fichierConfig);

        // Simule l'entrée utilisateur pour "Résolution automatique" puis quitter
        String input = "1\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Instancie le menu avec le fichier de configuration
        MenuColonie2 menu = new MenuColonie2(fichierConfig);

        // Exécute le menu
        menu.afficherMenu();

        // Vérifie les sorties
        assertTrue(outputStream.toString().contains("Lancement de la résolution automatique"));
        assertTrue(outputStream.toString().contains("Coût final après résolution (Jalousie minimale)"));
    }



    @Test
    void testAfficherMenu_Quitter() throws Exception {
        // Prépare un fichier de configuration valide dans src/test/resources/test_files
        String fichierConfig = "src/test/resources/test_files/colonie2";
        File configFile = new File(fichierConfig);
        assertTrue(configFile.exists(), "Le fichier de configuration n'existe pas : " + fichierConfig);

        // Simule l'entrée utilisateur pour quitter directement
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Instancie le menu avec le fichier de configuration
        MenuColonie2 menu = new MenuColonie2(fichierConfig);

        // Exécute le menu
        menu.afficherMenu();

        // Vérifie que le message de fin est affiché
        assertTrue(outputStream.toString().contains("Fin du programme."));
    }

    @Test
    void testAfficherMenu_OptionInvalide() throws Exception {
        // Prépare un fichier de configuration valide dans src/test/resources/test_files
        String fichierConfig = "src/test/resources/test_files/colonie3";
        File configFile = new File(fichierConfig);
        assertTrue(configFile.exists(), "Le fichier de configuration n'existe pas : " + fichierConfig);

        // Simule une option invalide
        String input = "5\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Instancie le menu avec le fichier de configuration
        MenuColonie2 menu = new MenuColonie2(fichierConfig);

        // Exécute le menu
        menu.afficherMenu();

        // Vérifie que le programme affiche un message d'erreur pour l'option invalide
        assertTrue(outputStream.toString().contains("Option invalide. Veuillez choisir entre 1, 2 ou 3."));
    }

    @Test
    void testAfficherMenu_EntréeNonEntier() throws Exception {
        // Prépare un fichier de configuration valide dans src/test/resources/test_files
        String fichierConfig = "src/test/resources/test_files/colonie1";
        File configFile = new File(fichierConfig);
        assertTrue(configFile.exists(), "Le fichier de configuration n'existe pas : " + fichierConfig);

        // Simule une entrée non entière
        String input = "abc\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Instancie le menu avec le fichier de configuration
        MenuColonie2 menu = new MenuColonie2(fichierConfig);

        // Exécute le menu
        menu.afficherMenu();

        // Vérifie que le programme affiche un message d'erreur pour l'entrée invalide
        assertTrue(outputStream.toString().contains("Entrée invalide. Veuillez entrer un nombre entier entre 1 et 3"));
    }
}
