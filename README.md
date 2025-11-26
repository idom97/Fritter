# Fritter
Service de microblogging nommé Fritter (inspiré de Twitter/X) réalisé en langage Java dans le cadre d'un projet académique de la seconde année de licence informatique à distance de l’Université Paris 8, sous la direction du Professeur G.BESACIER.

## ⚙️ ETAPES DE LANCEMENT DU PROGRAMME SOUS LINUX

Dans le terminal, cherchez le répertoire Fritter avec la commande pwd:
```bash
$ pwd Fritter
```
## 🚀 LANCEMENT DU SHELL

Placez-vous dans le répertoire à l'aide de la commande cd, puis lancez le programme
à l'aide de javac:
```bash
$ javac -d bin -cp "lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" src/Fritter/*.java src/FritterTest/*.java
```

## 🛠️  EXIGENCES

La commande javac doit être installée:
```bash
$ sudo apt install openjdk-17-jdk-headless
$ javac -version
```
