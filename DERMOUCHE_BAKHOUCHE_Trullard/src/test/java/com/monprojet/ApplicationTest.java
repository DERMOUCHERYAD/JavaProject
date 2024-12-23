package com.monprojet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        // Rediriger les sorties standard et d'erreur vers des flux capturés
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        // Restaurer les flux standard et d'erreur
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    void testMainWithNonExistingFile() {
        // Test avec un fichier qui n'existe pas
        String[] args = {"fichier_inexistant.txt"};
        Application.main(args);

        String error = errContent.toString();

        // Vérifiez que le message d'erreur est correct
        assertTrue(error.contains("Erreur : Le fichier spécifié n'existe pas"),
                "Le message d'erreur devrait indiquer que le fichier n'existe pas.");
    }



    @Test
    void testMainWithIOException() {
        // Simuler une situation où une IOException est levée
        // Cela nécessite un refactoring de la classe Application pour permettre le mock des opérations de fichiers
        String[] args = {"invalid_path/file.txt"};
        Application.main(args);

        String error = errContent.toString();
        assertFalse(error.contains("Erreur critique"),
                "Le programme devrait afficher une erreur critique en cas d'exception IOException.");
    }
}
