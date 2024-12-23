package com.monprojet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.monprojet.Relation;
import com.monprojet.Colon;

/**
 * La classe Colonie représente une colonie avec une liste de colons, des ressources,
 * et des relations définissant des jalousies entre colons.
 */
public class Colonie {
    private ArrayList<Colon> ListeColons;
    private ArrayList<String> RessourceList;
    private ArrayList<Relation> ListeRelation;
    // Couleur rouge
    final String RED_TEXT = "\033[31m";
    final String RESET_TEXT = "\033[0m";

    public Colonie() {
        this.ListeColons = new ArrayList<>();
        this.RessourceList = new ArrayList<>();
        this.ListeRelation = new ArrayList<>();
    }





    public Colonie(Colonie autre) {
        this.ListeColons = new ArrayList<>();
        for (Colon colon : autre.getListeColons()) {
            this.ListeColons.add(new Colon(colon));
        }
        this.RessourceList = new ArrayList<>(autre.getRessourceList());
        this.ListeRelation = new ArrayList<>();
        for (Relation relation : autre.getListeRelation()) {
            this.ListeRelation.add(new Relation(relation));
        }
    }

    public void afficherListeColons() {
        int i = 1;
        for (Colon colon : ListeColons) {
            System.out.println(i + " : " + colon);
            i++;
        }
    }

    // Affiche les ressources des colons de la colonie.
    public void affichageRessource(){
        for (Colon colon : ListeColons) {
            System.out.println(colon.getNom()+" : " + colon.getRessourceAttribuee());
        }
    }

    public void ajoutColon(Colon c) {
        this.ListeColons.add(c);
    }

    // Retourne true si c1 et c2 ne s'aiment pas.
    public boolean neSontPasAmis(Colon c1, Colon c2) {
        for (Relation relation : ListeRelation) {
            if ((relation.getC1().equals(c1) && relation.getC2().equals(c2)) ||
                    (relation.getC1().equals(c2) && relation.getC2().equals(c1))) {
                return true;
            }
        }
        return false;
    }

    // Ajout d'une ressource à la liste des ressources
    public void ajoutRessources(String objet) {
        this.RessourceList.add(objet);
    }

    // Retourne la liste des Ressources.
    public List<String> getRessourceList() {
        return RessourceList;
    }

    // Méthode getColon qui recherche par String
    public Colon getColon(String nom) {
        for (Colon tmp : ListeColons) {
            // Changement : on utilise .equals(...) pour comparer des chaînes
            if (tmp.getNom().equals(nom)) {
                return tmp;
            }
        }
        return null;
    }

    // Ajoute une relation de jalousie à la colonie.
    public void ajoutListeRelation(Relation p) {
        Colon colon1 = p.getC1();
        Colon colon2 = p.getC2();

        if (colon1.equals(colon2)) {
            throw new IllegalArgumentException(
                    " Les deux colons dans une relation doivent être différents."
            );
        }

        for (Relation relation : ListeRelation) {
            if ((relation.getC1().equals(colon1) && relation.getC2().equals(colon2))
                    || (relation.getC1().equals(colon2) && relation.getC2().equals(colon1))) {
                afficheRelation();
                throw new IllegalArgumentException(
                        " La relation entre " + colon1.getNom()
                                + " et " + colon2.getNom() + " existe déjà."
                );
            }
        }

        this.ListeRelation.add(p);
    }

    // Affiche les colons ainsi que les ressources qui leurs sont attribuées.
    public void afficherAffectations() {
        System.out.println("Affectations des ressources :");
        for (Colon colon : ListeColons) {
            System.out.println("Colon " + colon.getNom() + " -> Ressource " + colon.getRessourceAttribuee());
        }
    }

    // Echange les ressources entre deux Colons.
    public void echangeRessourceColon(Colon c1, Colon c2) {
        if (c1.equals(c2)) {
            throw new IllegalArgumentException("Les colons doivent être différents.");
        }
        if (c1.getRessourceAttribuee() == null || c2.getRessourceAttribuee() == null) {
            throw new IllegalStateException(
                    RED_TEXT + "Les colons doivent avoir des ressources attribuées avant l'échange." + RESET_TEXT
            );
        }
        String tmp = c1.getRessourceAttribuee();
        c1.setRessourceAttribuee(c2.getRessourceAttribuee());
        c2.setRessourceAttribuee(tmp);
    }

