 # Documentation technique

## Table des matières

1. [Introduction](#introduction)
2. [Convention de nommage](#convention-de-nommage)
3. [Structure du projet](#structure-du-projet)
4. [3 composants principaux](#3-composants-principaux)
    1. [@Entity](#entity)
    2. [@Dao](#dao)
    3. [@Database](#database)
        1. [Companion object](#companion-object)
5. [Repository](#repository)
6. [ModelView](#modelview)
7. [Points d'améliorations](#points-daméliorations)
8. [Ressources](#ressources)

## Introduction

Dans cette partie nous allons étudier l'application de cette architecture dans notre projet.

Le projet s'est limité aux opérations sur les clients et les chiens. IL s'agit d'une relation `one to many` où  un client peut avoir plusieurs chiens.

L'objectif était de créer les modèles pour les clients et chiens et d'insérer des données.

La version complète de l'application existe déjà sur le [CynoClientBase](https://github.com/cpnv-lvt/CynoClientBase). Il s'agit du même projet avec les mêmes tables et entités.

## Convention de nommage
|Element|Nom|
|---|---|
|Tables|Convention du CPNV: `entit`ies (au pluriel)|
|Classe DAO|`Entity`Dao|
|Classe entités|`Entity`|
|Classe repository|`EntityRepository`|

## Structure du projet

La structure se trouve dans `app/src/main/java/com/jav1/whatthedog/wtdClient`

Pour faire un parallèle avec le document d'architecture d'Android, voici notre model `MVVM` appliqué au projet:

| MVVM | Chemin |
|---|---|
|*Model*|`db/`|
|*ViewModel*|`model/`|
|*View*|`ui/`|


## 3 composants principaux

Dans cette section, nous allons brièvement évoquer les tâches plus spécifiques effectuées par certains `components`.

### @Entity
Ce composant se trouve dans `db/entities` et ses classes ont cette annotation `@entity`. 
Ils représentent les tables `clients` et `dogs` que l'on souhaite créer.
Il n'y a **aucune logique** dans ces fichiers, ils décrivent par des attributs les champs de ces tables.
Il contient `Client.kt`, `Dog.kt`.


### @Dao

Les `DAO` sont des classes `interface`.

Ce composant se trouve dans `db/dao`. Il contient les méthodes permettant l'accès à la base de donnée. 

Ses classes sont annotées `@dao` et le projet en l'état comporte `ClientDao.kt` et `DogDao.kt`.

Les query sont précédés des annotations `@Query`, `@Insert`, `@Update`, `@Delete` pour chaque opération CRUD associées.

> Remarque, c'est aussi ici que l'on définit quel comportement notre application doit adopter selon certaines situations. Typiquement dans le cas d'une insertion l'on peut choisir comment gérer un conflit via  `onConflict = OnConflictStrategy.IGNORE|REPLACE|ABORT` etc..

Pour faire le lien avec `@entity`, il a été nécessaire de donner en paramètre de ces méthodes notre `entitiy`.

### @Database
Il s'agit du point d'accès principal d'accès aux données de notre application.
Ce component est annoté `@Database` et il s'agit d'une classe `abstract`. Ce component se trouve dans `db/AppDatabase.kt`.
`AppDatabase` est une extension de `RoomDatabase`.

Dans cette classe:
- On explicite les `entities` utilisées.
- On permet l'accès aux `dao`s.
- L'on met en place un `singleton` pour l'accès à la base de données

#### Companion object
Le `singleton` est définit à l'intérieur de cette classe. 
Le méchanisme est le suivant: 
On définit en `volatile` l'instance de notre base de données. Cela a pour effet de le rendre visible aux autres `threads`.
On ajoute ensuite une méthode `getDatabase()` où l'on délimite un bloc protégé des accès concurentiels par d'autres `threads`.

Ce méchanisme rend l'instance de l'objet représentant notre base de donnée **unique** .
La raison pour laquelle l'on implémente cela est pour réduire les coûts de performances car plusieurs accès peuvent rapidement être onéreux.

## Repository

Dans le projet WhatTheDog, le repository se trouve dans  `db/model`, il contient pour chaque entité, son fichier repository respectif.
Cet intermédiaire est celui qui détient l'accès à plusieurs sources de données.

Il n'est pas obligatoire, il s'agit néanmoins d'une bonne pratique pour les raisons énoncés dans  [Partie 1: Architecture Android](architecture_android_theorie.md).

## ModelView
Les `modelsViews` se trouvent dans `db/ui/client`. Ils se présentent sous la forme d'extension de `AndroidViewModel`.

Initialement le but ici était de crée une page de détails de client et de pouvoir créer un client c'est ce qui a été préparé dans ce `modelView`.  

> `AndroidViewModel` est néanmoins différent de la classe `ViewModel` car il contient une référence sur `Application` de Android app.

## Points d'améliorations

J'ai pris la direction d'effectuer une documentation sur le projet. 
Ici sont détaillés les points qui manquent à cette implémentation pour être complète ou simplement pour être améliorée.

- Définir des constantes pour les versions des dépendances. 
- Utiliser le [guide de migration](https://goo.gle/kotlin-android-extensions-deprecation) car le plugin `kotlin-android-extensions` est déprécié.
- Fixer les bugs liés à la compilation.
- Terminer l'implémentation de la base de donnée
- Créer la relation entre chien et client
- Implémenter la totalité des entités conformément à la base de donnée transmises.


## Ressources

### Tutoriels (YouTube)
- [Coding in Flow](https://www.youtube.com/watch?v=ARpn-1FPNE4&list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118&index=1)
- [Stevdza-San](https://www.youtube.com/watch?v=lwAvI3WDXBY&t=203s)

## Ressources Code
- [CynoClientBase](https://github.com/cpnv-lvt/CynoClientBase)