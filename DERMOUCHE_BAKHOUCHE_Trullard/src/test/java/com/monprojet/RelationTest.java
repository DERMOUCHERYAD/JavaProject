package com.monprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RelationTest {

    private Colon colonA;
    private Colon colonB;
    private Colon colonC;
    private Relation relationAB;
    private Relation relationBA; // Relation inversée de AB
    private Relation relationAC;

    @BeforeEach
    void setUp() {
        colonA = new Colon("A");
        colonB = new Colon("B");
        colonC = new Colon("C");

        relationAB = new Relation(colonA, colonB);
        relationBA = new Relation(colonB, colonA);
        relationAC = new Relation(colonA, colonC);
    }

    // Test du constructeur avec deux colons différents
    @Test
    void testConstructorValid() {
        assertEquals(colonA, relationAB.getC1(), "c1 devrait être colonA");
        assertEquals(colonB, relationAB.getC2(), "c2 devrait être colonB");
    }

    // Test du constructeur avec les mêmes colons (devrait lancer une exception)
    @Test
    void testConstructorSameColon() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Relation(colonA, colonA);
        });
        assertEquals("Les deux colons dans une relation doivent être différents.", exception.getMessage());
    }

    // Test du constructeur de copie
    @Test
    void testCopyConstructor() {
        Relation copieAB = new Relation(relationAB);
        assertEquals(relationAB.getC1(), copieAB.getC1(), "c1 de la copie devrait être colonA");
        assertEquals(relationAB.getC2(), copieAB.getC2(), "c2 de la copie devrait être colonB");
        assertEquals(relationAB, copieAB, "La copie devrait être égale à l'original");
    }

    // Test des getters
    @Test
    void testGetters() {
        assertEquals(colonA, relationAB.getC1(), "getC1() devrait retourner colonA");
        assertEquals(colonB, relationAB.getC2(), "getC2() devrait retourner colonB");
    }

    // Test des setters avec des colons valides
    @Test
    void testSettersValid() {
        relationAB.setC1(colonC);
        assertEquals(colonC, relationAB.getC1(), "c1 devrait être mis à jour à colonC");

        relationAB.setC2(colonA);
        assertEquals(colonA, relationAB.getC2(), "c2 devrait être mis à jour à colonA");
    }

    // Test des setters avec le même colon (devrait lancer une exception)
    @Test
    void testSettersSameColon() {
        // Essayer de mettre c1 à colonB alors que c2 est déjà colonB
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            relationAB.setC1(colonB);
        });
        assertEquals("Les deux colons dans une relation doivent être différents.", exception1.getMessage());

        // Essayer de mettre c2 à colonA alors que c1 est déjà colonA
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            relationAB.setC2(colonA);
        });
        assertEquals("Les deux colons dans une relation doivent être différents.", exception2.getMessage());
    }

    // Test de la méthode getRelation
    @Test
    void testGetRelation() {
        String expected = "Relation : A et B";
        assertEquals(expected, relationAB.getRelation(), "getRelation() devrait retourner la chaîne correcte");
    }

    // Test de la méthode equals (relations identiques)
    @Test
    void testEqualsSameOrder() {
        Relation anotherAB = new Relation(colonA, colonB);
        assertEquals(relationAB, anotherAB, "Deux relations avec les mêmes colons dans le même ordre devraient être égales");
    }

    // Test de la méthode equals (relations inversées)
    @Test
    void testEqualsInverseOrder() {
        assertEquals(relationAB, relationBA, "Deux relations avec les mêmes colons mais dans un ordre différent devraient être égales");
    }

    // Test de la méthode equals (relations différentes)
    @Test
    void testEqualsDifferentRelations() {
        assertNotEquals(relationAB, relationAC, "Deux relations avec des colons différents ne devraient pas être égales");
    }

    // Test de la méthode hashCode (relations identiques)
    @Test
    void testHashCodeSameOrder() {
        Relation anotherAB = new Relation(colonA, colonB);
        assertEquals(relationAB.hashCode(), anotherAB.hashCode(), "hashCode devrait être identique pour des relations égales");
    }

    // Test de la méthode hashCode (relations inversées)
    @Test
    void testHashCodeInverseOrder() {
        assertEquals(relationAB.hashCode(), relationBA.hashCode(), "hashCode devrait être identique pour des relations égales même inversées");
    }

    // Test de la méthode toString
    @Test
    void testToString() {
        String expected = "Relation{c1=A, c2=B}";
        assertEquals(expected, relationAB.toString(), "toString() devrait retourner la chaîne correcte");
    }
}
