/**
 * Nom ......... : TestUnitaire.java
 * Rôle ........ : Sous-classe Commentaires.java 
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */


package Fritter;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Commentaires extends Fritte {
    
    // Liste pour gérer les commentaires
    private List<Commentaires> comListe = new ArrayList<>();

    public Commentaires(String auteur, Date date, String contenu, int numFritte) throws SaisieIncorrecte {
        // Appel du constructeur parent (Fritte)
        super(
            auteur,                 // récupération du pseudo l'utlisateur appelant
            date,                   // définition de la date
            Type.TEXTE,             // Type TEXTE pour les commentaires
            contenu,                // récupération du contenu
            0,                      // Initialisation des likes à 0
            0,                      // Initialize refrittages à 0
            0,                      // Initialisation des commentaires à 0
            numFritte               // méthode permettant de générer un numéro de Fritte
        );
    }

    // Ajouter liste de commentaires
    public void addComListe(Commentaires comment) {
        comListe.add(comment);
    }

    // Récupérer la liste des commentaires
    public List<Commentaires> getComListe() {
        return comListe;
    }

    // Récupération des commentaires par nombre de like
    public List<Commentaires> getComParLike() {
        List<Commentaires> sorted = new ArrayList<>(comListe);
        sorted.sort((c1, c2) -> Integer.compare(c2.getNbreLike(), c1.getNbreLike()));
        return sorted;
    }

    @Override
    public String toString() {
        return "Commentaire by " + getAuteur() + ": " + getContenu() + " (" + getNbreLike() + " likes)";
    }
}


