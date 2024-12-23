package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MenuGestionTest {

    private Colonie colonie;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        colonie = new Colonie();
        Colon colonA = new Colon("A");
        Colon colonB = new Colon("B");
        Colon colonC = new Colon("C");
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Arrays.asList("R2", "R3", "R1"));
        colonC.setListePreferences(Arrays.asList("R3", "R1", "R2"));
        colonie.ajoutColon(colonA);
        colonie.ajoutColon(colonB);
        colonie.ajoutColon(colonC);
        colonie.ajoutRessources("o1");
        colonie.ajoutRessources("o2");
        colonie.ajoutRessources("o3");
        colonA.setRessourceAttribuee("o1");
        colonB.setRessourceAttribuee("o2");
        colonC.setRessourceAttribuee("o3");

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testEchangerRessources() {
        String input = "1\nA\nB\n3\n"; // Choix 1, échanger ressources entre A et B, puis quitter
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> MenuGestion.lancerMenuGestion(colonie, scanner));

        assertEquals("o2", colonie.getColon("A").getRessourceAttribuee(), "La ressource d'A devrait être échangée avec B.");
        assertEquals("o1", colonie.getColon("B").getRessourceAttribuee(), "La ressource de B devrait être échangée avec A.");
        assertTrue(outputStream.toString().contains("Échange réussi entre A et B"), "Le succès de l'échange devrait être affiché.");
    }

    @Test
    void testColonNonTrouveDansEchange() {
        String input = "1\nA\nD\n3\n"; // Choix 1, deuxième colon n'existe pas, puis quitter
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> MenuGestion.lancerMenuGestion(colonie, scanner));

        assertTrue(outputStream.toString().contains("Erreur : Colon D non trouvé."), "Un message d'erreur devrait être affiché pour le colon non trouvé.");
    }

    @Test
    void testAfficherNombreDeColonsJaloux() {
        String input = "2\n3\n"; // Choix 2 pour afficher les colons jaloux, puis quitter
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> MenuGestion.lancerMenuGestion(colonie, scanner));

        assertFalse(outputStream.toString().contains("Liste des colons jaloux"), "La liste des colons jaloux devrait être affichée.");
    }

    @Test
    void testOptionInvalide() {
        String input = "5\n3\n"; // Option invalide, puis quitter
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> MenuGestion.lancerMenuGestion(colonie, scanner));

        assertTrue(outputStream.toString().contains("Option invalide"), "Un message d'option invalide devrait être affiché.");
    }

    @Test
    void testQuitterMenu() {
        String input = "3\n"; // Choix 3 pour quitter immédiatement
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> MenuGestion.lancerMenuGestion(colonie, scanner));

        assertTrue(outputStream.toString().contains("Fin du programme"), "Le programme devrait indiquer qu'il est terminé.");
    }
}
