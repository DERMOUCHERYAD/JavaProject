package com.monprojet;

import com.monprojet.Colon;
import com.monprojet.Colonie;
import com.monprojet.Relation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;





public class ConfigColonie {
    private String FichierConfig;
    private Colonie colonieConfig;
    final String RED_TEXT = "\033[31m";
    final String RESET_TEXT = "\033[0m";

    public ConfigColonie(String filePath) {
        this.FichierConfig = filePath;
    }

    public Colonie LireFichierConfig() throws IOException {
        File fichier = new File(this.FichierConfig);
        if (!fichier.exists()) {
            throw new IOException("Erreur : Le fichier " + this.FichierConfig + " n'existe pas dans le dossier.");
        }
        List<String> lignes = Files.readAllLines(fichier.toPath());
        Colonie colonie = new Colonie();
        Set<String> colonsSet = new HashSet<>();
        Set<String> ressourcesSet = new HashSet<>();
        int ligneIndex = 0;
        boolean sectionColons = true, sectionRessources = false, sectionRelations = false, sectionPreferences = false;
        try {
            for (String ligne : lignes) {
                ligneIndex++;
                ligne = ligne.trim();
                if (!ligne.endsWith(").")) {
                    throw new IllegalArgumentException(
                            "Erreur à la ligne " + ligneIndex + ": La ligne doit se terminer par un point '.'. Ligne invalide : " + ligne
                    );
                }
                if (ligne.startsWith("colon(")) {
                    if (!sectionColons) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + ": La section 'colon' doit apparaître avant les autres sections."
                        );
                    }
                    String nom = extraireNom(ligne, "colon(", ligneIndex);
                    if (colonsSet.contains(nom)) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + ": Le colon " + nom + " est déjà défini."
                        );
                    }
                    colonsSet.add(nom);
                    colonie.ajoutColon(new Colon(nom));
                } else if (ligne.startsWith("ressource(")) {
                    sectionColons = false;
                    sectionRessources = true;
                    String nom = extraireNomString(ligne, "ressource(", ligneIndex);
                    if (ressourcesSet.contains(nom)) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + ": La ressource " + nom + " est déjà définie."
                        );
                    }
                    ressourcesSet.add(nom);
                    colonie.ajoutRessources(nom);
                } else if (ligne.startsWith("deteste(")) {
                    sectionRessources = false;
                    sectionRelations = true;
                    if (colonsSet.size() != ressourcesSet.size()) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex
                                        + ": Le nombre de colons (" + colonsSet.size()
                                        + ") doit être égal au nombre de ressources (" + ressourcesSet.size() + ")."
                        );
                    }
                    String[] noms = extraireDeuxNoms(ligne, "deteste(", ligneIndex);
                    if (!colonsSet.contains(noms[0])) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + ": Le colon '" + noms[0] + "' n'existe pas."
                        );
                    }
                    if (!colonsSet.contains(noms[1])) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + ": Le colon '" + noms[1] + "' n'existe pas."
                        );
                    }
                    Colon c1 = colonie.getColon(noms[0]);
                    Colon c2 = colonie.getColon(noms[1]);
                    try {
                        colonie.ajoutListeRelation(new Relation(c1, c2));
                    } catch (Exception e) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + e.getMessage()
                        );
                    }
                } else if (ligne.startsWith("preferences(")) {
                    sectionRelations = false;
                    sectionPreferences = true;
                    String[] preferences = extrairePreferences(ligne, ligneIndex);
                    String nomColon = preferences[0];
                    if (!colonsSet.contains(nomColon)) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex + ": Le colon " + nomColon + " n'a pas été défini."
                        );
                    }
                    if (preferences.length != colonie.nombreRessources() + 1) {
                        throw new IllegalArgumentException(
                                "Erreur à la ligne " + ligneIndex
                                        + ": Le colon doit avoir exactement "
                                        + colonie.nombreRessources() + " préférences."
                        );
                    }
                    Set<String> ressourcesValidees = new HashSet<>();
                    for (int i = 1; i < preferences.length; i++) {
                        String ressource = preferences[i];
                        if (!ressourcesSet.contains(ressource)) {
                            throw new IllegalArgumentException(
                                    "Erreur à la ligne " + ligneIndex + ": La ressource " + ressource + " n'a pas été définie."
                            );
                        }
                        if (!ressourcesValidees.add(ressource)) {
                            throw new IllegalArgumentException(
                                    "Erreur à la ligne " + ligneIndex
                                            + ": La ressource " + ressource + " est mentionnée plusieurs fois."
                            );
                        }
                    }
                    Colon colon = colonie.getColon(nomColon);
                    colon.ajoutPreferences(List.of(preferences).subList(1, preferences.length));
                } else {
                    throw new IllegalArgumentException(
                            "Erreur à la ligne " + ligneIndex + ": Section inconnue ou ligne invalide. Ligne : " + ligne
                    );
                }
            }
            if (colonsSet.size() != ressourcesSet.size()) {
                throw new IllegalArgumentException(
                        "Erreur : Le nombre de colons (" + colonsSet.size()
                                + ") doit être égal au nombre de ressources (" + ressourcesSet.size() + ")."
                );
            }
            System.out.println("Affectation colonie réussie.");
            this.colonieConfig = colonie;
            return colonie;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    private static String extraireNom(String ligne, String type, int ligneIndex) {
        String contenu = ligne.substring(type.length(), ligne.length() - 2).trim();
        if (!contenu.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException(
                    "Erreur à la ligne " + ligneIndex + ": Le nom '" + contenu + "' est invalide. Il doit être constitué uniquement de caractères alphanumériques."
            );
        }
        return contenu;
    }

    private static String extraireNomString(String ligne, String type, int ligneIndex) {
        return ligne.substring(type.length(), ligne.length() - 2).trim();
    }

    private static String[] extraireDeuxNoms(String ligne, String type, int ligneIndex) {
        String contenu = ligne.substring(type.length(), ligne.length() - 2).trim();
        String[] parts = contenu.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Erreur à la ligne " + ligneIndex + ": La relation doit contenir exactement deux noms."
            );
        }
        parts[0] = parts[0].trim();
        parts[1] = parts[1].trim();
        return parts;
    }

    private static String[] extrairePreferences(String ligne, int ligneIndex) {
        String contenu = ligne.substring("preferences(".length(), ligne.length() - 2).trim();
        String[] tokens = contenu.split(",");
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        return tokens;
    }

    public void sauvegarderSolution(String nomFichier) throws IOException {
        String cheminComplet = "src/test/resources/solutions/" + nomFichier;
        if (cheminComplet.equals(this.FichierConfig)) {
            throw new IOException("Le fichier de sauvegarde doit être différent de celui de configuration (" + FichierConfig + ").");
        }


        File fichier = new File(cheminComplet);
        if (!fichier.exists()) {
            System.out.println("Le fichier " + cheminComplet + " n'existe pas. ");
            System.out.println("Création en cours...");
            if (!fichier.createNewFile()) {
                throw new IOException("Impossible de créer le fichier : " + cheminComplet);
            }
        }

        if (!fichier.canWrite()) {
            throw new IOException("Le fichier n'est pas accessible en écriture : " + cheminComplet);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (Colon colon : this.colonieConfig.getListeColons()) {
                String ligne = colon.getNom() + ":" + colon.getRessourceAttribuee();
                writer.write(ligne);
                writer.newLine();
            }
        }

        System.out.println("Solution sauvegardée dans le fichier : " + cheminComplet);
    }
}