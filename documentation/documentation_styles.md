# Styles 

Ce document présente les différents fichiers traitant la notion de styles, de polices et de couleurs.

###  Setting files

`main\res\values`

### Files and directories
Directory: `src\main\res\`

| Files |Description|
|---|---|
|`values\colors.xml`|Lieu où les couleurs qui sont utilisés dans toute l'application sont définies|
|`values\themes\themes.xml`|C'est ici que le thème `dark` ou `light` est défini.|
|`dimens.xml`|Ce fichier contient tout ce qui est notion de dimensions (taille de police, taille de bouton, taille des différents texte etc...).|
|`styles.xml`| Ce fichier contient la définition de tous les styles. Chaque texte, bouton, et label a un style (une dimension particulière, un couleur, une taille) et c'est ici q ue cela est défini. |


Si cela peut prendre un peu de temps à mettre en place, le bénéfice d'une telle organisation de répertoire se trouve dans la maintenabilité.
Si le design de l'application est amené à changer, alors il suffira de modifier ces fichiers pour appliquer ces styles dans tous les `fragments` ou `activities`.

## Sources

- [Thèmes](https://developer.android.com/guide/topics/ui/look-and-feel/themes)
- [Organizing your source files](https://guides.codepath.com/android/Organizing-your-Source-Files)
- [Style et thèmes](https://developer.android.com/guide/topics/ui/look-and-feel/themes)
- [Project folder structure](https://www.geeksforgeeks.org/android-project-folder-structure/)