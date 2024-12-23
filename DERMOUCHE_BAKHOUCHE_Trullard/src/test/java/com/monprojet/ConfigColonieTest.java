package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigColonieTest {

    private static final String TEST_CONFIG_FILE = "test_config.txt";
    private static final String SAVE_FILE = "test_save.txt";

    @BeforeEach
    void setup() throws IOException {
        // Crée un fichier de configuration temporaire pour les tests
        try (FileWriter writer = new FileWriter(TEST_CONFIG_FILE)) {
            writer.write("colon(A).\n");
            writer.write("colon(B).\n");
            writer.write("colon(C).\n");
            writer.write("ressource(o1).\n");
            writer.write("ressource(o2).\n");
            writer.write("ressource(o3).\n");
            writer.write("deteste(A,B).\n");
            writer.write("deteste(B,C).\n");
            writer.write("preferences(A,o1,o2,o3).\n");
            writer.write("preferences(B,o2,o3,o1).\n");
            writer.write("preferences(C,o3,o1,o2).\n");
        }
    }

    @Test
    void testLireFichierConfig() throws IOException {
        ConfigColonie configColonie = new ConfigColonie(TEST_CONFIG_FILE);
        Colonie colonie = configColonie.LireFichierConfig();

        // Vérifie que les colons ont été ajoutés correctement
        assertEquals(3, colonie.getListeColons().size());
        assertNotNull(colonie.getColon("A"));
        assertNotNull(colonie.getColon("B"));
        assertNotNull(colonie.getColon("C"));

        // Vérifie que les ressources ont été ajoutées correctement
        assertEquals(3, colonie.getRessourceList().size());
        assertTrue(colonie.getRessourceList().contains("o1"));
        assertTrue(colonie.getRessourceList().contains("o2"));
        assertTrue(colonie.getRessourceList().contains("o3"));

        // Vérifie que les relations ont été ajoutées correctement
        assertEquals(2, colonie.getListeRelation().size());

        // Vérifie que les préférences des colons ont été ajoutées
        assertEquals(3, colonie.getColon("A").getListePreferences().size());
        assertEquals("o1", colonie.getColon("A").getListePreferences().get(0));
    }

    @Test
    void testSauvegarderSolution() throws IOException {
        ConfigColonie configColonie = new ConfigColonie(TEST_CONFIG_FILE);
        Colonie colonie = configColonie.LireFichierConfig();

        // Affecte des ressources aléatoires
        colonie.affecterRessourcesNaivement();

        // Sauvegarde la solution
        configColonie.sauvegarderSolution(SAVE_FILE);

        // Vérifie que le fichier de sauvegarde existe
        File saveFile = new File("src/test/resources/solutions/" + SAVE_FILE);
        assertTrue(saveFile.exists());

        // Vérifie que le contenu du fichier est correct
        List<String> lignes = Files.readAllLines(saveFile.toPath());
        assertEquals(3, lignes.size());
        for (String ligne : lignes) {
            assertTrue(ligne.matches("[A-C]:o[1-3]"));
        }
    }

    @Test
    void testErreurFichierInexistant() {
        ConfigColonie configColonie = new ConfigColonie("fichier_inexistant.txt");
        Exception exception = assertThrows(IOException.class, configColonie::LireFichierConfig);
        assertTrue(exception.getMessage().contains("n'existe pas dans le dossier"));
    }

    @Test
    void testErreurSyntaxeDansFichier() throws IOException {
        // Crée un fichier de configuration avec une erreur de syntaxe
        String invalidFile = "invalid_config.txt";
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("colon(A\n"); // Manque le ")" et le point
        }

        ConfigColonie configColonie = new ConfigColonie(invalidFile);
        Exception exception = assertThrows(IllegalArgumentException.class, configColonie::LireFichierConfig);
        assertTrue(exception.getMessage().contains("La ligne doit se terminer par un point"));

        // Supprime le fichier de test temporaire
        new File(invalidFile).delete();
    }

    @Test
    void testErreurColonManquantDansPreferences() throws IOException {
        // Crée un fichier de configuration avec une préférence pour un colon non défini
        String invalidFile = "invalid_preferences.txt";
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("colon(A).\n");
            writer.write("ressource(o1).\n");
            writer.write("preferences(B,o1).\n"); // B n'est pas défini
        }

        ConfigColonie configColonie = new ConfigColonie(invalidFile);
        Exception exception = assertThrows(IllegalArgumentException.class, configColonie::LireFichierConfig);
        assertTrue(exception.getMessage().contains("n'a pas été défini"));

        // Supprime le fichier de test temporaire
        new File(invalidFile).delete();
    }

    @Test
    void testErreurRessourceManquanteDansPreferences() throws IOException {
        // Crée un fichier de configuration avec une ressource manquante
        String invalidFile = "invalid_resources.txt";
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("colon(A).\n");
            writer.write("ressource(o1).\n");
            writer.write("preferences(A,o2).\n"); // o2 n'est pas défini
        }

        ConfigColonie configColonie = new ConfigColonie(invalidFile);
        Exception exception = assertThrows(IllegalArgumentException.class, configColonie::LireFichierConfig);
        assertTrue(exception.getMessage().contains("La ressource o2 n'a pas été définie"));

        // Supprime le fichier de test temporaire
        new File(invalidFile).delete();
    }
}
