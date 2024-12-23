package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    private Solver solver;
    private Colonie colonie;

    @BeforeEach
    void setUp() {
        solver = new Solver();
        colonie = new Colonie();

        // Création des colons
        Colon colonA = new Colon("A");
        Colon colonB = new Colon("B");
        Colon colonC = new Colon("C");

        // Ajout des colons à la colonie
        colonie.ajoutColon(colonA);
        colonie.ajoutColon(colonB);
        colonie.ajoutColon(colonC);

        // Ajout des ressources
        colonie.ajoutRessources("o1");
        colonie.ajoutRessources("o2");
        colonie.ajoutRessources("o3");

        // Définir les préférences des colons
        colonA.ajoutPreferences(Arrays.asList("o1", "o2", "o3"));
        colonB.ajoutPreferences(Arrays.asList("o2", "o3", "o1"));
        colonC.ajoutPreferences(Arrays.asList("o3", "o1", "o2"));

        // Création des relations (jalousie) entre les colons
        Relation relation1 = new Relation(colonA, colonB); // A et B ne s’aiment pas
        Relation relation2 = new Relation(colonB, colonC); // B et C ne s’aiment pas

        // Ajout des relations à la colonie
        colonie.ajoutListeRelation(relation1);
        colonie.ajoutListeRelation(relation2);

        // Affectation initiale des ressources (naïve)
        colonie.affecterRessourcesNaivement();
    }

    @Test
    void testSolve_SolutionValide() {
        // Appeler la méthode `solve` pour résoudre
        List<String> solution = solver.solve(colonie);

        // Vérifier que la solution retourne bien toutes les ressources assignées
        List<String> ressourcesAttribuees = new ArrayList<>();
        for (Colon colon : colonie.getListeColons()) {
            ressourcesAttribuees.add(colon.getRessourceAttribuee());
        }

        // Vérifier que toutes les ressources sont assignées sans doublons
        assertEquals(colonie.getRessourceList().size(), ressourcesAttribuees.size(),
                "Le nombre de ressources attribuées doit correspondre au nombre de ressources disponibles.");
        assertTrue(ressourcesAttribuees.containsAll(colonie.getRessourceList()),
                "Toutes les ressources doivent être attribuées.");
    }

    @Test
    void testSolve_JalousieReduite() {
        // Affecter initialement des ressources naïves
        int jalousieInitiale = colonie.getJalousie();

        // Appeler la méthode `solve` pour minimiser la jalousie
        solver.solve(colonie);
        int jalousieFinale = colonie.getJalousie();

        // Vérifier que la jalousie a diminué ou reste inchangée
        assertTrue(jalousieFinale <= jalousieInitiale,
                "La jalousie après la résolution doit être inférieure ou égale à la jalousie initiale.");
    }

    @Test
    void testSolve_ColonsEtRessourcesEgaux() {
        // Ajouter un nombre de colons différent des ressources
        colonie.ajoutColon(new Colon("D")); // Déséquilibre

        // Vérifier qu'une exception est levée
        Exception exception = assertThrows(IllegalArgumentException.class, () -> solver.solve(colonie));
        assertEquals("Le nombre de colons doit être égal au nombre de ressources.", exception.getMessage());
    }
}
