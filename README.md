Course de voiture
=============================================

## Introduction

Le projet consiste à développer une application pouvant jouer des courses de voiture en utilisant des algorithmes de calculs de trajectoire.

![Capture d'écran (doc/screen1.png)](https://github.com/sandavid/racing-line/raw/master/doc/screen1.png)

### Cahier des charges

#### Lecture et simulation d'un circuit

La solution doit pouvoir lire tout circuit fourni au format .trk et jouer une voiture sur ce circuit.

#### Sauvegardes des commandes

La solution doit pouvoir sauvegarder les commandes fournies par la stratégie et appliquées à la voiture.

#### Affichage graphique simple

La solution doit afficher le résultat d'une course sous une représentation graphique au format .PNG ou dans une fenêtre en temps-réel.

#### Langage

La solution doit être développée en Java.

#### Architecture modulaire

La solution doit être suffisamment modulaire pour pouvoir ajouter d'autres fonctionnalités au cours du semestre.

#### Modèle-vue-contrôleur et interface homme-machine

La solution doit suivre le patron de conception modèle-vue-contrôleur pour l'implémentation de l'interface homme-machine.

#### Interface homme-machine avancée

La solution doit pouvoir permettre la configuration des paramètres de la simulation, notamment le choix du circuit à jouer, des stratégies à employer, des vues à afficher. Elle doit aussi permettre le lancement, la mise en pause, la reprise et l'arrêt de la course.

![Capture d'écran (doc/screen2.png)](https://github.com/sandavid/racing-line/raw/master/doc/screen2.png)




## Exécution
### Compilation des fichiers Java
```
ant
```

### Programme principal

```
java -classpath bin li260.mains.Main
```

### Programme de traitements par lots
```
java -classpath bin li260.mains.BatchJeu track/1_safe.trk
```

### Convertisseurs PNG vers TRK, et TRK vers PNG
```
java -classpath bin li260.mains.ConvertToTrk track/1_safe.png
java -classpath bin li260.mains.ConvertToPng track/1_safe.trk
```

### Programme du partiel 2011
```
java -classpath bin li260.mains.MainPartiel2011
```

### Programme LI260 Maps
```
java -classpath bin li260.mains.Maps
```


![Capture d'écran (doc/screen3.png)](https://github.com/sandavid/racing-line/raw/master/doc/screen3.png)

![Capture d'écran (doc/screen4.png)](https://github.com/sandavid/racing-line/raw/master/doc/screen4.png)


## Licence

(Licence MIT)

Copyright © 2011-2012 David San

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

