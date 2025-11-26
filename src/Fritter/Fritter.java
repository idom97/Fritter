/**
 * Nom ......... : Fritter.java
 * Rôle ........ : Classe Fritter 
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */
package Fritter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Fritter.Fritte.Type;

public class Fritter {
	
	
	private List<Utilisateur> fritos;//Un fritos est un utilisateur de Fritter
	private Map<Fritte, Type> originales;
	private Map<Fritte, Type> multimedia;
	private Map<Fritte, Type> ensembledesFrittes;
	private LinkedList<String> commentaires;
	private LinkedList<String> refrittages;
	private List<Utilisateur> fritosEnLigne;
	private Map<Fritte, Type> frittesFil;	
	private static int numFritteActuel = 0;


	/*
	 * Constructeur de la classe Fritter
	 */

	public Fritter(List<Utilisateur> fritos, Map<Fritte, Type> originales, Map<Fritte, Type> multimedia, 
			Map<Fritte, Type> ensembledesFrittes , LinkedList<String> commentaires, LinkedList<String> refrittages,
			List<Utilisateur> fritosEnLigne, Map<Fritte, Type> frittesFil, int numFritteActuel) {
		this.fritos = new ArrayList<>();
		this.originales = new HashMap<>();
		this.multimedia = new HashMap<>();
		this.ensembledesFrittes = new HashMap<>();
		this.commentaires = new LinkedList<>();
		this.refrittages = new LinkedList<>();
		this.fritosEnLigne = new ArrayList<>();
		this.frittesFil = new HashMap<>();
		Fritter.numFritteActuel = numFritteActuel;

	}
	
	/*
	 * Getters
	 */
	public List<Utilisateur> getFritos() {
		return fritos;
	}
	
	public Map<Fritte, Type> getMultimedia() {
		return multimedia;
	}

	public Map<Fritte, Type> getOriginales() {
		return originales;
	}
	
	public Map<Fritte, Type> getEnsembledesFrittes() {
		return ensembledesFrittes;
	}
	
	public LinkedList<String> getCommentaires() {
		return commentaires;
	}
	
	public LinkedList<String> getRefrittages() {
		return refrittages;
	}
	
	public List<Utilisateur> getFritosEnLigne() {
		return fritosEnLigne;
	}
	
	public static int getNumFritteActuel() {
		return numFritteActuel;
	}
	
	public Map<Fritte, Type> getFrittesFil() {
		return frittesFil;
	}
	
	/*
	 * Setters
	 */
	
	public void setFritos(List<Utilisateur> fritos) {
		this.fritos = fritos;
	}

	public void setOriginales(Map<Fritte, Type> originales) {
		this.originales = originales;
	}

	public void setMultimedia(Map<Fritte, Type> multimedia) {
		this.multimedia = multimedia;
	}

	public void setEnsembledesFrittes(Map<Fritte, Type> ensembledesFrittes) {
		this.ensembledesFrittes = ensembledesFrittes;
	}

	public void setCommentaires(LinkedList<String> commentaires) {
		this.commentaires = commentaires;
	}

	public void setRefrittages(LinkedList<String> refrittages) {
		this.refrittages = refrittages;
	}

	public void setFritosEnLigne(List<Utilisateur> fritosEnLigne) {
		this.fritosEnLigne = fritosEnLigne;
	}

	public void setFrittesFil(Map<Fritte, Type> frittesFil) {
		this.frittesFil = frittesFil;
	}

	public static void setNumFritteActuel(int numFritteActuel) {
		Fritter.numFritteActuel = numFritteActuel;
	}




