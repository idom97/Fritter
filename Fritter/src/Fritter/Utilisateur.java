/**
 * Nom ......... : Utilisateur.java
 * Rôle ........ : Classe Utilisateur
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */
package Fritter;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class Utilisateur {
    private String nom;
    private String prenom;
    private List<String> pseudo;
    private String email;
    private String mdp;
    private Fritter fritter; // Référence pour atteindre la classe Fritter
    private Map<Utilisateur, Date> abonnements;
    
    /*
     * Constructeur
     */
    public Utilisateur(String nom, String prenom, String pseudo, String email, String mdp, Fritter fritter) {
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = new ArrayList<>(); 
        this.pseudo.add(pseudo); // Pseudo actuel ajouté à la liste des pseudos
        this.email = email;
        this.mdp = mdp;
        this.fritter = fritter;
        this.abonnements = new HashMap<>();
    }

    /*
     * Getters
     */
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
    /**Permet d'afficher le dernier pseudo d'un utilisateur
     * 
     * @return
     */
    public List<String> getAncienPseudo() {
        List<String> anciensPseudos = new ArrayList<>();

        // Si la liste contient 1 élément ou moins, il n'y a pas d'ancien pseudo
        if (pseudo.size() <= 1) {
            return anciensPseudos; // Retourne une liste vide
        }

        // Ajouter tous les éléments sauf le dernier à la liste `anciensPseudos`
        for (int i = 0; i < pseudo.size() - 1; i++) {
            anciensPseudos.add(pseudo.get(i));
        }

        return anciensPseudos;
    }
    

    /**
     * Permet d'afficher le pseudo actuel d'un utilisateur
     * @return
     */
    public String getPseudo() {
        if (pseudo.isEmpty()) {
            return null;
        }
        return pseudo.get(pseudo.size() - 1);
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

   /*
    * Permet de connaître l'ensemble des pseudos d'un utilisateur
    */
    public List<String> getPseudoHistorique() {
        return new ArrayList<>(pseudo);
    }
    
    public Fritter getFritter() {
		return fritter;
	}
    
    public Map<Utilisateur, Date> getAbonnements() {
		return abonnements;
	}
    
    
    /*
     * Setters
     */
	public void setFritter(Fritter fritter) {
		this.fritter = fritter;
	}


	public void setAbonnements(Map<Utilisateur, Date> abonnements) {
		this.abonnements = abonnements;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setPseudo(List<String> pseudo) {
		this.pseudo = pseudo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	
@Override
	public String toString() {
		return "Utilisateur [nom=" + nom + ", prenom=" + prenom + ", pseudo=" + pseudo + ", email=" + email + ", mdp="
				+ mdp + ", fritter=" + fritter + ", abonnements=" + abonnements + "]";
	}

	/*
 * Méthodes
 */
	/**
	 * Modifie le pseudo de l'utilisateur en s'assurant de son unicité sur la plateforme
	 * et permettant à l'utilisateur appelant de réutiliser un ancien pseudo 
	 * @param nouvPseudo
	 * @throws SaisieIncorrecte
	 */
public void editPseudo(String nouvPseudo) throws SaisieIncorrecte {
	// L'utilisateur doit être connecté
	if (!fritter.isFritosEnLigne(this)) {
		throw new SaisieIncorrecte("Usage: Vous devez être connecté pour modifier votre pseudo.");
	}
	
	// Les arguments ne peuvent être nuls      	
	try {
		// Vérifier que les paramètres sont valides
		fritter.verifArgs(nouvPseudo);
	} catch (SaisieIncorrecte e) {
		throw new SaisieIncorrecte("Usage : editPseudo <nouveau_nom_d'utilisateur> ");
	}
	
	// Ce pseudo doit être unique
	if (fritter.rechercheParPseudoActuel(nouvPseudo) != null) {
		throw new SaisieIncorrecte("Usage: Ce pseudo est actuellement utilisé par un autre utilisateur.");
	}
	
	// On s'assure que le pseudo est unique
	if (fritter.rechercheParPseudoAncien(nouvPseudo, this) != null) {
		throw new SaisieIncorrecte("Usage: Ce pseudo a déjà été utilisé par un autre utilisateur.");
	}
	
	// Le nouveau pseudo doit respecter une taille minimale
	if (nouvPseudo.length() < 6 || nouvPseudo.length() > 20) {
		throw new SaisieIncorrecte("Usage: Le pseudo doit contenir entre 6 et 20 caractères.");
	}
	
	if (!nouvPseudo.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*_]{6,}$")) {
		throw new SaisieIncorrecte("Usage: Le pseudo doit contenir au moins une lettre minuscule, "
		+ "une lettre majuscule, un chiffre, et un caractère spécial comme @ ou !. "
		+ "Les pseudos avec un underscore sont autorisés.");
	}
	
	// Si le nouveau pseudo est différent de l'actuel
	if (!nouvPseudo.equals(this.getPseudo())) {
		String ancienPseudo = this.getPseudo(); // Sauvegarder l'ancien pseudo
		this.pseudo.add(nouvPseudo); // Ajouter le nouveau pseudo
		
		// Mettre à jour le pseudo de l'utilisateur actuel
		fritter.majPseudo(this, nouvPseudo);
		
		// Afficher une confirmation
		System.out.println("Votre pseudo a été mis à jour avec succès. Nouveau pseudo: " + nouvPseudo);
		System.out.println("Ancien pseudo: " + ancienPseudo + " ajouté à l'historique.");
	}
}


	/**
	 * Modifie le mot de passe et impose un mot de passe fort
	 * @param nouvMdp
	 * @throws SaisieIncorrecte
	 */
	public void editMdp(String nouvMdp) throws SaisieIncorrecte {
		//L'utilisateur doit être connecté
		if (!fritter.isFritosEnLigne(this)) {
			throw new SaisieIncorrecte("Usage: Vous devez être connecté pour modifier votre mot de passe.");
		}
		
		//Les arguments ne peuvent être nuls      	
      	try {
  			// Vérifier que les paramètres sont valides
      		fritter.verifArgs(nouvMdp);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage : editMdp <nouveau_mot_de_passe> ");
  		}
      	
		if (!nouvMdp.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@!]).{6,}$"))
		 {
			throw new SaisieIncorrecte("Usage: Le mot de passe doit contenir au moins 6 caractères dont une lettre majuscule, "
					+ "un chiffre et un caractère spécial comme @ ou !.");
		}
		
		if (!nouvMdp.equals(this.mdp)) {
			this.mdp = nouvMdp;
			System.out.println("Votre mot de passe a été mis à jour avec succès.");
		}else {
			throw new SaisieIncorrecte("Usage :Le nouveau mot de passe doit être différent de l'ancien mot de passe.");
		}
	}
	
    /**Permet de suivre un autre utilisateur en réalisant différents contrôles
     * 
     * @param utilisateurCible
     * @throws SaisieIncorrecte
     */
    public void suivre(String utilisateurCible) throws SaisieIncorrecte {
    	//L'utilisateur doit être connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour suivre un autre utilisateur.");
        }

        //Les arguments ne peuvent être nuls      	
      	try {
  			// Vérifier que les paramètres sont valides
      		fritter.verifArgs(utilisateurCible);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage : suivre <nom_utilisateur> ");
  		}

        // Vérifier si la cible existe parmi les utilisateurs (actuel ou historique)
        Utilisateur cible = fritter.rechercheParPseudoActuel(utilisateurCible);
        if (cible == null) {
                cible = fritter.rechercheParPseudoAncien(utilisateurCible,this);
                if (cible != null) {
                	System.out.println("Le pseudo "+ utilisateurCible +" a déjà existé! Actuellement, "
                			+ "il s'agit de l'ancien pseudo de l'utilisateur " + cible.getPseudo());
                }
        }
         
        // Si la cible n'est pas trouvée, lever une exception
        if (cible == null) {
            throw new SaisieIncorrecte("Usage: Aucun utilisateur avec ce pseudo n'existe.");
        }

        // Vérifier si l'utilisateur tente de se suivre lui-même
        if (cible == this) {
            throw new SaisieIncorrecte("Usage: Vous ne pouvez pas vous suivre vous-même.");
        }

        // Vérifier si la cible est déjà suivie
        if (this.getAbonnements().containsKey(cible)) {
            throw new SaisieIncorrecte("Usage: Vous suivez déjà cet utilisateur.");
        }

        // Ajouter la cible à la liste des abonnements avec la date actuelle
        Date dateSuivi = new Date(); // Get the current date and time
        this.getAbonnements().put(cible, dateSuivi);
        System.out.println("Vous suivez maintenant " + cible.getPseudo() + " depuis le " + dateSuivi + " !");
    }
    
    /**Permet de stopper  un abonnement
     * 
     * @param utilisateurCible
     * @throws SaisieIncorrecte
     */
    public void stopAbonnement(String utilisateurCible) throws SaisieIncorrecte {
        // L'utilisateur appelant doit être connecté pour utiliser cette méthode
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour arrêter de suivre un autre utilisateur.");
        }

        // Vérifier que les paramètres sont valides
        try {
            fritter.verifArgs(utilisateurCible);
        } catch (SaisieIncorrecte e) {
            throw new SaisieIncorrecte("Usage : stopAbonnement <nom_utilisateur>");
        }

        // Vérifier si la cible existe parmi les utilisateurs (pseudo actuel ou historique)
        Utilisateur cible = fritter.rechercheParPseudoActuel(utilisateurCible);
        if (cible == null) {
            cible = fritter.rechercheParPseudoAncien(utilisateurCible, this);
            if (cible != null) {
                System.out.println("Le pseudo " + utilisateurCible + " a déjà existé! Actuellement, "
                    + "il s'agit de l'ancien pseudo de l'utilisateur " + cible.getPseudo());
            }
        }

        // Si la cible n'est pas trouvée, lever une exception
        if (cible == null) {
            throw new SaisieIncorrecte("Usage: Aucun utilisateur avec ce pseudo n'existe.");
        }

        // Vérifier si l'utilisateur tente de se désabonner de lui-même
        if (cible == this) {
            throw new SaisieIncorrecte("Usage: Vous ne pouvez pas stopper un abonnement sur vous-même...");
        }

        // Vérifier si la cible est suivie
        Date dateSuivi = this.getAbonnements().get(cible);
        if (dateSuivi == null) {
            throw new SaisieIncorrecte("Usage: Vous ne suivez pas "+ cible.getPseudo()+"...");
        }

        // Supprimer la cible de la liste des abonnements
        this.getAbonnements().remove(cible);

        // Afficher un message confirmant l'arrêt du suivi
        System.out.println("Vous ne suivez plus " + cible.getPseudo() + ", suivi commencé le " + dateSuivi + ".");
    }
    
    
    /**Permet de connaître le temps de suivi d''un abonnement
     * 
     * @param utilisateurSuivi
     * @throws SaisieIncorrecte
     */
    public void tmpsdeSuivi(String utilisateurSuivi) throws SaisieIncorrecte {
        // L'utilisateur appelant doit être connecté pour utiliser cette méthode
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour consulter la durée de suivi.");
        }

        // Vérifier que les paramètres sont valides
        try {
            fritter.verifArgs(utilisateurSuivi);
        } catch (SaisieIncorrecte e) {
            throw new SaisieIncorrecte("Usage : tmpsdeSuivi <nom_utilisateur>");
        }

        // Vérifier si la cible existe parmi les utilisateurs (pseudo actuel ou historique)
        Utilisateur cible = fritter.rechercheParPseudoActuel(utilisateurSuivi);
        if (cible == null) {
            cible = fritter.rechercheParPseudoAncien(utilisateurSuivi, this);
            if (cible != null) {
                System.out.println("Le pseudo " + utilisateurSuivi + " a déjà existé! Actuellement, "
                    + "il s'agit de l'ancien pseudo de l'utilisateur " + cible.getPseudo());
            }
        }

        // Si la cible n'est pas trouvée, lever une exception
        if (cible == null) {
            throw new SaisieIncorrecte("Usage: Aucun utilisateur avec ce pseudo n'existe.");
        }

        // Vérifier si la cible est suivie
        Date dateSuivi = this.getAbonnements().get(cible);
        if (dateSuivi == null) {
            throw new SaisieIncorrecte("Usage: Vous ne suivez pas " + cible.getPseudo() + "...");
        }

        
        Instant debut = dateSuivi.toInstant();
        Instant fin = Instant.now();
        
        // Calcul de la durée de suivi entre les deux instants
        Duration duree = Duration.between(debut, fin);

        long days = duree.toDays();
        long hours = duree.toHoursPart();
        long minutes = duree.toMinutesPart();
        long seconds = duree.toSecondsPart();

        System.out.println("Vous suivez " + cible.getPseudo() + " depuis " + days + " jours, " + 
            hours + " heures, " + minutes + " minutes, " + seconds + " secondes.");
    }

    /**Poster une fritte textuelle limitée à 140 caractères
     * 
     * @param contenu
     * @throws SaisieIncorrecte
     */
    public void textuelle(String contenu) throws SaisieIncorrecte {
    	//L'utilisateur doit être connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour poster un texte.");
        }

        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(contenu);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage : textuelle <votre_message> ");
  		}
        
        //Une fritte textuelle est un texte de moins de 140 caractères
        if (contenu.length() > 140) {
            throw new SaisieIncorrecte("Usage: Le texte ne peut pas dépasser 140 caractères.");
        }
        
        // Constructeur de la fritte
        Fritte nouvelleFritte = new Fritte(
                this.getPseudo(),  // Auteur de la fritte
                new Date(),        // Date actuelle
                Fritte.Type.TEXTE,//Type de la fritte textuelle
                contenu.trim(), //Contenu de la fritte textuelle
                0, 0, 0, //Initialisation à 0 du nombre de Commentaire, Like et Reffritage
                Fritter.genNumFritte() //Appel à une méthode permettant de générer un numéro de fritte
            );
        

        //Ajout de la fritte texte aux structures Originales et EnsembledesFrittes
        fritter.getOriginales().put(nouvelleFritte, Fritte.Type.TEXTE);
        fritter.getEnsembledesFrittes().put(nouvelleFritte, Fritte.Type.TEXTE);
        
        // Vérifications après ajout dans les structures de données originales et ensembledesFrittes
    	if (!fritter.getOriginales().containsKey(nouvelleFritte)) {
    		throw new SaisieIncorrecte("Usage: La fritte textuelle n'a pas été ajoutée correctement à la structure Originales.");
    	}
    	if (!fritter.getEnsembledesFrittes().containsKey(nouvelleFritte)) {
    		throw new SaisieIncorrecte("Usage: La fritte textuelle n'a pas été ajoutée correctement à la structure EnsembledesFrittes.");
    	}
    	
    	// Message de succès en sortie
    	System.out.println("Fritte textuelle publiée avec succès par " + this.getPseudo() + " à " + nouvelleFritte.getDate()+" Num:"+nouvelleFritte.getNumFritte());

        // Afficher fritte textuelle
        afficherFritte(nouvelleFritte);
    }
    
    /**Permet de poster une fritte multimédia après avoir réalisé différents contrôles
     * 
     * @param cheminVersImage
     * @throws SaisieIncorrecte
     * @throws IOException
     */
    public void multimedia(String cheminVersImage) throws SaisieIncorrecte, IOException {
        // Vérifier que l'utilisateur est connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour publier une fritte multimédia.");
        }

        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(cheminVersImage);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage: multimedia <chemin_vers_votre_image>");
  		}

        // Vérifier que le fichier existe
        File image = new File(cheminVersImage);
        if (!image.exists() || !image.isFile()) {
            throw new SaisieIncorrecte("Usage: Le fichier spécifié n'existe pas ou n'est pas valide.");
        }

        // Créer une nouvelle fritte multimédia avec Img2Ascii
        Fritte nouvelleFritte = new Img2Ascii(
            this,                // Utilisateur actuel comme auteur
            cheminVersImage.trim()     // Chemin vers l'image
        );

        // Ajouter la fritte à la liste des multimédias
        fritter.getMultimedia().put(nouvelleFritte, Fritte.Type.MULTIMEDIA);

        // Ajouter la fritte à la liste contenant l'ensemble des frittes
        fritter.getEnsembledesFrittes().put(nouvelleFritte, Fritte.Type.MULTIMEDIA);

        // Vérifications après ajout dans les structures de données originales et ensembledesFrittes
        if (!fritter.getMultimedia().containsKey(nouvelleFritte)) {
            throw new SaisieIncorrecte("Usage: La fritte multimédia n'a pas été ajoutée correctement.");
        }
        if (!fritter.getMultimedia().containsKey(nouvelleFritte)) {
            throw new SaisieIncorrecte("Usage: La fritte multimédia n'a pas été ajoutée correctement.");
        }
        
        // Message de succès en sortie
        System.out.println("Fritte multimédia publiée avec succès par " + this.getPseudo() + " à " + nouvelleFritte.getDate()+" Num:"+nouvelleFritte.getNumFritte());

        // Afficher fritte multimédia
        afficherFritte(nouvelleFritte);
        
    }
    /**Affiche une fritte
     * 
     * @param fritte
     * @throws SaisieIncorrecte
     */
    public void afficherFritte(Fritte fritte) throws SaisieIncorrecte {

        // Vérification si c'est un refrittage
        if (fritte.getType() == Fritte.Type.REFRITTAGE) {
            System.out.println(fritte.getAuteur() + " a refritté la fritte de: " + fritte.getRefrittageOriginalAuteur());
            System.out.println("Le :" + fritte.getDate()+" | N°: " + fritte.getNumFritte());
        } else {
            System.out.println(fritte.getAuteur() + " a posté le :" + fritte.getDate()+" | N°: " + fritte.getNumFritte());
        }
        System.out.println("------------------------------------------------------------");
        System.out.println(fritte.getContenu());
        System.out.println("------------------------------------------------------------");

        // Affecte le nombre de likes reçu par la fritte
        int likes;
        if (fritte.getType() == Fritte.Type.REFRITTAGE) {//Sauf si c'est un refrittage
            likes = 0;
        } else {
            likes = fritte.getNbreLike();
        }

        // Affecte le nombre de commentaires reçu par la fritte
        int commentaires;
        if (fritte.getType() == Fritte.Type.REFRITTAGE) {//Sauf si c'est un refrittage
            commentaires = 0;
        } else {
            commentaires = fritte.getCommentaire().size();
        }

        System.out.println("Likes: " + likes +
                "          | Refrittages: " + fritte.getNbreRefrittage() +
                "         | Commentaires: " + commentaires);
                
        System.out.println("------------------------------------------------------------");

        if (fritte.getType() != Fritte.Type.REFRITTAGE) {
            afficherCommentaires(fritte);
        }

        System.out.println("............................................................");
        System.out.println("");
        System.out.println("");
    }

    /**Affiche les commentaires d'une fritte quelque soit son type
     * 
     * @param fritte
     * @throws SaisieIncorrecte
     */
    private void afficherCommentaires(Fritte fritte) throws SaisieIncorrecte {
    	try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(fritte);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage : Aucun commentaire pour cette fritte ");
  		}
    	
    	// Liste chaînée des commentaires
        LinkedList<Commentaires> commentaires = fritte.getCommentaire();
        
        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(commentaires);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage : Cette fritte n'a pas encore reçue de commentaires ");
  		}
        
        System.out.println("Commentaires triés par likes:");

        // Trie en ordre décroissant des commentaires
        List<Commentaires> sortCom = new ArrayList<>(commentaires);
        sortCom.sort((c1, c2) -> Integer.compare(c2.getNbreLike(), c1.getNbreLike()));
        
        // Vérifier si aucun commentaire n'a de likes
        boolean aucunLike = sortCom.stream().allMatch(c -> c.getNbreLike() == 0);

        if (aucunLike) {
            // Si aucun commentaire n'a de likes, afficher tous les commentaires
            for (Commentaires commentaire : sortCom) {
                zoneCommentaire(commentaire);
            }
        } else {
            // Sinon, afficher seulement les 3 commentaires ayant le plus de likes
            for (int i = 0; i < Math.min(3, sortCom.size()); i++) {
                zoneCommentaire(sortCom.get(i));
            }
        }

    }

    /**Mise en forme des commentaires
     * 
     * @param commentaire
     */
    private void zoneCommentaire(Commentaires commentaire) {
    	
    	String indentation = "    ";
    	
        System.out.println(indentation + ".............................................................");
        System.out.println(indentation +  commentaire.getAuteur() + " a commenté le " + commentaire.getDate()+":"+" | N°:" + commentaire.getNumFritte());
        System.out.println(indentation + "-------------------------------------------------------------");
        System.out.println(indentation + commentaire.getContenu());
        System.out.println(indentation + "-------------------------------------------------------------");
        System.out.println(indentation + "Likes: " + commentaire.getNbreLike() +
                "          | Refrittages: " + commentaire.getNbreRefrittage() +
                "         | Commentaires: " + commentaire.getNbreCommentaires());
        System.out.println(indentation + "-------------------------------------------------------------");
        System.out.println(indentation + " ");
        System.out.println(indentation + " ");
    }
    
    /**Republier une fritte multimédia ou texte
     * 
     * @param auteur
     * @param numFritte
     * @throws SaisieIncorrecte
     */
    public void refrittage(String auteur, int numFritte) throws SaisieIncorrecte {
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour republier une fritte.");
        }
        
        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(auteur, numFritte);
  		} catch (SaisieIncorrecte e) {
  			// Lever une exception plus descriptive pour la commande `commentaire`
  			throw new SaisieIncorrecte("Usage : refrittage <auteur> <num_de_Fritte> ");
  		}

        // Rechercher la fritte cible associée à l'auteur
     	Fritte original = fritter.rechercherFritte(auteur, numFritte);

        Fritte refrittage = new Fritte(
            this.getPseudo(),  // Le pseudo de l'utilisateur appelant
            new Date(),        // La date actuelle
            Fritte.Type.REFRITTAGE, //Le type de la fritte
            original.getContenu(), //Le contenu de la fritte d'origine
            0, 0, 0, Fritter.genNumFritte() //Nbre de like, de refrittage, de commentaires, et numéro de fritte
        );
        
        //Lier le refrittage à la fritte d'origine
        refrittage.setRefrittageOriginalAuteur(original.getAuteur());
        refrittage.setRefrittageOriginalNumFritte(original.getNumFritte());
        refrittage.setOriginalDate(original.getOriginalDate());

        //Ajouter le refrittage à la map ensembleDesFrittes
        fritter.getEnsembledesFrittes().put(refrittage, Fritte.Type.REFRITTAGE);
        original.getRefrittage().add(refrittage);
        original.setNbreRefrittage(original.getNbreRefrittage() + 1);
        
        //Message de succès
        System.out.println("Refrittage ajouté avec succès par " + this.getPseudo() +
            " pour la fritte originale de " + original.getAuteur());
    }
    
    /**Commenter une fritte quelque soit son type
     * 
     * @param auteur
     * @param numFritte
     * @param contenu
     * @throws SaisieIncorrecte
     */
    public void commentaire(String auteur, int numFritte, String contenu) throws SaisieIncorrecte {
        // Vérifier que l'utilisateur appelant est connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage : Vous devez être connecté pour commenter.");
        }
        
        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(auteur, numFritte, contenu);
  		} catch (SaisieIncorrecte e) {
  			// Lever une exception plus descriptive pour la commande `commentaire`
  			throw new SaisieIncorrecte("Usage : commentaire <auteur> <num_de_Fritte> <contenu>");
  		}

        // Rechercher la fritte cible associée à l'auteur
        Fritte cible = fritter.rechercherFritte(auteur, numFritte);

        // Créer un nouvel objet Commentaires
        Commentaires nouvCom = new Commentaires(
            this.getPseudo(),              // Auteur du commentaire
            new Date(),                    // Date actuelle
            contenu,            // Contenu du commentaire
            Fritter.genNumFritte()         // Générer un ID unique pour le commentaire
        );

        // Ajouter le commentaire à la liste des commentaires de la fritte cible
        cible.addCommentaire(nouvCom);

        // Incrémenter le nombre de commentaires sur la fritte originale
        cible.setNbreCommentaires(cible.getNbreCommentaires() + 1);

        // Ajouter le commentaire à ensembledesFrittes
        fritter.getEnsembledesFrittes().put(nouvCom, Fritte.Type.COMMENTAIRE);

        // Afficher la fritte originale avec ses commentaires
        afficherFritte(cible);
    }


    /**Like, permet d'aimer une fritte
     * 
     * @param auteur
     * @param numFritte
     * @throws SaisieIncorrecte
     */
    
    public void like(String auteur, int numFritte) throws SaisieIncorrecte {
    	// Vérifier que l'utilisateur appelant est connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour aimer une fritte.");
        }
        
        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(auteur, numFritte);
  		} catch (SaisieIncorrecte e) {
  			// Lever une exception plus descriptive pour la commande `commentaire`
  			throw new SaisieIncorrecte("Usage : like <auteur> <num_de_Fritte> ");
  		}
        
  		// Rechercher la fritte cible associée à l'auteur
  		Fritte cible = fritter.rechercherFritte(auteur, numFritte);
  		
        // Incrementer le nombre de likes pour la fritte originale
        cible.setNbreLike(cible.getNbreLike() + 1);

        // Message de succès
        System.out.println("La fritte de " + auteur + " numéro " + numFritte + " a été aimée par " + this.getPseudo());
    }
   
    /**unlike, permet de retirer son like à une fritte
     * 
     * @param auteur
     * @param numFritte
     * @throws SaisieIncorrecte
     */
    public void unlike(String auteur, int numFritte) throws SaisieIncorrecte {
    	// Vérifier que l'utilisateur appelant est connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage: Vous devez être connecté pour retirer un like d'une fritte.");
        }

        try {
  			// Vérifier que les paramètres sont valides
  			fritter.verifArgs(auteur, numFritte);
  		} catch (SaisieIncorrecte e) {
  			// Lever une exception plus descriptive pour la commande `commentaire`
  			throw new SaisieIncorrecte("Usage : unlike <auteur> <num_de_Fritte> ");
  		}

        // Rechercher la fritte cible associée à l'auteur
    	Fritte cible = fritter.rechercherFritte(auteur, numFritte);

        // Décrémenter le nombre de likes pour la fritte originale si <0
        if (cible.getNbreLike() > 0) {
            cible.setNbreLike(cible.getNbreLike() - 1);
        } else {
            throw new SaisieIncorrecte("Usage : Impossible de retirer un like. La fritte n'a pas de likes.");
        }

        // Message de succès
        System.out.println("Le like sur la fritte de " + auteur + " numéro " + numFritte + " a été retiré par " + this.getPseudo());
        
    }
    /** La méthode fil permet de voir toutes ses frittes (originale, refrittage, ou réponse, textuelles 
     * ou multimédia) par ordre chronologique. 
     * 
     * @param fritos
     * @throws SaisieIncorrecte
     */
    public void fil(String fritos) throws SaisieIncorrecte {
        // Verifie que l'utilisateur appelant est connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage : Vous devez être connecté pour afficher le fil.");
        }

        try {
            // Les paramères ne peuvent être vides
            fritter.verifArgs(fritos);
        } catch (SaisieIncorrecte e) {
            throw new SaisieIncorrecte("Usage : fil <pseudo de l'utilisateur>.");
        }

        // Vérification que l'utilisateur existe
        Utilisateur fritosExiste = fritter.rechercheParPseudoActuel(fritos);
        if (fritosExiste == null) {
            fritosExiste = fritter.rechercheParPseudoAncien(fritos, this);
            if (fritosExiste == null) {
                throw new SaisieIncorrecte("Usage: L'utilisateur " + fritos + " n'existe pas.");
            }
        }

        // Agregation de toutesles frittes en  lien avec l'utilisateur qu'il les ai posté avec son pseudo ancien ou actuel
        List<Fritte> frittesdelaCible = new ArrayList<>();
        for (Map.Entry<Fritte, Fritte.Type> entry : fritter.getEnsembledesFrittes().entrySet()) {
            Fritte fritte = entry.getKey();
            if (fritte.getAuteur().equals(fritosExiste.getPseudo()) ||
                fritosExiste.getPseudoHistorique().contains(fritte.getAuteur())) {
                frittesdelaCible.add(fritte);
            }
        }

        // Si la liste est vide, un message indique l'inactivité de l'utilisateur
        if (frittesdelaCible.isEmpty()) {
            System.out.println("Oops... " + fritosExiste.getPseudo() + " n'est pas très actif! Il n'a posté aucune frittes!!");
        } else {
        	// Sinon trie des frittes par ordre chronologique
            frittesdelaCible.sort(Comparator.comparing(Fritte::getDate));
            
            System.out.println("Fil de l'utilisateur : " + fritosExiste.getPseudo());
            
            //Affichage de l'ensemble des frites
            for (Fritte fritte : frittesdelaCible) {
                afficherFritte(fritte);
            }
        }
    }

    /**
     * Affiche le profil de l'utilisateur, en affichant les frittes des abonnements 
     * tout en respectant les règles de gestion des refrittages.
     * 
     * @throws SaisieIncorrecte
     */
    public void monProfil() throws SaisieIncorrecte {
        // Vérification que l'utilisateur appelant est connecté
        if (!fritter.isFritosEnLigne(this)) {
            throw new SaisieIncorrecte("Usage : Vous devez être connecté pour afficher votre profil.");
        }

        // Vérification que l'utilisateur a des abonnements
        if (this.getAbonnements().isEmpty()) {
            throw new SaisieIncorrecte("Usage : Vous devez avoir des abonnements pour afficher votre fil.");
        }

        // Vérification que l'utilisateur existe
        Utilisateur fritosExiste = fritter.rechercheParPseudoActuel(this.getPseudo());
        if (fritosExiste == null) {
            fritosExiste = fritter.rechercheParPseudoAncien(this.getPseudo(), this);
            if (fritosExiste == null) {
                throw new SaisieIncorrecte("Usage: L'utilisateur " + this.getPseudo() + " n'existe pas.");
            }
        }

        // Récupération de toutes les frittes des abonnements de l'utilisateur
        List<Fritte> frittes = new ArrayList<>();
        for (Utilisateur abonnement : fritosExiste.getAbonnements().keySet()) {
            for (Map.Entry<Fritte, Fritte.Type> entry : fritter.getEnsembledesFrittes().entrySet()) {
                Fritte fritte = entry.getKey();

                // Vérification si la fritte appartient à l'abonné (pseudo actuel ou historique)
                if (fritte.getAuteur().equals(abonnement.getPseudo()) ||
                    abonnement.getPseudoHistorique().contains(fritte.getAuteur())) {

                    // Ignorer les refrittages si la fritte originale existe déjà
                    if (fritte.getType() == Fritte.Type.REFRITTAGE &&
                        frittes.stream().anyMatch(original ->
                            original.getAuteur().equals(fritte.getRefrittageOriginalAuteur()) &&
                            original.getContenu().equals(fritte.getContenu()))) {
                        continue;
                    }

                    frittes.add(fritte);
                }
            }
        }

        // Gestion des doublons et des refrittages multiples
        HashMap<String, Fritte> exemplaireUnique = new HashMap<>(); // Clé : Identifiant unique, Valeur : Fritte
        for (Fritte fritte : frittes) {
            String Id = genIdFritte(fritte);

            // Si la fritte n'est pas encore dans la map ou si elle est plus ancienne, on l'ajoute/remplace
            if (!exemplaireUnique.containsKey(Id) || exemplaireUnique.get(Id).getDate().after(fritte.getDate())) {
                exemplaireUnique.put(Id, fritte);
            }
        }

        // Conversion des frittes uniques en liste triée
        List<Fritte> frittesUniques = new ArrayList<>(exemplaireUnique.values());
        frittesUniques.sort(Comparator.comparing(Fritte::getDate));

        // Affichage des frittes ou d'un message en cas d'absence de contenu
        if (frittesUniques.isEmpty()) {
            System.out.println("Oops... Vous n'avez aucun contenu à afficher dans votre profil.");
        } else {
            System.out.println("Profil de l'utilisateur : " + fritosExiste.getPseudo());
            for (Fritte fritte : frittesUniques) {
                afficherFritte(fritte);
            }
        }
    }

    /**
     * Génère un identifiant unique pour une fritte basé sur son contenu et son auteur.
     * @param fritte La fritte à identifier
     * @return Une chaîne unique représentant la fritte
     */
    private String genIdFritte(Fritte fritte) {
        return fritte.getAuteur() + ":" + fritte.getContenu().hashCode();
    }

    
}

