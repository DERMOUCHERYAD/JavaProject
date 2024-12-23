package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MenuCreationTest {

    private Colonie colonie;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Initialisation de la colonie avec des données de test
        colonie = new Colonie();
        colonie.ajoutColon(new Colon("A"));
        colonie.ajoutColon(new Colon("B"));
        colonie.ajoutColon(new Colon("C"));
        colonie.ajoutRessources("o1");
        colonie.ajoutRessources("o2");
        colonie.ajoutRessources("o3");

        // Rediriger la sortie standard pour capturer les messages affichés
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testAjouterRelation() throws Exception {
        // Simulation de l'entrée utilisateur : ajout d'une relation entre A et B, puis quitter
        String input = "1\nA\nB\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        MenuCreation menu = new MenuCreation(colonie, scanner, 3);
        menu.lancerConfiguration();

        // Vérification que la relation a été ajoutée
        assertEquals(1, colonie.getListeRelation().size(), "Une relation devrait être ajoutée.");
        assertTrue(outputStream.toString().contains("Relation : A et B"), "La relation devrait être affichée.");
    }

    @Test
    void testAjouterPreferences() throws Exception {
        // Simulation de l'entrée utilisateur : ajout des préférences pour le colon A, puis quitter
        String input = "2\nA o1 o2 o3\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        MenuCreation menu = new MenuCreation(colonie, scanner, 3);
        menu.lancerConfiguration();

        // Vérification que les préférences ont été ajoutées
        assertEquals(3, colonie.getColon("A").getListePreferences().size(), "Les préférences devraient être ajoutées.");
        assertTrue(outputStream.toString().contains("Préférences ajoutées avec succès"), "Un message de succès devrait être affiché.");
    }

    @Test
    void testOptionInvalide() throws Exception {
        // Simulation d'une option invalide, puis quitter
        String input = "5\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        MenuCreation menu = new MenuCreation(colonie, scanner, 3);
        menu.lancerConfiguration();

        // Vérification que le message d'erreur pour une option invalide est affiché
        assertTrue(outputStream.toString().contains("Option invalide"), "Un message d'option invalide devrait être affiché.");
    }

    @Test
    void testQuitterMenuSansAction() throws Exception {
        // Simulation de l'entrée utilisateur : quitter directement le menu
        String input = "3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        MenuCreation menu = new MenuCreation(colonie, scanner, 3);
        menu.lancerConfiguration();

        // Vérification que le programme se termine sans erreurs
        assertTrue(outputStream.toString().contains("Fin du programme"), "Le programme devrait se terminer correctement.");
    }

    @Test
    void testErreurRessourceInvalideDansPreferences() throws Exception {
        // Simulation d'une entrée complète avec une ressource invalide
        String input = "2\nA o1 o4 o3\n3\n"; // Ajouter une option pour quitter après l'erreur
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        MenuCreation menu = new MenuCreation(colonie, scanner, 3);
        menu.lancerConfiguration();

        // Vérifiez que l'erreur pour la ressource invalide est affichée
        assertTrue(outputStream.toString().contains("Erreur : La ressource o4 n'existe pas."), "Une erreur pour ressource invalide devrait être affichée.");
    }

    @Test
    void testAjouterRelationEntreLeMêmeColon() throws Exception {
        // Simulation de l'entrée utilisateur : ajout d'une relation entre le même colon
        String input = "1\nA\nA\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        MenuCreation menu = new MenuCreation(colonie, scanner, 3);
        menu.lancerConfiguration();

        // Vérification que la relation entre le même colon n'est pas autorisée
        assertTrue(outputStream.toString().contains("Erreur : Les deux colons doivent être différents."), "Une erreur devrait être affichée pour une relation entre le même colon.");
    }
}
