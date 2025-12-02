# Space_Invaders_Game

## Description
Ce projet est une réimplémentation du célèbre jeu d'arcade *Space Invaders*, développée en Java avec JavaFX. L'objectif principal n'est pas seulement le jeu lui-même, mais la mise en œuvre stricte d'une architecture logicielle robuste basée sur les **Design Patterns** (GoF).

Le jeu propose un système de vagues infinies, des ennemis intelligents (Composite), des bonus défensifs (Decorator), et un système de score découplé (Observer).

## Membres du Groupe
* [Votre Nom Prénom]
* [Nom Prénom Binôme si applicable]

## Technologies Utilisées
* **Langage :** Java 17
* **Framework GUI :** JavaFX (OpenJFX 21)
* **Logging :** Log4j2 (Format strict [TIMESTAMP] [TYPE] Message)
* **Build & Gestion de dépendances :** Maven
* **Architecture :** MVC simplifié (GameEngine / States)

## Design Patterns Implémentés
Ce projet intègre 6 patrons de conception majeurs :

1.  **State Pattern** (`MenuState`, `PlayingState`, `GameOverState`) :
    * Gère les transitions fluides entre le menu, le jeu et l'écran de fin.
2.  **Factory Method** (`AlienFactory`) :
    * Centralise la création des différents types d'ennemis (Rouge, Vert) sans coupler le code logique.
3.  **Composite Pattern** (`AlienSquad`) :
    * Permet de manipuler une flotte entière ou une ligne d'ennemis comme une seule entité (déplacement synchronisé, détection de collision groupée).
4.  **Decorator Pattern** (`ShieldDecorator`) :
    * Permet d'ajouter dynamiquement un bouclier au vaisseau du joueur sans modifier la classe `SpaceShip`.
5.  **Singleton Pattern** (`GameEngine`, `ScoreManager`) :
    * Garantit une instance unique pour le moteur de jeu et le gestionnaire de score.
6.  **Observer Pattern** (`ScoreManager`, `PlayingState`) :
    * Notifie l'interface graphique des changements de score sans que la logique métier ne connaisse l'affichage.

## Installation et Exécution

### Prérequis
* JDK 17 ou supérieur
* Maven 3.6+

### Étapes
1.  Cloner le dépôt :
    ```bash
    git clone [URL_DE_VOTRE_DEPOT]
    ```
2.  Compiler et lancer le jeu via Maven (Recommandé) :
    ```bash
    mvn clean javafx:run
    ```

## Utilisation (Contrôles)
* **Flèches GAUCHE / DROITE** : Déplacer le vaisseau.
* **ESPACE** : Tirer un laser.
* **Touche B** : Activer le Bouclier (Démonstration du pattern Decorator).
* **ENTRÉE** : Démarrer le jeu ou Rejouer après un Game Over.

## Fonctionnalités du Jeu
* **Intelligence de Groupe :** Les ennemis descendent et font demi-tour lorsqu'un seul membre touche le bord (Composite).
* **Vagues Infinies :** La difficulté augmente (plus d'ennemis) à chaque niveau complété.
* **Système de Protection :** 4 Bunkers destructibles protègent le joueur.
* **Riposte Ennemie :** Les aliens tirent aléatoirement sur le joueur.
