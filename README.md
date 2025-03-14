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

# Installation

## Démarrer le backend (Spring Boot)

1. Clonez le repository du projet.

    ```bash
    git clone https://github.com/Remi13Git/Projet-3-OC
    ```

2. Importez le projet dans votre IDE préféré (par exemple, IntelliJ IDEA ou Eclipse) en tant que projet Maven.

3. Configurer le fichier `application.properties` pour la connexion à la base de données

Pour créer votre base de données MySQL, utilisez le schema présent dans `Frontend/resources/sql/script.sql`

Dans le fichier `src/main/resources/application.properties`, vous devez configurer la connexion à votre base de données MySQL en remplaçant l'url, l'username et le password par vos propres données. 

## Installer les dépendances

Dans le terminal de votre IDE ou dans un terminal, exécutez la commande suivante pour télécharger et installer toutes les dépendances nécessaires à l'exécution du projet :

```bash
cd Projet-3-OC
mvn clean install
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

## Démarrer le frontend (Angular)

Ouvrez une nouvelle fenêtre de terminal (ou un nouvel onglet dans votre IDE), puis exécutez les commandes suivantes :

```bash
cd Frontend
npm install
npm run start
```
Cela va installer les dépendances nécessaires puis démarrer votre application Angular. Le frontend sera accessible à l'adresse suivante :

```bash
http://localhost:4200
```

## Documentation de l'API

Une fois l'application démarrée, vous pouvez accéder à la documentation de l'API à l'adresse suivante :

👉 [Swagger UI - Documentation de l'API](http://localhost:3001/swagger-ui/index.html)

Cela vous permet de tester les différentes routes de l'API directement depuis votre navigateur.