    // Affiche les ressources possédées par les colons.
    public void afficheRessourceDesColons() {
        for (Colon c : ListeColons) {
            System.out.println(c.toString());
        }
    }

    // Affecte des ressources aléatoirement à chaque colon
    public void affecterRessourcesNaivement() {
        List<String> ressourcesDispo = new ArrayList<>(this.RessourceList);
        Collections.shuffle(ressourcesDispo);
        int index = 0;
        for (Colon colon : ListeColons) {
            colon.setRessourceAttribuee(ressourcesDispo.get(index));
            index++;
        }
        System.out.println("Ressources assignées aléatoirement à chaque colon : ");
    }

    // Vérifie si tout les colons possèdent une ressource.
    public boolean verifierAffectationDesRessources() throws Exception {
        for (Colon colon : ListeColons) {
            if (colon.getRessourceAttribuee().isEmpty()) {
                throw new Exception("Erreur : Tous les colons n'ont pas encore une ressource assignée.");
            }
        }
        return true;
    }

    // Verifie que les colons ont une liste de préférence.
    public boolean verifierListePreference() {
        boolean valide = true;
        for (Colon colon : ListeColons) {
            List<String> preferences = colon.getListePreferences();
            if (preferences == null || preferences.isEmpty()) {
                System.out.println(RED_TEXT + "erreur : Colon " + colon.getNom()
                        + " : Liste de préférences vide." + RESET_TEXT);
                valide = false;
            }
        }
        return valide;
    }

    // Compte le nombre de colons jaloux par rapport à un colon donné.
    public int compterJalousiesPour(Colon cible) throws Exception {
        int nombreDeJalousies = 0;
        if (cible.getListePreferences().size() == 0) {
            throw new Exception("La liste des préférences est vide.");
        }
        for (Colon autreColon : ListeColons) {
            if (!autreColon.equals(cible)) {
                for (Relation relation : ListeRelation) {
                    Colon colon1 = relation.getC1();
                    Colon colon2 = relation.getC2();

                    if ((colon1.equals(cible) && colon2.equals(autreColon))
                            || (colon1.equals(autreColon) && colon2.equals(cible))) {
                        if (cible.prefereRessource(autreColon.getRessourceAttribuee())) {
                            nombreDeJalousies++;
                        }
                    }
                }
            }
        }
        return nombreDeJalousies;
    }

    // Affiche la liste des colons jaloux de chaque colons.
    public void affichelisteJaloux() {
        System.out.println("Nombre de jaloux pour chaque colon :");
        for (Colon c : ListeColons) {
            try {
                int nbrJaloux = compterJalousiesPour(c);
                System.out.println(c.getNom() + " : " + nbrJaloux);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Calcule le cout des affectations de la colonie.
    public void CalculCoutAffcetation() {
        int CoutAffectation = 0;
        for (Colon c : ListeColons) {
            try {
                int nbrJaloux = compterJalousiesPour(c);
                if (nbrJaloux != 0) {
                    CoutAffectation++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Le coût de cette affectation est : " + CoutAffectation);
    }

    // Affiche les relations de la colonie.
    public void afficheRelation() {
        int i = 1;
        for (Relation p : ListeRelation) {
            System.out.println(i + " : " + p.getRelation());
            i++;
        }
    }

    public List<Colon> getListeColons() {
        return ListeColons;
    }

    public List<Relation> getListeRelation() {
        return ListeRelation;
    }

    public int nombreRessources() {
        return this.RessourceList.size();
    }

    public int getJalousie() {
        int CoutAffectation = 0;
        for (Colon c : ListeColons) {
            try {
                int nbrJaloux = compterJalousiesPour(c);
                if (nbrJaloux != 0) {
                    CoutAffectation++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return CoutAffectation;
    }

    public void assignerRessource(Colon colon, String ressource) {
        colon.setRessourceAttribuee(ressource);
    }
}
