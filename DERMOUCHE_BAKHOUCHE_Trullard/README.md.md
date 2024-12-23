# Projet Programmation Avancée - Université Paris Cité

## Partage de biens dans une colonie spatiale

### Description du projet
Ce projet consiste à résoudre un problème d'affectation optimale de ressources dans une colonie spatiale. Les fonctionnalités principales incluent :
- La création d'une colonie composée de colons.
- L'attribution de relations de jalousie entre les colons.
- L'affectation de ressources aux colons.
- La détermination d'une combinaison optimale minimisant la jalousie à l'aide d'algorithmes avancés décrit dans la partie Sources

### Structure du projet

#### Arborescence
```
PooColonie/
├── .idea/
│   ├── misc.xml
│   └── other IntelliJ config files...
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── monprojet/
│   │   │           ├── Application.java
│   │   │           ├── Colon.java
│   │   │           ├── Colonie.java
│   │   │           ├── ConfigColonie.java
│   │   │           ├── MenuColonie1.java
│   │   │           ├── MenuColonie2.java
│   │   │           ├── MenuCreation.java
│   │   │           ├── MenuGestion.java
│   │   │           ├── Relation.java
│   │   │           └── Solver.java
│   │   └── resources/
│   │       ├── config_files/
│   │       │   ├── example_config.txt
│   │       │   └── other_config_files
│   │       └── other resources...
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── monprojet/
│   │   │           ├── ApplicationTest.java
│   │   │           ├── ColonTest.java
│   │   │           ├── ColonieTest.java
│   │   │           ├── ConfigColonieTest.java
│   │   │           ├── MenuColonie1Test.java
│   │   │           ├── MenuColonie2Test.java
│   │   │           ├── MenuCreationTest.java
│   │   │           ├── MenuGestionTest.java
│   │   │           ├── RelationTest.java
│   │   │           └── SolverTest.java
│   │   └── resources/
│   │       ├── test_files/
│   │       │   ├── test_config.txt
│   │       │   └── other_test_files (comme colonie1,colonie2,...)
│   │       └── solutions/    //Pour sauvegarder les solutions de l'algo dans un fichier nommé par l'utilisateur 
├── target/
│   ├── classes/
│   ├── generated-sources/
│   ├── generated-test-sources/
│   └── test-classes/
├── README.md
├── pom.xml
```

### Instructions de compilation et d'exécution

#### Prérequis
- **Java** : Version 11 ou supérieure.
- **Maven** : Pour gérer les dépendances et la compilation.
- **IntelliJ IDEA** : Environnement de développement recommandé.

#### Compilation et exécution

##### 1. Avec Maven
Depuis le terminal, placez-vous dans le répertoire racine du projet et utilisez les commandes suivantes :
- **Pour compiler le projet** :
  ```bash
  mvn compile
  ```
- **Pour exécuter les tests** :
  ```bash
  mvn test
  ```
- **Pour exécuter l'application avec un fichier de configuration** :
  ```bash
  mvn exec:java -Dexec.mainClass="com.monprojet.Application" -Dexec.args="colonie2"
  ```
  *Remplacez `colonie2` par le nom de votre fichier de configuration situé dans `src/main/resources/test_file`.*

- **Pour exécuter l'application sans argument** (mode manuel) :
  ```bash
  mvn exec:java -Dexec.mainClass="com.monprojet.Application"
  ```

##### 2. Avec IntelliJ IDEA
- **Importer le projet** : Ouvrez le projet via `pom.xml`.
- **Compiler** : Cliquez sur `Build > Build Project`.
- **Exécuter avec un fichier de configuration** :
  1. Créez une nouvelle configuration dans IntelliJ.
  2. Ajoutez `colonie2` comme argument de ligne de commande.
- **Exécuter sans argument** : Lancez `com.monprojet.Application` directement.

##### 3. Avec la ligne de commande
Après avoir compilé avec Maven (`mvn package`), les classes exécutables se trouvent dans `target/classes`.

- **Exécution avec un fichier de configuration** :
  ```bash
  java -cp target/classes com.monprojet.Application colonie2
  ```
- **Exécution sans argument** :
  ```bash
  java -cp target/classes com.monprojet.Application
  ```

### Sources
- [Recuit simulé sur Wikipédia](https://fr.wikipedia.org/wiki/Recuit_simul%C3%A9)
- [Tutoriel sur le recuit simulé](https://rfia2012.wordpress.com/wp-content/uploads/2011/12/amine_le_recuit_simulc3a9.pdf)

### Auteurs
- **Mohammed Ryad DERMOUCHE**
- **Imene BAKHOUCHE**
- **Martin Trullard**

---
