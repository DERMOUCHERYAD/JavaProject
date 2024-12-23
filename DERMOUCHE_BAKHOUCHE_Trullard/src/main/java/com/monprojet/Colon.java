package com.monprojet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Représente un colon dans une colonie. Chaque colon a un nom, une liste
 * de préférences pour des ressources, et une ressource attribuée.
 */
class Colon {
    private String nom;
    private List<String> ListePreferences;
    private String RessourceAttribuee;
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("[a-zA-Z0-9]+");

    public Colon(String nom) {
        this.nom = nom;
        this.ListePreferences = new ArrayList<>();
        this.RessourceAttribuee = null;
    }

    /**
     * Construit une copie d'un autre colon.
     *
     * @param autre Le colon à copier.
     */
    public Colon(Colon autre) {
        this.nom = autre.nom;
        this.ListePreferences = new ArrayList<>(autre.ListePreferences);
        this.RessourceAttribuee = autre.RessourceAttribuee;
    }


    /**
     * Retourne une représentation textuelle du colon, incluant son nom,
     * ses préférences et la ressource attribuée.
     *
     * @return Une chaîne représentant le colon.
     */
    @Override
    public String toString() {
        return "Colon [nom = " + nom + ", ListePreferences = " + ListePreferences + ", RessourceAttribuee = "
                + RessourceAttribuee + "]";
    }

    /**
     * Ajoute une liste de préférences à ce colon si aucune n'est encore définie.
     *
     * @param ressourcesDisponibles Les ressources disponibles à définir comme préférences.
     * @return {@code true} si les préférences ont été ajoutées, {@code false} sinon.
     */
    public boolean ajoutPreferences(List<String> ressourcesDisponibles) {
        if (!ListePreferences.isEmpty()) {
            return false;
        }
        this.ListePreferences = ressourcesDisponibles;
        return true;
    }

    /**
     * Retourne le nom du colon.
     *
     * @return Le nom du colon.
     */

    public String getNom() {
        return nom;
    }


    /**
     * Retourne la liste des préférences du colon.
     *
     * @return La liste des préférences.
     */
    public List<String> getListePreferences() {
        return ListePreferences;
    }


    /**
     * Définit la liste des préférences pour le colon.
     *
     * @param listePreferences La liste des ressources préférées.
     * @throws IllegalArgumentException Si la liste est nulle ou si une ressource n'est pas alphanumérique.
     */
    public void setListePreferences(List<String> listePreferences) {
        if (listePreferences == null) {
            throw new IllegalArgumentException("La liste des préférences ne peut pas être nulle.");
        }
        for (String ressource : listePreferences) {
            if (!ALPHANUMERIC_PATTERN.matcher(ressource).matches()) {
                throw new IllegalArgumentException("Toutes les ressources doivent être alphanumériques.");
            }
        }
        this.ListePreferences = listePreferences;
    }

    /**
     * Retourne la ressource attribuée au colon.
     *
     * @return La ressource attribuée.
     */
    public String getRessourceAttribuee() {
        return RessourceAttribuee;
    }


    /**
     * Définit la ressource attribuée au colon.
     *
     * @param ressourceAttribuee La ressource attribuée.
     * @throws IllegalArgumentException Si la ressource est nulle.
     */
    public void setRessourceAttribuee(String ressourceAttribuee) {
        if (ressourceAttribuee == null) {
            throw new IllegalArgumentException("La ressource attribuée doit être alphanumérique.");
        }
        this.RessourceAttribuee = ressourceAttribuee;
    }

    /**
     * Vérifie si une ressource est préférée à la ressource actuellement attribuée.
     *
     * @param ressource La ressource à comparer.
     * @return {@code true} si la ressource est préférée, {@code false} sinon.
     */
    public boolean prefereRessource(String ressource) {
        if (!ListePreferences.contains(ressource) || !ListePreferences.contains(RessourceAttribuee)) {
            return false;
        }
        return ListePreferences.indexOf(ressource) < ListePreferences.indexOf(RessourceAttribuee);
    }

    public boolean prefereRessource(String r1, String r2) {
        return this.ListePreferences.indexOf(r1) < this.ListePreferences.indexOf(r2);
    }
}
