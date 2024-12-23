package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ColonTest {

    private Colon colon;
    private List<String> ressourcesDisponibles;

    @BeforeEach
    void setUp() {
        colon = new Colon("A");
        ressourcesDisponibles = Arrays.asList("o1", "o2", "o3");
    }

    // Test du constructeur avec nom
    @Test
    void testConstructorWithName() {
        assertEquals("A", colon.getNom(), "Le nom doit être 'A'");
        assertTrue(colon.getListePreferences().isEmpty(), "La liste des préférences doit être vide");
        assertNull(colon.getRessourceAttribuee(), "La ressource attribuée doit être null");
    }

    // Test du constructeur de copie
    @Test
    void testCopyConstructor() {
        colon.ajoutPreferences(ressourcesDisponibles);
        colon.setRessourceAttribuee("o2");

        Colon copieColon = new Colon(colon);
        assertEquals(colon.getNom(), copieColon.getNom(), "Le nom doit être identique dans la copie");
        assertEquals(colon.getListePreferences(), copieColon.getListePreferences(),
                "La liste des préférences doit être identique dans la copie");
        assertEquals(colon.getRessourceAttribuee(), copieColon.getRessourceAttribuee(),
                "La ressource attribuée doit être identique dans la copie");
    }

    // Test de la méthode toString
    @Test
    void testToString() {
        colon.ajoutPreferences(ressourcesDisponibles);
        colon.setRessourceAttribuee("o1");
        String expected = "Colon [nom = A, ListePreferences = [o1, o2, o3], RessourceAttribuee = o1]";
        assertEquals(expected, colon.toString(), "Le toString ne correspond pas à l'attendu");
    }

    // Test de l'ajout de préférences lorsque la liste est vide
    @Test
    void testAjoutPreferencesWhenEmpty() {
        boolean result = colon.ajoutPreferences(ressourcesDisponibles);
        assertTrue(result, "L'ajout des préférences devrait réussir");
        assertEquals(ressourcesDisponibles, colon.getListePreferences(),
                "La liste des préférences ne correspond pas aux ressources disponibles");
    }

    // Test de l'ajout de préférences lorsque la liste n'est pas vide
    @Test
    void testAjoutPreferencesWhenNotEmpty() {
        colon.ajoutPreferences(ressourcesDisponibles);
        boolean result = colon.ajoutPreferences(Arrays.asList("o4"));
        assertFalse(result, "L'ajout des préférences devrait échouer car la liste n'est pas vide");
        assertEquals(ressourcesDisponibles, colon.getListePreferences(),
                "La liste des préférences ne doit pas avoir été modifiée");
    }

    // Test des getters
    @Test
    void testGetters() {
        assertEquals("A", colon.getNom(), "Le getter getNom() ne retourne pas la valeur correcte");
        assertTrue(colon.getListePreferences().isEmpty(), "La liste des préférences doit être vide au départ");
        assertNull(colon.getRessourceAttribuee(), "Le getter getRessourceAttribuee() doit retourner null au départ");
    }

    // Test du setter setListePreferences avec des données valides
    @Test
    void testSetListePreferencesValid() {
        colon.setListePreferences(ressourcesDisponibles);
        assertEquals(ressourcesDisponibles, colon.getListePreferences(),
                "La liste des préférences ne doit pas être vide après le setter");
    }

    // Test du setter setListePreferences avec une liste nulle
    @Test
    void testSetListePreferencesNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            colon.setListePreferences(null);
        });
        assertEquals("La liste des préférences ne peut pas être nulle.", exception.getMessage(),
                "Le message d'exception ne correspond pas");
    }

    // Test du setter setListePreferences avec des ressources non alphanumériques
    @Test
    void testSetListePreferencesInvalid() {
        List<String> invalidRessources = Arrays.asList("o1", "o#2", "o3");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            colon.setListePreferences(invalidRessources);
        });
        assertEquals("Toutes les ressources doivent être alphanumériques.", exception.getMessage(),
                "Le message d'exception ne correspond pas");
    }

    // Test du setter setRessourceAttribuee avec une valeur valide
    @Test
    void testSetRessourceAttribueeValid() {
        colon.setRessourceAttribuee("o1");
        assertEquals("o1", colon.getRessourceAttribuee(),
                "La ressource attribuée ne correspond pas à la valeur définie");
    }

    // Test du setter setRessourceAttribuee avec une valeur nulle
    @Test
    void testSetRessourceAttribueeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            colon.setRessourceAttribuee(null);
        });
        assertEquals("La ressource attribuée doit être alphanumérique.", exception.getMessage(),
                "Le message d'exception ne correspond pas");
    }

    // Test de la méthode prefereRessource(String ressource) quand les conditions sont remplies
    @Test
    void testPrefereRessourceTrue() {
        colon.setListePreferences(Arrays.asList("o1", "o2", "o3"));
        colon.setRessourceAttribuee("o3");
        assertTrue(colon.prefereRessource("o1"),
                "La o1 devrait être préférée à o3");
    }

    // Test de la méthode prefereRessource(String ressource) quand les conditions ne sont pas remplies
    @Test
    void testPrefereRessourceFalse() {
        colon.setListePreferences(Arrays.asList("o1", "o2", "o3"));
        colon.setRessourceAttribuee("o1");
        assertFalse(colon.prefereRessource("o3"),
                "La o3 ne devrait pas être préférée à o1");
    }

    // Test de la méthode prefereRessource(String ressource) quand une des ressources n'est pas dans la liste
    @Test
    void testPrefereRessourceMissing() {
        colon.setListePreferences(Arrays.asList("o1", "o2"));
        colon.setRessourceAttribuee("o3");
        assertFalse(colon.prefereRessource("o1"),
                "La méthode devrait retourner false si une ressource n'est pas dans la liste");
    }

    // Test de la méthode prefereRessource(String r1, String r2) avec r1 préféré à r2
    @Test
    void testPrefereRessourceTwoParametersTrue() {
        colon.setListePreferences(Arrays.asList("o1", "o2", "o3"));
        assertTrue(colon.prefereRessource("o1", "o3"),
                "o1 devrait être préférée à o3");
    }

    // Test de la méthode prefereRessource(String r1, String r2) avec r2 préféré à r1
    @Test
    void testPrefereRessourceTwoParametersFalse() {
        colon.setListePreferences(Arrays.asList("o1", "o2", "o3"));
        assertFalse(colon.prefereRessource("o3", "o1"),
                "o3 ne devrait pas être préférée à o1");
    }

    // Test de la méthode prefereRessource(String r1, String r2) avec l'une des ressources manquantes
    @Test
    void testPrefereRessourceTwoParametersMissing() {
        colon.setListePreferences(Arrays.asList("o1", "o2"));
        assertFalse(colon.prefereRessource("o1", "o3"),
                "La méthode devrait retourner false si l'une des ressources n'est pas dans la liste");
    }
}
