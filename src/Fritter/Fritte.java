/**
 * Nom ......... : Fritte.java
 * Rôle ........ : Classe Fritte représente la classe des messages publiées par les utilisateurs.
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */

package Fritter;

import java.util.Date;
import java.util.LinkedList;

public class Fritte {

    private String auteur;
    private Date date;
    private Date originalDate;

    public Date getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(Date originalDate) {
        this.originalDate = originalDate;
    }

    public enum Type {
        TEXTE, MULTIMEDIA, REFRITTAGE, COMMENTAIRE
    }

    private Type type;
    private String contenu;
    private int nbreLike;
    private int nbreRefrittage;
    private int nbreCommentaires;
    private int numFritte;

    private LinkedList<Commentaires> commentaire = new LinkedList<>();
    private LinkedList<Fritte> refrittage = new LinkedList<>();

    private String refrittageOriginalAuteur;
    private int refrittageOriginalNumFritte;

    public Fritte(String auteur, Date date, Type type, String contenu, int nbreLike, int nbreRefrittage,
                  int nbreCommentaires, int numFritte) throws SaisieIncorrecte {
        if (type == Type.TEXTE && contenu.length() > 140) {
            throw new SaisieIncorrecte("Le contenu d'une fritte ne peut être supérieur à 140 caractères.");
        }
        this.auteur = auteur;
        this.date = date;
        this.originalDate = type == Type.REFRITTAGE ? null : date; // Set original date only for original frittes
        this.type = type;
        this.contenu = contenu;
        this.nbreLike = nbreLike;
        this.nbreRefrittage = nbreRefrittage;
        this.nbreCommentaires = nbreCommentaires;
        this.numFritte = numFritte;
    }

    public String getAuteur() {
        return auteur;
    }
    public Date getDate() {
        return date;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getContenu() {
        return contenu;
    }
    
    public int getNbreLike() {
        return nbreLike;
    }
    
    public int getNbreRefrittage() {
        return nbreRefrittage;
    }
    
    public int getNbreCommentaires() {
        return nbreCommentaires;
    }
    
    public int getNumFritte() {
        return numFritte;
    }
    
    public LinkedList<Commentaires> getCommentaire() {
        return commentaire;
    }
    
    public LinkedList<Fritte> getRefrittage() {
        return refrittage;
    }
    
    public String getRefrittageOriginalAuteur() {
        return refrittageOriginalAuteur;
    }
    
    public int getRefrittageOriginalNumFritte() {
        return refrittageOriginalNumFritte;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setNbreLike(int nbreLike) {
        this.nbreLike = nbreLike;
    }

    public void setNbreRefrittage(int nbreRefrittage) {
        this.nbreRefrittage = nbreRefrittage;
    }

    public void setNbreCommentaires(int nbreCommentaires) {
        this.nbreCommentaires = nbreCommentaires;
    }

    public void setNumFritte(int numFritte) {
        this.numFritte = numFritte;
    }

    public void setCommentaire(LinkedList<Commentaires> commentaire) {
        this.commentaire = commentaire;
    }

    public void addCommentaire(Commentaires comment) {
        this.commentaire.add(comment);
        this.nbreCommentaires++;
    }

    public void setRefrittage(LinkedList<Fritte> refrittage) {
        this.refrittage = refrittage;
    }

    public void setRefrittageOriginalAuteur(String refrittageOriginalAuteur) {
        this.refrittageOriginalAuteur = refrittageOriginalAuteur;
    }

    public void setRefrittageOriginalNumFritte(int refrittageOriginalNumFritte) {
        this.refrittageOriginalNumFritte = refrittageOriginalNumFritte;
    }
}