	@Override
	public String toString() {
		return "Fritter [fritos=" + fritos + ", originales=" + originales + ", multimedia=" + multimedia
				+ ", ensembledesFrittes=" + ensembledesFrittes + ", commentaires=" + commentaires + ", refrittages="
				+ refrittages + ", fritosEnLigne=" + fritosEnLigne + ", frittesFil=" + frittesFil + "]";
	}
	/**
	 * Permet de créer un nouvel utilisateur sur la plateforme
	 * @param nom
	 * @param prenom
	 * @param pseudo
	 * @param email
	 * @param mdp
	 * @return
	 * @throws SaisieIncorrecte
	 */
	public Utilisateur creerCompte(String nom, String prenom, String pseudo, String email, String mdp) throws SaisieIncorrecte {
		//Les arguments ne peuvent être nuls      	
      	try {
  			// Vérifier que les paramètres sont valides
      		verifArgs(nom, prenom, pseudo, email, mdp);
  		} catch (SaisieIncorrecte e) {
  			
  			throw new SaisieIncorrecte("Usage : creerCompte <votre_nom> <votre_prénom>  <votre_nom_d'utilisateur> <votre_emailr> <votre_mdp>");
  		}
		//L'Email saisi doit être unique
		if (rechercheParEmail(email) != null) {
			throw new SaisieIncorrecte("Un utilisateur avec cet email existe déjà.");
		}
		
		//Le pseudo choisi doit respecter certaines règles
		if (!pseudo.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*_]{6,}$")) {
	        throw new SaisieIncorrecte("Usage: Le pseudo doit contenir au moins une lettre minuscule, "
	                + "une lettre majuscule, un chiffre, et un caractère spécial comme @ ou !. "
	                + "Les pseudos avec un underscore sont autorisés.");
	    }
		
		//Le Pseudo saisi doit être unique
		if (rechercheParPseudoActuel(pseudo) != null) {
			throw new SaisieIncorrecte("Un utilisateur avec ce pseudo existe déjà.");
		}
		
		if (rechercheParPseudoAncien(pseudo,null) != null) {
			throw new SaisieIncorrecte("Un utilisateur avec ce pseudo existe déjà.");
		}
		
