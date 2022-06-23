 # Android Architecture Component librairies

## Table des matières

1. [Introduction](#introduction)
2. [MVVM](#mvvm-model-view-viewmodel)
    1. [Schéma officiel d'Android](#schéma-officiel-dandroid)
    2. [View: Activity/Fragment](#view-activityfragment)
    3. [ViewModel](#viewmodel)
    4. [Repository](#repository)
    5. [Model](#model)
3. [Définitions](#définitions)
4. [Sources](#sources)

## Introduction

L'architecture d'Android fonctionne par des `fondamental components`. Ils font parti intégrante de l'application et ils en existent plusieurs tel que Data Biding, Navigation, Paging, ViewModel etc..

Dans les faits, le développeur a le choix quant au `design pattern` qu'il souhaite utiliser en guise d'architecture et par extension, le`framework` interne qu'il souhaite intégrer. 

Cependant, Android préconise l'architecture `MVVM`. C'est cette dernière qui sera adopté et présenté dans ce document.

## MVVM: Model View ViewModel

Il s'agit d'un `design pattern` qui a pour but de séparer une application en 3 couches distinctes.
||Description|
|---|---|
|*model*|Données liés à la logique métier issues d'une DB ou d'une API|
|*view*|Interface graphique faisant le lien entre les actions de l'utilisateur et le `ViewModel`.|
|*ViewModel*|Traduisible en modèle de vue, il organise les modèles métiers pour pouvoir transmettre les données à afficher à la vue. 


### Schéma officiel d'Android  
![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

### View: Activity/Fragment

#### Activity
Une `Activity` est un `component` qui donne à l'interface utilisateur un lieu où l'utilisateur peut interragir avec l'application. 

#### Fragments 
Il s'agit d'une portion réutilisable de notre application. On y définit et gère le layout et le cycle de vie en fonction des différentes evénements d'entrées (`input events`).
Un `fragment` est une part de l'activité. 

Enfaite, un fragment est à l'intérieur d'une activité. Le fait d'avoir plusieurs fragments rend l'activité plus riche. Cela a pour effet d'améliorer l'expérience utilisateur. 

#### Principe
Les `activities` et `fragments` se connectent sur le `ViewModel`, ils récupèrent les données nécessaires et les affichent à l'écran. Ils rapportent également les interractions de l'utilisateur au `ViewModel`

### ViewModel
Il s'agit d'un component d'architecture fondamental qui est utilisé notamment pour préparer et contenir les données requises pour les interfaces utilisateurs. Ce component nous permet de ne pas directement mettre ces données à même les `fragments` et `activities`.

Il faut percevoir le `ViewModel` comme une `Gateway` entre les `activities`/`fragments` et le reste de l'application.

Dans le principe, les `fragments` et les `activities` se connectent au `ViewModel`.
Ils transmettent les interractions des utilisateurs au à ce dernier. 

Celui-ci retransmet aux couches inférieurs les demandes de lecture, de modification ou de suppression de données.  

De nouveau, l'avantage d'une telle structure c'est qu'elle empêche d'initier explicitement des requêtes SQL depuis les `activities` et `fragments`.

### Repository
Dans notre architecture, nous avons un intermédiaire entre le `Model` et le `ViewModel` il s'agit d'une classe appelée `Repository`.

Il faut vraiment la percevoir comme une classe supplémentaire et pas un élément de l'architecture comme le serait le `Model`.

Enfaite, la raison pour laquelle on a besoin de cet intermédiaire, se trouve dans le fait que les données peuvent provenir de notre base de donnée `SQLite` ou d'une source de données externe (tel q'un `Webservice` par exemple). 

Le `Repository` soulage le `ViewModel` qui n'a alors pas à se soucier de la provenance des données. Cela permet l'accès en simultanés à des ressources différentes. 

### Model

Le modèle ici comportera plusieurs éléments distincts:
- La base de donnée `SQLite`
- `Room persistance library`
- `Data Access Object`

#### Room persistance library
Room représente donc le `Model` dans notre architecture MVVM.

Il s'agit dans les faits d'une librairie qui vient répondre aux problèmes liés à l'utilisation du langage SQL. A savoir les erreurs de syntaxe, des point-virgules oubliés etc...

Dans l'absolu, il permet d'écrire moins de code. Des opérations complexes tels que les opérations CRUD, ou purement et simplement la création de table sont facilités.

Ce qui rend `Room` intéressant, c'est qu'il fournit une vérification des requêtes SQL à la compilation. 
L'on ne parle pas ici d'erreurs de syntaxes du code `SQL`. L'on parle d'erreurs tels que l'accès à une table qui n'existe pas. Cette opération provoquera ici une erreur à la compilation (`compile-time`) empêchant le code d'être compilé. 

Le bénéfice de cette approche se trouve notamment dans l'expérience utilisateur. En effet, elle évite un crash au `Runtime`. Ce qui signifie qu'elle évite un crash durant l'exécution même du programme.
Cela est  possible parce que `SQLite` est une base de donnée locale.

#### Data Access Object (DAO)

DAO pour `data access object` est un autre `design pattern`. Son rôle dans le modèle sera de permettre les opérations CRUD. 
C'est au `compile time` que room génère l'implémentation du DAO défini.

Enfaite, on utilisera DAO à la place de `query builders` ou de requêtes `SQL` explicite.

### Définitions

Afin de ne pas alourdir la lecture de ce texte, certains terme sont directement défini et expliqués dans cette section.

| Terme | Définition |
|---|---|
| *compile time* | Période durant laquelle les instructions ou le code source est converti en code machine. C'est à ce moment que le compilateur vérifie la syntaxe, la sémantique et le type. Les erreurs qui surviennent à ce moment de ce moment sont appelés `compile time error`, ce temps précèdent l'exécution du programme. |
| *runtime*|Période qui suit le cycle de vie d'un programme où celui-ci est en cours d'exécution. Les erreurs qui surviennent à ce moment là sont par exemple des divisions par zéro, des pointeurs `null` etc... |
|*entities*|Il s'agit de la représentation des entités de la base de données. Une table et ses champs sera traduit par une classe et ses attributs. |
|*webservice*|Il s'agit d'une application qui permet d'échanges des données avec d'autres applications web.|
|*Design Pattern*|Patron de conceptions, il s'agit de solution classiques à des problèmes récurrent de la conception de logiciels|

## Sources

- [Runtime, compile-time](https://www.baeldung.com/cs/runtime-vs-compile-time)
- [Fragments](https://developer.android.com/guide/fragments)
- [Activity](https://developer.android.com/reference/android/app/Activity)
- [Pattern MVVM](https://www.arkance-systems.fr/pattern-mvvm/)
- [Design Pattern](https://refactoring.guru/fr/design-patterns)
- [Activity and fragments](https://dev.to/codewithrish/difference-between-activity-and-fragment-f68)
- [DAO](https://developer.android.com/training/data-storage/room/accessing-data)
