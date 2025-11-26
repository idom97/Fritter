/**
 * Nom ......... : TestUnitaire.java
 * Rôle ........ : Sous-classe TestUnitaire.java 
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */
package FritterTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Fritter.Commentaires;
import Fritter.Fritte;
import Fritter.Fritter;
import Fritter.SaisieIncorrecte;
import Fritter.Utilisateur;



@FixMethodOrder(MethodSorters.JVM)
public class TestUnitaire {

    private static Fritter fritter;
    private static Utilisateur u1, u2, u3, u4,u5;

    @BeforeClass
    public static void setup() throws SaisieIncorrecte {
        // Initialisation de l'instance de Fritter et création des utilisateurs
        fritter = new Fritter(new ArrayList<>(), new HashMap<>(), new HashMap<>(),
        		new HashMap<>() ,new LinkedList<>(), new LinkedList<>(), new ArrayList<>(), 
        		new HashMap<>(), 1);
        u1 = fritter.creerCompte("ANTZ", "Émile", "@TiEmile972", "A@exemple.com", "@Mdp10");
        u2 = fritter.creerCompte("ZUNES", "Héglantine", "@Queen971", "B@exemple.com", "@Mdp10");
        u3 = fritter.creerCompte("BONES", "Fernand", "@Typikal94", "C@exemple.com", "@Mdp10");
        u4 = fritter.creerCompte("JOHN", "Albert", "@Jojo404", "D@exemple.com", "@Mdp10");
        u5 = fritter.creerCompte("DOE", "Joe", "@Jdoe78","E@exemple.com", "@Mdp10");
        System.out.println(" ");
        System.out.println("Initialisation complète avec création de "+ fritter.getFritos().size() +" comptes.");
        System.out.println("------");
    }
    
    
    /**
     * T1 : Vérifier la création de comptes
     */
    @Test
    public void test1_CreationCompte() {
        // Vérification de l'utilisateur u1
        assertNotNull("L'utilisateur u1 doit être créé.", u1);
        assertEquals("L'utilisateur u1 doit avoir le pseudo '@TiEmile972'.", "@TiEmile972", u1.getPseudo());
        assertEquals("L'utilisateur u1 doit avoir l'email 'A@exemple.com'.", "A@exemple.com", u1.getEmail());

        // Vérification de l'utilisateur u2
        assertNotNull("L'utilisateur u2 doit être créé.", u2);
        assertEquals("L'utilisateur u2 doit avoir le pseudo '@Queen971'.", "@Queen971", u2.getPseudo());
        assertEquals("L'utilisateur u2 doit avoir l'email 'B@exemple.com'.", "B@exemple.com", u2.getEmail());
        
        // Vérification de l'utilisateur u3
        assertNotNull("L'utilisateur u3 doit être créé.", u3);
        assertEquals("L'utilisateur u3 doit avoir le pseudo '@Typikal94'.", "@Typikal94", u3.getPseudo());
        assertEquals("L'utilisateur u3 doit avoir l'email 'C@exemple.com'.", "C@exemple.com", u3.getEmail());
        
        // Vérification de l'utilisateur u4
        assertNotNull("L'utilisateur u4 doit être créé.", u4);
        assertEquals("L'utilisateur u4 doit avoir le pseudo '@Jojo404'.", "@Jojo404", u4.getPseudo());
        assertEquals("L'utilisateur u4 doit avoir l'email 'D@exemple.com'.", "D@exemple.com", u4.getEmail());
        
        // Vérification de l'utilisateur u5
        assertNotNull("L'utilisateur u5 doit être créé.", u5);
        assertEquals("L'utilisateur u5 doit avoir le pseudo '@Jdoe78'.", "@Jdoe78", u5.getPseudo());
        assertEquals("L'utilisateur u5 doit avoir l'email 'E@exemple.com'.", "E@exemple.com", u5.getEmail());

        System.out.println("Test T1 : Les comptes ont été créés avec succès.");
        
        System.out.println("------");
    }

