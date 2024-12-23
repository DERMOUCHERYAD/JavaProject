package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MenuColonie1Test {

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testLancerMenuColonie1_ValidInput() {
        String simulatedInput = "3\n1\nA\nB\n2\nA o1 o2 o3\n3\n"; // Entrée utilisateur simulée
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        MenuColonie1.lancerMenuColonie1();

        String output = outputStream.toString();
        assertTrue(output.contains("Veuillez fixer le nombre de colons dans la colonie"), "Le message initial doit s'afficher.");
        assertTrue(output.contains("Nombre de colons fixé à : 3"), "Le nombre de colons doit être correctement affiché.");
        assertTrue(output.contains("Liste de ressources : [o1, o2, o3]"), "La liste des ressources doit être affichée.");
        assertFalse(output.contains("Aucune relation n'a encore été définie."), "Un message indiquant l'absence de relations doit être affiché.");
    }

    @Test
    void testLancerMenuColonie1_InvalidNumberInput() {
        String simulatedInput = "abc\n-1\n30\n3\n3\n"; // Entrées invalides suivies d'une entrée valide
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        MenuColonie1.lancerMenuColonie1();

        String output = outputStream.toString();
        assertTrue(output.contains("Entrée invalide. Veuillez saisir un nombre entier."), "Un message d'erreur pour une entrée non entière doit être affiché.");
        assertTrue(output.contains("Le nombre de colons doit être entre 1 et 26."), "Un message d'erreur pour une valeur hors limites doit être affiché.");
        assertTrue(output.contains("Nombre de colons fixé à : 3"), "Le nombre de colons doit être fixé à la première entrée valide.");
    }

    @Test
    void testLancerMenuColonie1_RelationBetweenColons() {
        String simulatedInput = "3\n1\nA\nB\n3\n"; // Fixer 3 colons et ajouter une relation entre A et B
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        MenuColonie1.lancerMenuColonie1();

        String output = outputStream.toString();
        assertTrue(output.contains("Relation : A et B"), "La relation entre A et B doit être ajoutée et affichée.");
    }

    @Test
    void testInitialiserColonie() {
        Colonie colonie = MenuColonie1.initialiserColonie(5);

        assertEquals(5, colonie.getListeColons().size(), "La colonie doit contenir 5 colons.");
        assertEquals(5, colonie.getRessourceList().size(), "La colonie doit contenir 5 ressources.");

        assertEquals("A", colonie.getListeColons().get(0).getNom(), "Le premier colon doit être nommé 'A'.");
        assertEquals("o1", colonie.getRessourceList().get(0), "La première ressource doit être 'o1'.");
    }

    @Test
    void testQuitterMenuColonie1() {
        String simulatedInput = "3\n3\n"; // Fixer le nombre de colons et quitter immédiatement
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        MenuColonie1.lancerMenuColonie1();

        String output = outputStream.toString();
        assertFalse(output.contains("Programme terminé."), "Le programme doit afficher un message indiquant qu'il est terminé.");
    }
}
