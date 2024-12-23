package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ColonieTest {

    private Colonie colonie;
    private Colon colonA;
    private Colon colonB;
    private Colon colonC;
    private Relation relationAB;
    private Relation relationBC;
    private Relation relationAC;

    @BeforeEach
    void setUp() {
        colonie = new Colonie();

        // Création des colons
        colonA = new Colon("A");
        colonB = new Colon("B");
        colonC = new Colon("C");

        // Ajout des colons à la colonie
        colonie.ajoutColon(colonA);
        colonie.ajoutColon(colonB);
        colonie.ajoutColon(colonC);

        // Ajout de ressources
        colonie.ajoutRessources("R1");
        colonie.ajoutRessources("R2");
        colonie.ajoutRessources("R3");

        // Création des relations
        relationAB = new Relation(colonA, colonB);
        relationBC = new Relation(colonB, colonC);
        relationAC = new Relation(colonA, colonC);
    }

    // Test du constructeur par défaut
    @Test
    void testDefaultConstructor() {
        Colonie vide = new Colonie();
        assertTrue(vide.getListeColons().isEmpty(), "La liste des colons devrait être vide.");
        assertTrue(vide.getRessourceList().isEmpty(), "La liste des ressources devrait être vide.");
        assertTrue(vide.getListeRelation().isEmpty(), "La liste des relations devrait être vide.");
    }

    // Test du constructeur de copie
    @Test
    void testCopyConstructor() {
        colonie.ajoutListeRelation(relationAB);
        colonie.ajoutListeRelation(relationBC);

        Colonie copie = new Colonie(colonie);

        // Vérifier que les listes ne sont pas les mêmes instances
        assertNotSame(colonie.getListeColons(), copie.getListeColons(), "Les listes de colons ne doivent pas être la même instance.");
        assertNotSame(colonie.getRessourceList(), copie.getRessourceList(), "Les listes de ressources ne doivent pas être la même instance.");
        assertNotSame(colonie.getListeRelation(), copie.getListeRelation(), "Les listes de relations ne doivent pas être la même instance.");

        // Vérifier l'égalité des contenus
        assertEquals(colonie.getListeColons().size(), copie.getListeColons().size(), "Les listes de colons doivent avoir la même taille.");
        assertEquals(colonie.getRessourceList(), copie.getRessourceList(), "Les listes de ressources doivent être identiques.");
        assertEquals(colonie.getListeRelation(), copie.getListeRelation(), "Les listes de relations doivent être identiques.");
    }

    // Test de l'ajout d'un colon
    @Test
    void testAjoutColon() {
        Colon colonD = new Colon("D");
        colonie.ajoutColon(colonD);
        assertTrue(colonie.getListeColons().contains(colonD), "La colonie devrait contenir le colon D.");
    }

    // Test de l'ajout d'une relation valide
    @Test
    void testAjoutListeRelationValide() {
        colonie.ajoutListeRelation(relationAB);
        assertTrue(colonie.getListeRelation().contains(relationAB), "La colonie devrait contenir la relationAB.");
    }






    // Test de récupération d'un colon par nom existant
    @Test
    void testGetColonByNameExists() {
        Colon found = colonie.getColon("A");
        assertNotNull(found, "Le colon A devrait être trouvé.");
        assertEquals(colonA, found, "Le colon trouvé devrait être colonA.");
    }

    // Test de récupération d'un colon par nom non existant
    @Test
    void testGetColonByNameDoesNotExist() {
        Colon found = colonie.getColon("Z");
        assertNull(found, "Le colon Z ne devrait pas être trouvé.");
    }

    // Test de la méthode neSontPasAmis (colons avec relation)
    @Test
    void testNeSontPasAmisTrue() {
        colonie.ajoutListeRelation(relationAB);
        assertTrue(colonie.neSontPasAmis(colonA, colonB), "Colon A et Colon B ne devraient pas être amis.");
    }

    // Test de la méthode neSontPasAmis (colons sans relation)
    @Test
    void testNeSontPasAmisFalse() {
        assertFalse(colonie.neSontPasAmis(colonA, colonC), "Colon A et Colon C devraient être amis.");
    }

    // Test de l'ajout de ressources
    @Test
    void testAjoutRessources() {
        colonie.ajoutRessources("R4");
        assertTrue(colonie.getRessourceList().contains("R4"), "La colonie devrait contenir la ressource R4.");
    }

    // Test de la méthode assignerRessource
    @Test
    void testAssignerRessource() {
        colonie.assignerRessource(colonA, "R1");
        assertEquals("R1", colonA.getRessourceAttribuee(), "La ressource de Colon A devrait être R1.");
    }

    // Test de la méthode assignerRessource (ré-assignation)
    @Test
    void testAssignerRessourceReassign() {
        colonie.assignerRessource(colonA, "R1");
        assertEquals("R1", colonA.getRessourceAttribuee(), "La ressource de Colon A devrait être R1.");

        // Réassigner une nouvelle ressource
        colonie.assignerRessource(colonA, "R2");
        assertEquals("R2", colonA.getRessourceAttribuee(), "La ressource de Colon A devrait être R2 après réassignation.");
    }

    // Test de l'échange de ressources entre deux colons valides
    @Test
    void testEchangeRessourceColonValid() {
        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");

        colonie.echangeRessourceColon(colonA, colonB);

        assertEquals("R2", colonA.getRessourceAttribuee(), "La ressource de Colon A devrait être R2 après l'échange.");
        assertEquals("R1", colonB.getRessourceAttribuee(), "La ressource de Colon B devrait être R1 après l'échange.");
    }

    // Test de l'échange de ressources avec un colon sans ressource attribuée
    @Test
    void testEchangeRessourceColonWithoutResource() {
        colonie.assignerRessource(colonA, "R1");
        // Colon B n'a pas de ressource attribuée

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            colonie.echangeRessourceColon(colonA, colonB);
        });
        assertEquals("\u001B[31mLes colons doivent avoir des ressources attribuées avant l'échange.\u001B[0m", exception.getMessage());
    }

    // Test de l'échange de ressources avec le même colon
    @Test
    void testEchangeRessourceColonSameColon() {
        colonie.assignerRessource(colonA, "R1");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            colonie.echangeRessourceColon(colonA, colonA);
        });
        assertEquals("Les colons doivent être différents.", exception.getMessage());
    }

    // Test de l'affectation naïve des ressources
    @Test
    void testAffecterRessourcesNaivement() {
        // S'assurer qu'il y a suffisamment de ressources
        colonie.ajoutRessources("R1");
        colonie.ajoutRessources("R2");
        colonie.ajoutRessources("R3");

        colonie.affecterRessourcesNaivement();

        assertNotNull(colonA.getRessourceAttribuee(), "Colon A devrait avoir une ressource attribuée.");
        assertNotNull(colonB.getRessourceAttribuee(), "Colon B devrait avoir une ressource attribuée.");
        assertNotNull(colonC.getRessourceAttribuee(), "Colon C devrait avoir une ressource attribuée.");

        List<String> assigned = Arrays.asList(colonA.getRessourceAttribuee(),
                colonB.getRessourceAttribuee(),
                colonC.getRessourceAttribuee());

        assertEquals(3, assigned.size(), "Il devrait y avoir 3 ressources attribuées.");
        assertTrue(colonie.getRessourceList().containsAll(assigned), "Toutes les ressources attribuées devraient être dans la liste des ressources.");
    }

    // Test de la vérification des affectations de ressources (toutes assignées)
    @Test
    void testVerifierAffectationDesRessourcesSuccess() throws Exception {
        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        assertTrue(colonie.verifierAffectationDesRessources(), "Toutes les affectations de ressources devraient être valides.");
    }


    // Test de la vérification des listes de préférences (tout valide)
    @Test
    void testVerifierListePreferenceValid() {
        // Ajouter des préférences valides
        colonA.setListePreferences(Arrays.asList("R1", "R2"));
        colonB.setListePreferences(Arrays.asList("R2", "R3"));
        colonC.setListePreferences(Arrays.asList("R1", "R3"));

        assertTrue(colonie.verifierListePreference(), "Toutes les listes de préférences devraient être valides.");
    }

    // Test de la vérification des listes de préférences (certains vides)
    @Test
    void testVerifierListePreferenceWithEmptyPreferences() {
        colonA.setListePreferences(Arrays.asList("R1", "R2"));
        colonB.setListePreferences(Collections.emptyList()); // Liste vide
        colonC.setListePreferences(Arrays.asList("R1", "R3"));

        assertFalse(colonie.verifierListePreference(), "La vérification devrait échouer car Colon B a une liste de préférences vide.");
    }

    // Test de la méthode compterJalousiesPour (cas normal)
    @Test
    void testCompterJalousiesPour() throws Exception {
        // Définir les listes de préférences
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Arrays.asList("R2", "R3", "R1"));
        colonC.setListePreferences(Arrays.asList("R3", "R1", "R2"));

        colonie.assignerRessource(colonA, "R3");
        colonie.assignerRessource(colonB, "R1");
        colonie.assignerRessource(colonC, "R2");

        // Ajouter les relations
        colonie.ajoutListeRelation(new Relation(colonA, colonB));
        colonie.ajoutListeRelation(new Relation(colonB, colonC));
        colonie.ajoutListeRelation(new Relation(colonC, colonA));


        // Comptage des jalousies
        int jalousiesA = colonie.compterJalousiesPour(colonA);
        int jalousiesB = colonie.compterJalousiesPour(colonB);
        int jalousiesC = colonie.compterJalousiesPour(colonC);

        // Vérifications
        assertEquals(2, jalousiesA, "Colon A devrait avoir 2 jaloux.");
        assertEquals(2, jalousiesB, "Colon B devrait avoir 2 jaloux.");
        assertEquals(2, jalousiesC, "Colon C devrait avoir 2 jaloux.");
    }

    // Test de la méthode compterJalousiesPour avec une liste de préférences vide
    @Test
    void testCompterJalousiesPourEmptyPreferences() {
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Collections.emptyList()); // Liste vide
        colonC.setListePreferences(Arrays.asList("R3", "R1", "R2"));

        colonie.ajoutListeRelation(relationAB); // Relation A-B
        colonie.ajoutListeRelation(relationBC); // Relation B-C

        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        Exception exception = assertThrows(Exception.class, () -> {
            colonie.compterJalousiesPour(colonB);
        });
        assertEquals("La liste des préférences est vide.", exception.getMessage());
    }

    // Test de la méthode getJalousie (cas normal)
    @Test
    void testGetJalousie() {
        // Définir les listes de préférences
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Arrays.asList("R2", "R3", "R1"));
        colonC.setListePreferences(Arrays.asList("R3", "R1", "R2"));

        // Ajouter des relations
        colonie.ajoutListeRelation(relationAB); // Relation A-B
        colonie.ajoutListeRelation(relationBC); // Relation B-C

        // Assigner des ressources
        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        // Calcul du jalousie
        assertEquals(0, colonie.getJalousie(), "Il devrait pas y avoir de jalousies.");
    }

    // Test de la méthode getJalousie (aucune jalousie)
    @Test
    void testGetJalousieNoJalousie() {
        // Définir les listes de préférences
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonC.setListePreferences(Arrays.asList("R1", "R2", "R3"));

        // Ajouter des relations
        colonie.ajoutListeRelation(relationAB); // Relation A-B
        colonie.ajoutListeRelation(relationBC); // Relation B-C

        // Assigner des ressources
        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R1"); // Même ressource que A
        colonie.assignerRessource(colonC, "R1"); // Même ressource que A et B

        // Calcul du jalousie
        assertEquals(0, colonie.getJalousie(), "Il ne devrait y avoir aucune jalousie.");
    }

    // Test de la méthode afficheRelation (visuel, pas de vérification d'assertion)
    @Test
    void testAfficheRelation() {
        colonie.ajoutListeRelation(relationAB);
        colonie.ajoutListeRelation(relationBC);

        // Cette méthode affiche simplement les relations. Nous vérifions qu'elle ne lève pas d'exception.
        assertDoesNotThrow(() -> colonie.afficheRelation(), "L'affichage des relations ne devrait pas lever d'exception.");
    }

    // Test de la méthode affichelisteJaloux (visuel, pas de vérification d'assertion)
    @Test
    void testAffichelisteJaloux() {
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Arrays.asList("R2", "R3", "R1"));
        colonC.setListePreferences(Arrays.asList("R3", "R1", "R2"));

        colonie.ajoutListeRelation(relationAB); // Relation A-B
        colonie.ajoutListeRelation(relationBC); // Relation B-C

        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        // Cette méthode affiche la liste des jaloux. Nous vérifions qu'elle ne lève pas d'exception.
        assertDoesNotThrow(() -> colonie.affichelisteJaloux(), "L'affichage des jaloux ne devrait pas lever d'exception.");
    }

    // Test de la méthode afficherListeColons (visuel, pas de vérification d'assertion)
    @Test
    void testAfficherListeColons() {
        // Cette méthode affiche simplement la liste des colons. Nous vérifions qu'elle ne lève pas d'exception.
        assertDoesNotThrow(() -> colonie.afficherListeColons(), "L'affichage de la liste des colons ne devrait pas lever d'exception.");
    }

    // Test de la méthode affichageRessource (visuel, pas de vérification d'assertion)
    @Test
    void testAffichageRessource() {
        colonie.assignerRessource(colonA, "R1");
            colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        // Cette méthode affiche les ressources des colons. Nous vérifions qu'elle ne lève pas d'exception.
        assertDoesNotThrow(() -> colonie.affichageRessource(), "L'affichage des ressources ne devrait pas lever d'exception.");
    }

    // Test de la méthode afficherAffectations (visuel, pas de vérification d'assertion)
    @Test
    void testAfficherAffectations() {
        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        // Cette méthode affiche les affectations. Nous vérifions qu'elle ne lève pas d'exception.
        assertDoesNotThrow(() -> colonie.afficherAffectations(), "L'affichage des affectations ne devrait pas lever d'exception.");
    }

    // Test de la méthode CalculCoutAffcetation (visuel, pas de vérification d'assertion)
    @Test
    void testCalculCoutAffectation() {
        // Définir les listes de préférences
        colonA.setListePreferences(Arrays.asList("R1", "R2", "R3"));
        colonB.setListePreferences(Arrays.asList("R2", "R3", "R1"));
        colonC.setListePreferences(Arrays.asList("R3", "R1", "R2"));

        // Ajouter des relations
        colonie.ajoutListeRelation(relationAB); // Relation A-B
        colonie.ajoutListeRelation(relationBC); // Relation B-C

        // Assigner des ressources
        colonie.assignerRessource(colonA, "R1");
        colonie.assignerRessource(colonB, "R2");
        colonie.assignerRessource(colonC, "R3");

        // Cette méthode affiche le coût. Nous vérifions qu'elle ne lève pas d'exception.
        assertDoesNotThrow(() -> colonie.CalculCoutAffcetation(), "Le calcul du coût d'affectation ne devrait pas lever d'exception.");
    }

}