    /**
     * T2 : Vérifier la connexion
     */
    @Test
    public void test2_Connexion() throws SaisieIncorrecte {
    	System.out.println("Test T2 : Connexion de tous les utilisateurs.");
        
    	Utilisateur u1 = fritter.connexion("A@exemple.com", "@Mdp10");
        assertNotNull("L'utilisateur u1 doit pouvoir se connecter.", u1);
        assertEquals("L'utilisateur connecté doit avoir l'email 'A@exemple.com'.", "A@exemple.com", u1.getEmail());
        
        Utilisateur u2 = fritter.connexion("B@exemple.com", "@Mdp10");
        assertNotNull("L'utilisateur u2 doit pouvoir se connecter.", u2);
        assertEquals("L'utilisateur connecté doit avoir l'email 'B@exemple.com'.", "B@exemple.com", u2.getEmail());
        
        Utilisateur u3 = fritter.connexion("C@exemple.com", "@Mdp10");
        assertNotNull("L'utilisateur u2 doit pouvoir se connecter.", u3);
        assertEquals("L'utilisateur connecté doit avoir l'email 'C@exemple.com'.", "C@exemple.com", u3.getEmail());
        
        Utilisateur u4 = fritter.connexion("D@exemple.com", "@Mdp10");
        assertNotNull("L'utilisateur u4 doit pouvoir se connecter.", u4);
        assertEquals("L'utilisateur connecté doit avoir l'email 'D@exemple.com'.", "D@exemple.com", u4.getEmail());
        
        System.out.println("------");
    }