		if (!mdp.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@!]).{6,}$"))
		 {
			throw new SaisieIncorrecte("Usage: Le mot de passe doit contenir au moins 6 caractères dont une "
					+ "lettre majuscule, un chiffre et un caractère spécial comme @ ou !.");
		}
	
		//Création d'un nouvel utilisateur
		Utilisateur nouveau = new Utilisateur(nom, prenom, pseudo, email, mdp, this);
		
		//Stockage des informations de l'utilisateurs dans la liste fritos
		fritos.add(nouveau);
		
		
		/*Message confirmant la création du compte*/
		System.out.println("Compte créé ! Bienvenue : " + pseudo);
		return nouveau;
	}
	/**
	 * Permet de vérifier que les arguments ne sont pas vides
	 * @param args
	 * @throws SaisieIncorrecte
	 */
	public void verifArgs(Object... args) throws SaisieIncorrecte {
			for (Object arg : args){
			    if (arg == null) {
				    throw new SaisieIncorrecte("Usage: Vous devez remplir tous les champs!");
			}
		}
	}
	/**
	 * Permet d'effectuer une recherche d'un utilisateur par e-mail
	 * @param email
	 * @return
	 */
	private Utilisateur rechercheParEmail(String email) {
        for (Utilisateur utilisateur : fritos) {
            if (utilisateur.getEmail().equalsIgnoreCase(email)) {
                return utilisateur;
            }
        }
        return null;
    }
	/**
	 * Permet de rechercher un utilisateur par son pseudo actuel
	 * @param pseudo
	 * @return
	 */
    public Utilisateur rechercheParPseudoActuel(String pseudo) {
        for (Utilisateur utilisateur : fritos) {
            if (utilisateur.getPseudo().contains(pseudo)) {
                return utilisateur;
            }
        }
        return null;
    }
    /**
     * Permet de rechercher un utilisateur par son pseudo ancien
     * @param pseudo
     * @param utilisateurAppelant
     * @return
     */
    public Utilisateur rechercheParPseudoAncien(String pseudo, Utilisateur utilisateurAppelant) {
    	for (Utilisateur utilisateur : fritos) {
	        if (utilisateur.getPseudoHistorique().contains(pseudo) && utilisateur != utilisateurAppelant) {
	            return utilisateur;
	        }
	    }
    	return null;
    }
    /**
     * Permet de savoir si un utilisateur est connecté sur la plateforme,
     * nécessaire pour l'utilisation de nombreuses méthodes
     * @param utilisateur
     * @return
     */
    public boolean isFritosEnLigne(Utilisateur utilisateur) {
	    return fritosEnLigne.contains(utilisateur);
	}
    
    /**
     * Mise à jour du pseudo dans la classe Utilisateur
     * @param utilisateur
     * @param newPseudo
     */
    void majPseudo(Utilisateur utilisateur, String newPseudo) {
        boolean utilisateurTrouve = false;

        // Mise à jour dans la liste des utilisateurs (fritos)
        for (Utilisateur u : fritos) {
            if (u.equals(utilisateur)) {
                u.setPseudo(new ArrayList<>(u.getPseudoHistorique())); // Copie historique avant mise à jour
                utilisateurTrouve = true;
            }
        }

        // Log pour indiquer la mise à jour du pseudo si utilisateur trouvé
        if (utilisateurTrouve) {
            System.out.println("Pseudo mis à jour pour l'utilisateur : " +
                    utilisateur.getAncienPseudo() + " -> Nouveau pseudo : " + newPseudo);
        } else {
            System.out.println("Aucun utilisateur correspondant trouvé pour mise à jour du pseudo.");
        }
    }


	/**
	 * Permet de fermer sa session sur la plateforme
	 * @param utilisateur
	 * @throws SaisieIncorrecte
	 */
    public void deconnexion(Utilisateur utilisateur) throws SaisieIncorrecte {
        if (fritosEnLigne.contains(utilisateur)) {
            fritosEnLigne.remove(utilisateur);
            System.out.println("Déconnexion réussie pour " + utilisateur.getPseudo());
        } else {
            throw new SaisieIncorrecte("Utilisateur non connecté.");
        }
    }
    /**Se connecter à Fritter
     * 
     * @param email
     * @param mdp
     * @return
     * @throws SaisieIncorrecte
     */
    public Utilisateur connexion(String email, String mdp) throws SaisieIncorrecte {
        for (Utilisateur utilisateur : fritos) {
            if (utilisateur.getEmail().equals(email) && utilisateur.getMdp().equals(mdp)) {
                if (!fritosEnLigne.contains(utilisateur)) {
                    fritosEnLigne.add(utilisateur); // Add user to the list of connected users
                }
                System.out.println("Bienvenue, " + utilisateur.getPseudo() + " !");
                return utilisateur;
            }
        }
        throw new SaisieIncorrecte("Email ou mot de passe incorrect.");
    }
    
    /**Attribuer un numéro à une fritte afin de ficiliter l'interaction avec celle-ci
     * 
     * @return
     */
    public static  int genNumFritte() {
        return numFritteActuel++;
    }
    /**Rechercher un comentaire
     * 
     * @param auteur
     * @param numFritte
     * @return
     */
    public Commentaires rechercherCommentaire(String auteur, int numFritte) {
    	if (auteur == null) {
            return null;
        }
        for (Fritte fritte : ensembledesFrittes.keySet()) {
            for (Commentaires commentaire : fritte.getCommentaire()) {
                if (commentaire.getAuteur().equals(auteur) && commentaire.getNumFritte() == numFritte) {
                    return commentaire;
                }
            }
        }
        return null;
    }
    
    public Fritte rechercherFritte(String auteur, int numFritte)throws SaisieIncorrecte {
    	// Détection de l'auteur initialisée à faux
    	boolean auteurDetection = false;
        
    	// Parcourt la Map ensembledesFrittes
        for (Map.Entry<Fritte, Fritte.Type> entry : ensembledesFrittes.entrySet()) {
        	Fritte fritteExiste = entry.getKey();
        	
            // Vérifie si le pseudo actuel et historique et le numéro de la fritte correspond à une entrée de la Map
            if (fritteExiste.getAuteur().equals(auteur) || (rechercheParPseudoActuel(auteur) != null && rechercheParPseudoActuel(auteur).getPseudoHistorique().contains(fritteExiste.getAuteur()))){
            	//Auteur trouvé
            	auteurDetection=true;
            	
            	if (fritteExiste.getNumFritte() == numFritte) {
            		return fritteExiste; // Retourne la Fritte correspondante
            	}
            }
        }
          
        // La fritte n'existe pas
        if(auteurDetection) {
        	throw new SaisieIncorrecte("Usage: L'auteur " + auteur + " n'a pas de frittes portant le numéro:  "+numFritte);
        }
        // L'auteur n'existe pas
        throw new SaisieIncorrecte("Usage: L'auteur " + auteur + " n'existe pas.");   
    }


}
