# Projet-3-OC - ChâTop

## Description

Ce projet est une application de gestion de locations immobilières, permettant aux utilisateurs d'ajouter, de consulter et de gérer des informations liées à des locations, y compris des messages entre propriétaires et locataires.

## Fonctionnalités principales

- **Page de Register - /register** :  
  Permet à l'utilisateur de créer un compte. 
  
- **Page de Login - /login** :  
  Permet à l'utilisateur de se connecter à son compte. 

- **Page d'informations /me** :  
  Permet à un utilisateur authentifié de consulter ses propres informations personnelles, comme son email et d'autres détails du profil.

- **Page d'Accueil - /rentals** :  
  Affiche une liste de toutes les locations disponibles. 

- **Page de création - /rentals/create** :  
  Permet à un utilisateur authentifié de créer une nouvelle location en fournissant des informations telles que le nom, la surface, le prix, la description, et l'image associée. 

- **Page de modification - /rentals/update** :  
  Permet à un utilisateur authentifié de mettre à jour les informations d'une location existante. 


## Prérequis

- [Java 17 ou supérieur](https://openjdk.java.net/)
- [Maven](https://maven.apache.org/)
- [MySQL 8.0 ou supérieur](https://dev.mysql.com/downloads/installer/)
- [Node.js et npm](https://nodejs.org/en/download/)
- [Angular CLI](https://angular.io/cli)

## Installation

1. Clonez le repository du projet.

    ```bash
    git clone https://github.com/Remi13Git/Projet-3-OC
    ```

2. Importez le projet dans votre IDE préféré (par exemple, IntelliJ IDEA ou Eclipse) en tant que projet Maven.

3. Configurer le fichier `application.properties` pour la connexion à la base de données

Dans le fichier `src/main/resources/application.properties`, vous devez configurer la connexion à votre base de données MySQL. 

## Installer les dépendances

Dans le terminal de votre IDE ou dans un terminal, exécutez la commande suivante pour télécharger et installer toutes les dépendances nécessaires à l'exécution du projet :

```bash
cd Projet-3-OC
```

## Lancer l'application

Une fois les dépendances installées, vous pouvez démarrer l'application avec la commande suivante :

```bash
cd SpringSecurityAuth
mvn spring-boot:run
```

Cela va démarrer votre application Spring Boot, et le backend sera accessible à l'adresse suivante :

```bash
http://localhost:3001
```