    /**
     * T3 : Vérifier une erreur pour un mot de passe incorrect
     */
    @Test
    public void test3_MotDePasseIncorrect() {
        System.out.println("Test T3 : Saisie mot de passe incorrect.");


        try {
            fritter.connexion("D@exemple.com", "J'AI_OUBLIÉ_MON_MOT_DE_PASSE");
            fail("Une exception SaisieIncorrecte aurait dû être levée pour un mot de passe incorrect.");
        } catch (SaisieIncorrecte e) {
            assertEquals("Email ou mot de passe incorrect.", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("------");

        }
    }

    /**
     * T4 : Modifier un pseudo avec succès
     */
    @Test
    public void test4_editPseudo() throws SaisieIncorrecte {
        System.out.println("Test T4 : Un utilisateur modifie son pseudo");
        
        // Connexion de l'utilisateur
        Utilisateur u5 = fritter.connexion("E@exemple.com", "@Mdp10");
        assertEquals("L'utilisateur connecté doit être u5 dont le pseudo est @Jode78.", "@Jdoe78", u5.getPseudo());
        assertNotNull("L'utilisateur u5 doit pouvoir se connecter.", u5);
        assertEquals("L'utilisateur connecté doit avoir l'email 'D@exemple.com'.", "E@exemple.com", u5.getEmail());
        
        // Modifier le pseudo
        String nouveauPseudo = "@Bulle972";
        u5.editPseudo(nouveauPseudo);

        // Vérification : Le pseudo actuel doit être mis à jour
        assertEquals("Le pseudo de l'utilisateur u5 doit être mis à jour en '@Bulle972'.", 
                     nouveauPseudo, u5.getPseudo());

        // Vérification : Le nouveau pseudo doit être trouvable dans les pseudos actuels
        assertNotNull("Le pseudo '@Bulle972' doit être trouvable dans les pseudos actuels.", 
                      fritter.rechercheParPseudoActuel(nouveauPseudo));

        // Vérification : L'ancien pseudo doit être présent dans l'historique des pseudos
        assertTrue("L'ancien pseudo '@Jdoe78' doit être présent dans l'historique des pseudos de l'utilisateur.", 
                   u5.getPseudoHistorique().contains("@Jdoe78"));

        // Vérification : Le nouveau pseudo ne doit pas être trouvé dans les pseudos anciens
        assertNull("Le pseudo '@Bulle972' ne doit pas être présent dans les pseudos anciens.", 
                   fritter.rechercheParPseudoAncien(nouveauPseudo, u5));

        System.out.println("Test réussi : Le pseudo a été modifié avec succès.");
        System.out.println("------");
    }

    /**
     * T5 : Empêcher la réutilisation d'un pseudo déjà utilisé par un autre utilisateur
     */
    @Test
    public void test5_PseudoNonLibre() throws SaisieIncorrecte {
    	System.out.println("Test T5 : Un utilisateur modifie son pseudo actuel par un pseudo déjà utilisé par un autre utilisateur.");
        try {
            u1.editPseudo("@Jojo404");
            fail("Une exception SaisieIncorrecte aurait dû être levée pour un pseudo déjà utilisé.");
        } catch (SaisieIncorrecte e) {
            assertEquals("Usage: Ce pseudo est actuellement utilisé par un autre utilisateur.", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("------");
        }
    }

    /**
     * T6 : Autoriser la réutilisation de son propre pseudo précédent
     */
    @Test
    public void test6_ReutiliserSonPseudo() throws SaisieIncorrecte {
    	System.out.println("Test T6 : Réutilisation d'un pseudo.");
    	
    	assertEquals("L'utilisateur u5 a actuellement pour pseudo '@Bulle972'.", "@Bulle972", u5.getPseudo());
    	
        u5.editPseudo("@Jdoe78");
        
        assertEquals("L'utilisateur u5 doit pouvoir réutiliser son ancien pseudo '@Jdoe78'.", "@Jdoe78", u5.getPseudo());
        assertNotNull("Le pseudo '@Jdoe78' doit être trouvable dans Fritter.", fritter.rechercheParPseudoActuel("@Jdoe78"));
        assertNotNull("Le pseudo '@Bulle972' doit être trouvable dans Fritter.", u5.getAncienPseudo());
        assertNotNull("Le pseudo '@Bulle972' doit être trouvable dans Fritter.", u5.getPseudoHistorique());
        System.out.println("------");

    }
    
    /**
     * T7 : Modification du mot de passe
     */

    @Test
    public void test7_editMdp() throws SaisieIncorrecte {
        System.out.println("Test T7 : Modification d'un mot de passe.");

        assertEquals("L'utilisateur connecté doit être @Typikal94.", "@Typikal94", u3.getPseudo());

        u3.editMdp("P@ssword645");
        assertEquals("L'utilisateur u3 doit avoir son mot de passe mis à jour.", "P@ssword645", u3.getMdp());

        System.out.println("Mot de passe modifié avec succès pour l'utilisateur : " + u3.getPseudo());
        System.out.println("------");
    }

    /**
     * T8 : Modification incorrecte du mot de passe
     */
    @Test
    public void test8_ModifIncorrecte_Mdp() throws SaisieIncorrecte {
        System.out.println("Test T8 : Modification incorrecte d'un mot de passe.");

        Utilisateur connectedUser = fritter.connexion("D@exemple.com", "@Mdp10");
        assertEquals("L'utilisateur connecté doit être u4.", u4, connectedUser);

        try {
            u4.editMdp("123645");
            fail("Une exception SaisieIncorrecte aurait dû être levée pour un mot de passe incorrect.");
        } catch (SaisieIncorrecte e) {
            assertEquals(
                "Usage: Le mot de passe doit contenir au moins 6 caractères dont une lettre majuscule, un chiffre et un caractère spécial comme @ ou !.",
                e.getMessage()
            );
            System.out.println(e.getMessage());
        }
        System.out.println("------");
    }
   
    /**
     * T9 : Suivre un utilisateur
     */
    @Test
    public void test9_SuivreUtilisateur() throws SaisieIncorrecte {
        System.out.println("Test T9 : Suivre un utilisateur.");

        fritter.connexion("A@exemple.com", "@Mdp10");
        u1.suivre("@Queen971");

        assertTrue("u1 doit suivre u2.", u1.getAbonnements().containsKey(u2));
        System.out.println("Test réussi : u1 suit maintenant u2.");
        System.out.println("------");
    }
    
    /**
     * T10 : Suivre un utilisateur avec son ancien pseudo
     */
    @Test
    public void test10_SuivreUtilisateurAncienPseudo() throws SaisieIncorrecte {
        System.out.println("Test T10 : Suivre un utilisateur avec son ancien pseudo.");

        fritter.connexion("D@exemple.com", "@Mdp10");
        u4.editPseudo("@Dorlis973!");

        fritter.connexion("A@exemple.com", "@Mdp10");
        u1.suivre("@Jojo404");

        assertTrue("u1 doit suivre u4.", u1.getAbonnements().containsKey(u4));
        System.out.println("Test réussi : u1 suit maintenant u4.");
        System.out.println("------");
    }
    /*
     * T11: Suivre son propre compte
     */

    @Test
    public void test11_SuivreSonCompte() throws SaisieIncorrecte {
        System.out.println("Test T11 : Suivre son compte.");

        fritter.connexion("A@exemple.com", "@Mdp10");

        try {
            
            u1.suivre("@TiEmile972");

            
            fail("Une exception SaisieIncorrecte aurait dû être levée.");
        } catch (SaisieIncorrecte e) {
           
            assertEquals("Usage: Vous ne pouvez pas vous suivre vous-même.", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("------");
        }
    }
    
    /*
     * T12: Suivre un compte 2 fois
     */
    @Test
    public void test12_Suivre2fois() throws SaisieIncorrecte {
        System.out.println("Test T12 : Suivre un abonnement 2fois.");

        fritter.connexion("A@exemple.com", "@Mdp10");

        try {
            
            u1.suivre("@Queen971");
            u1.suivre("@Queen971");

          
            fail("Une exception SaisieIncorrecte aurait dû être levée.");
        } catch (SaisieIncorrecte e) {
            
            assertEquals("Usage: Vous suivez déjà cet utilisateur.", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("------");
        }
    }
    
    /*
     * T13: Arrêter un abonnement
     */
    @Test
    public void test13_StopAbonnement() throws SaisieIncorrecte {
        System.out.println("Test T13 : Arrêter de suivre un utilisateur.");

        
        fritter.connexion("C@exemple.com", "P@ssword645");
        u3.suivre("@Queen971");

        assertTrue("u3 doit suivre u2.", u3.getAbonnements().containsKey(u2));

        u3.stopAbonnement("@Queen971");

        assertFalse("u3 ne doit plus suivre u2.", u3.getAbonnements().containsKey(u2));
        System.out.println("Test réussi : u3 ne suit plus u2.");
        System.out.println("------");
    }
    
    /*
     * T14: Arrêter un abonnement auquel on n'est pas abonné
     */
    @Test
    public void test14_StopAbonnementErreur() throws SaisieIncorrecte {
        System.out.println("Test T14 : Arrêter de suivre un utilisateur auquel on n'est pas abonné.");

        fritter.connexion("C@exemple.com", "P@ssword645");

        assertFalse("u3 ne doit pas suivre u2 (@Queen971) avant le test.", u3.getAbonnements().containsKey(u2));

        try {
            u3.stopAbonnement("@Queen971");
            fail("Une exception SaisieIncorrecte aurait dû être levée.");
        } catch (SaisieIncorrecte e) {
        	
            assertEquals("Usage: Vous ne suivez pas @Queen971...", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("------");
        }
    }
    
    /*
     * T15: Éssayer d'arrêter un abonnement sur soi-même
     */
    @Test
    public void test15_StopAbonnementErreur() throws SaisieIncorrecte {
        System.out.println("Test T15 : Essayer d'arrêter de se suivre soi-même.");

        fritter.connexion("C@exemple.com", "P@ssword645");

        // Attempt to unfollow themselves
        try {
            u3.stopAbonnement("@Typikal94");
            fail("Une exception SaisieIncorrecte aurait dû être levée.");
        } catch (SaisieIncorrecte e) {
        	
            assertEquals("Usage: Vous ne pouvez pas stopper un abonnement sur vous-même...", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("------");
        }
    }
    
    /*
	* T16: Arret de suivi signifie suppression de date
	*/
    @Test
    public void test16_StopAbonnementDateDeletion() throws SaisieIncorrecte {
        System.out.println("Test 16 : Vérifier la suppression de la date lors de l'arrêt du suivi.");
        
        fritter.connexion("A@exemple.com", "@Mdp10");
        u1.suivre("@Typikal94");

        assertTrue("u1 doit suivre u3.", u1.getAbonnements().containsKey(u3));
        assertNotNull("La date d'abonnement de u1 à u3 ne doit pas être nulle.", u1.getAbonnements().get(u3));

        u1.stopAbonnement("@Typikal94");

        assertFalse("u1 ne doit plus suivre u3.", u1.getAbonnements().containsKey(u3));
        assertNull("La date d'abonnement de u1 à u3 doit être nulle.", u1.getAbonnements().get(u3));
        System.out.println("------");
        
    }
    /*
     * T17: Durée de suivi d'un abonnement
     */
    @Test
    public void test17_TmpsDeSuivi() throws SaisieIncorrecte, InterruptedException {
    	System.out.println("Test 17 : Afficher le temps de suivi.");
    	
    	u1.suivre(u5.getPseudo());
    	assertTrue("u1 doit suivre u5.", u1.getAbonnements().containsKey(u5));
    	
    	// On simule un délai de 2 secondes
        Thread.sleep(2000); 
    	
        u1.tmpsdeSuivi(u5.getPseudo());
        System.out.println("------");
       
    }
    /*
     * T18 : Publication d'une fritte textuelle
     */
    @Test
    public void test18_PosterFritteTextuelle() throws SaisieIncorrecte {
        System.out.println("Test 18 : Poster une fritte de type TEXTE.");

        // Étape 1 : u1 poste un message
        String contenuMessage = "Salut Les AMIS!!!";
        u1.textuelle(contenuMessage);

        // Récupérer le message posté (fritte)
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getOriginales().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo()) && fritte.getContenu().equals(contenuMessage)) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("Le message posté par u1 devrait exister.", fritteTexte);

        System.out.println("Test 18 réussi : Le message textuel a été posté correctement.");
        System.out.println("------");
    }
    /*
     * T18_1 : Publication d'une fritte textuelle de plus de 140 caractères
     */
    @Test
    public void test18_1_PosterFritteTextuelle() throws SaisieIncorrecte {
        System.out.println("Test 18_1 : Poster une fritte de type TEXTE avec plus de 140 caractères.");

        // Étape 1 : Génération d'un message de plus de 140 caractères
        StringBuilder texteLong = new StringBuilder();
        while (texteLong.length() <= 140) {
            texteLong.append("Salut Les AMIS!!! ");
        }

        // Assurez-vous que le texte dépasse 140 caractères
        String contenuMessage = texteLong.toString();

        try {
            u1.textuelle(contenuMessage);
            fail("Une exception aurait dû être levée pour un message de plus de 140 caractères");
        } catch (SaisieIncorrecte e) {
            System.out.println("L'erreur attendue a été capturée : " + e.getMessage());
        }

        
        System.out.println("Test 18_1 réussi : Un texte > 140 caractères ne peut être posté");
        System.out.println("------");
    }

    /*
     * T18_2 : Ajouter des commentaires à une fritte textuelle
     */
    @Test
    public void test18_2_CommenterFritteTextuelle() throws SaisieIncorrecte {
        System.out.println("Test 18_2 : Commenter une fritte textuelle.");

        // Récupérer la fritte postée par u1
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getOriginales().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo()) && fritte.getContenu().equals("Salut Les AMIS!!!")) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("Le message posté par u1 devrait exister.", fritteTexte);

        // Étape 2 : u1 commente son propre message
        String premierCommentaire = "Vous êtes lààà ???";
        u1.commentaire(u1.getPseudo(), fritteTexte.getNumFritte(), premierCommentaire);

        // Vérifier que le commentaire a été ajouté
        LinkedList<Commentaires> commentaires = fritteTexte.getCommentaire();
        assertNotNull("La fritte devrait avoir une liste de commentaires.", commentaires);
        assertEquals("La fritte devrait avoir un commentaire.", 1, commentaires.size());
        assertEquals("Le contenu du premier commentaire devrait être correct.", premierCommentaire, commentaires.get(0).getContenu());

        System.out.println("Test 18_2 réussi : Les commentaires ont été ajoutés correctement.");
        System.out.println("------");
    }

    /*
     * T18_3 : Interaction avec des commentaires (ajout de plusieurs commentaires)
     */
    @Test
    public void test18_3_InteractionsCommentaires() throws SaisieIncorrecte {
        System.out.println("Test 18_3 : Ajouter plusieurs commentaires à une fritte textuelle.");

        // Récupérer la fritte postée par u1
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getOriginales().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo()) && fritte.getContenu().equals("Salut Les AMIS!!!")) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("Le message posté par u1 devrait exister.", fritteTexte);

        // Ajouter plusieurs commentaires
        String[] contenuCommentaires = {
            "Salut, je suis là!",
            "Toujours là pour toi!",
            "Oui, que se passe-t-il?",
            "Présent et à l'écoute!",
            "OUIII!!!"
        };

        u2.commentaire(u1.getPseudo(), fritteTexte.getNumFritte(), contenuCommentaires[0]);
        u3.commentaire(u1.getPseudo(), fritteTexte.getNumFritte(), contenuCommentaires[1]);
        u4.commentaire(u1.getPseudo(), fritteTexte.getNumFritte(), contenuCommentaires[2]);
        u5.commentaire(u1.getPseudo(), fritteTexte.getNumFritte(), contenuCommentaires[3]);
        u1.commentaire(u1.getPseudo(), fritteTexte.getNumFritte(), contenuCommentaires[4]);

        LinkedList<Commentaires> commentaires = fritteTexte.getCommentaire();
        assertEquals("La fritte devrait maintenant avoir 6 commentaires.", 6, commentaires.size());

        System.out.println("Test 18_3 réussi : Les interactions avec les commentaires sont correctes.");
        System.out.println("------");
    }

    /*
     * T18.4 : Gestion des likes et des unlikes sur une fritte et ses commentaires
     */
    @Test
    public void test18_4_LikesUnlikes() throws SaisieIncorrecte {
        System.out.println("Test 18_4 : Gestion des likes et des unlikes.");

        // Récupérer la fritte postée par u1
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getOriginales().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo()) && fritte.getContenu().equals("Salut Les AMIS!!!")) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("Le message posté par u1 devrait exister.", fritteTexte);

        // Ajouter des likes
        
        u1.like(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u2.like(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u3.like(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u4.like(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u5.like(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        
        u2.like(fritteTexte.getAuteur(), 2);

        // Vérifier les likes
        assertEquals("Le nombre de likes sur la fritte devrait être correct.", 5, fritteTexte.getNbreLike());
        
        
        u1.afficherFritte(fritteTexte);

        // Ajouter des unlikes
        u1.unlike(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u2.unlike(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u3.unlike(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u4.unlike(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        u5.unlike(fritteTexte.getAuteur(), fritteTexte.getNumFritte());
        

        // Vérifier les unlikes
        assertEquals("Le nombre de likes sur la fritte devrait être réinitialisé.", 0, fritteTexte.getNbreLike());
        
        u1.afficherFritte(fritteTexte);

        System.out.println("Test 18_4 réussi : Les likes et unlikes fonctionnent correctement.");
        System.out.println("------");
    }

    
    /*
     * T19 : Connexion et publication d'une fritte multimédia
     */
    @Test
    public void test19_PosterFritteMultimedia() throws SaisieIncorrecte, IOException {
        System.out.println("Test 19 : Publication d'une fritte multimédia.");

  
        String cheminImage = "src/Images/Gane.png";
        File imageTest = new File(cheminImage);
        assertTrue("Le fichier d'image de test doit exister.", imageTest.exists());

        u2.multimedia(cheminImage);

        // Vérifier que la fritte multimédia a été postée
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getEnsembledesFrittes().keySet()) {
            if (fritte.getAuteur().equals(u2.getPseudo()) && fritte.getContenu().contains("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#")) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("La fritte multimédia devrait exister après la publication.", fritteTexte);

        System.out.println("Test 19 terminé.");
        System.out.println("------");
    }

    /*
     * T19_1 : Interagir avec une fritte multimédia (commentaires et likes)
     */
    @Test
    public void test19_1_InteragirAvecFritteMultimedia() throws SaisieIncorrecte {
        System.out.println("Test 19_1 : Interagir avec une fritte multimédia.");

        // Récupérer la fritte postée par u2
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getEnsembledesFrittes().keySet()) {
            if (fritte.getAuteur().equals(u2.getPseudo()) && fritte.getContenu().contains("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#")) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("La fritte multimédia devrait exister.", fritteTexte);
        
        // Ajouter plusieurs commentaires
        String[] contenuCommentaires = {
            "Vous le connaissez? C'est combattant de MMA! Un français!",
            "Oui, j'ai vu son combat!",
            "Gane a vaincu avec deux orteilles cassés!! Incroyable!!",
            "Un guerrier!!",
            "Wai mais il a pas voulu de la victoire! Il est déçu de lui!"
        };

        u2.commentaire(fritteTexte.getAuteur(), fritteTexte.getNumFritte(), contenuCommentaires[0]);
        u3.commentaire(fritteTexte.getAuteur(), fritteTexte.getNumFritte(), contenuCommentaires[1]);
        u4.commentaire(fritteTexte.getAuteur(), fritteTexte.getNumFritte(), contenuCommentaires[2]);
        u1.commentaire(fritteTexte.getAuteur(), fritteTexte.getNumFritte(), contenuCommentaires[3]);
        u5.commentaire(fritteTexte.getAuteur(), fritteTexte.getNumFritte(), contenuCommentaires[4]);

        
        LinkedList<Commentaires> commentaires = fritteTexte.getCommentaire();
        assertEquals("La fritte multimédia devrait avoir 5 commentaires.", 5, commentaires.size());

        // Ajouter des likes
        u2.like(u2.getPseudo(),9);
        u1.like(u2.getPseudo(),9);
        u3.like(u2.getPseudo(),9);
        u4.like(u2.getPseudo(),9);
        u5.like(u2.getPseudo(),9);
        
        u4.like(u4.getPseudo(),11);
        u1.like(u4.getPseudo(),11);
        u2.like(u4.getPseudo(),11);
        u3.like(u4.getPseudo(),11);
        
        u5.like(u5.getPseudo(),13);
        u2.like(u5.getPseudo(),13);
        
        
        
        // Vérification des likes pour les frittes
        assertEquals("La fritte de u2 devrait avoir 5 likes.", 5, fritter.rechercherFritte(u2.getPseudo(), 9).getNbreLike());
        assertEquals("La fritte de u4 devrait avoir 4 likes.", 4, fritter.rechercherFritte(u4.getPseudo(), 11).getNbreLike());
        assertEquals("La fritte de u5 devrait avoir 2 likes.", 2, fritter.rechercherFritte(u5.getPseudo(), 13).getNbreLike());

        
        
        u1.afficherFritte(fritteTexte);

        System.out.println("Test 19_1 terminé.");
        System.out.println("------");
    }
    

    /*
     * T19_2 : Refrittage d'une fritte multimédia
     */
    @Test
    public void test19_2_RefrittageFritteMultimedia() throws SaisieIncorrecte {
        System.out.println("Test 19_2 : Refrittage d'une fritte multimédia.");

        // Récupérer la fritte postée par u2
        Fritte fritteTexte = null;
        for (Fritte fritte : fritter.getEnsembledesFrittes().keySet()) {
            if (fritte.getAuteur().equals(u2.getPseudo()) && fritte.getContenu().contains("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#")) {
                fritteTexte = fritte;
            }
        }
        assertNotNull("La fritte multimédia devrait exister.", fritteTexte);

        // Effectuer un refrittage
        u1.refrittage(fritteTexte.getAuteur(), fritteTexte.getNumFritte());

        // Vérifier que le refrittage existe
        Fritte refritte = null;
        for (Fritte fritte : fritter.getEnsembledesFrittes().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo()) && fritte.getContenu().equals(fritteTexte.getContenu())) {
                refritte = fritte;
            }
        }
        assertNotNull("La fritte refrittée devrait exister.", refritte);

        // Vérifier les attributs réinitialisés pour le refrittage
        assertEquals("Les likes doivent être réinitialisés à 0 pour une fritte refrittée.", 0, refritte.getNbreLike());
        assertEquals("Les commentaires doivent être réinitialisés à 0 pour une fritte refrittée.", 0, refritte.getCommentaire().size());
        assertEquals("L'auteur original doit être enregistré dans le refrittage.", fritteTexte.getAuteur(), refritte.getRefrittageOriginalAuteur());

        // Afficher la fritte refrittée
        u1.afficherFritte(refritte);
        
        

        System.out.println("Test 19_2 terminé.");
        System.out.println("------");
    }
    
    

    /*
     * T20 : Vérification du fil d'un utilisateur
     */
   
    @Test
    public void test20_VerificationFilUtilisateur() throws SaisieIncorrecte {
        System.out.println("Test 20 : Vérification du fil d'un utilisateur.");

        // Afficher le fil de u1
        u5.fil(u1.getPseudo());

        // Capture et analyse du contenu du fil affiché
        List<Fritte> filAffiche = new ArrayList<>();
        for (Fritte fritte : fritter.getEnsembledesFrittes().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo())) {
                filAffiche.add(fritte);
            }
        }
        
        // Trier les frittes par date pour assurer l'ordre attendu
        filAffiche.sort(Comparator.comparing(Fritte::getDate));

        assertFalse("Le fil de u1 ne devrait pas être vide.", filAffiche.isEmpty());

        

        // Vérification du contenu de la première fritte
        String contenuAttendu = "Salut Les AMIS!!!";
        assertTrue("Le contenu de la première fritte devrait être correct.",
            filAffiche.get(0).getContenu().trim().contains(contenuAttendu));

        System.out.println("Test 20 réussi : Le fil d'un utilisateur s'affiche correctement.");
        System.out.println("------");
    }

    /*
     * T21: Vérification du fil d'un utilisateur
     */
    @Test
    public void test21_VerificationPageAccueillUtilisateur() throws SaisieIncorrecte {
        System.out.println("Test 21 : Vérification de la page d'accueil fil d'un utilisateur.");
        
        
        
        try {
        	u5.monProfil();
            fail("Une exception aurait dû être levée car u5 n'a pas d'abonnements");
        } catch (SaisieIncorrecte e) {
            System.out.println("L'erreur attendue a été capturée : " + e.getMessage());
        }

        

        System.out.println("Test 21 réussi : Le fil d'un utilisateur s'affiche correctement.");
        System.out.println("------");
    }
    
    /*
     * T22: Vérification du fil d'un utilisateur
     */
    @Test
    public void test22_VerificationPageAccueillUtilisateur() throws SaisieIncorrecte {
        System.out.println("Test 22 : Vérification de la page d'accueil fil d'un utilisateur.");
        
        u5.suivre(u1.getPseudo());
        u5.suivre(u2.getPseudo());
        u5.suivre(u3.getPseudo());
        u5.suivre(u4.getPseudo());
        
        u5.monProfil();
        
        // Capture et analyse du contenu du fil affiché
        List<Fritte> filAffiche = new ArrayList<>();
        for (Fritte fritte : fritter.getEnsembledesFrittes().keySet()) {
            if (fritte.getAuteur().equals(u1.getPseudo())) {
                filAffiche.add(fritte);
            }
        }
     
        // Vérification qu'il n'y a pas de doublons
        Set<String> contenuUnique = new HashSet<>();
        for (Fritte fritte : filAffiche) {
            assertTrue("Aucun doublon ne devrait exister dans le fil d'accueil.",
                contenuUnique.add(fritte.getContenu()));
        }
        assertEquals("Le nombre de frittes uniques devrait correspondre au nombre total de frittes affichées.",
            contenuUnique.size(), filAffiche.size());

        System.out.println("Test 22 réussi : Le fil d'un utilisateur s'affiche correctement.");
        System.out.println("----");
    }

  
}

