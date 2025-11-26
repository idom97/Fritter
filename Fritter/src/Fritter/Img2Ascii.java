/**
 * Nom ......... : Img2Ascii.java
 * Rôle ........ : Sous-classe Img2Ascii.java 
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */
package Fritter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;

import javax.imageio.ImageIO;

public class Img2Ascii extends Fritte {
	  private BufferedImage img;
	  private double pixval;
	  private PrintWriter prntwrt;
	  private FileWriter filewrt;

	
	
    public Img2Ascii(Utilisateur auteur, String fichier) throws SaisieIncorrecte, IOException {
    	

    	super(
    			auteur.getPseudo(),   // récupération du pseudo l'utlisateur appelant
    			new Date(),          // définition de la date
    			Type.MULTIMEDIA,    // définition du type Multimédia
    			"",                // récupération du contenu
    			0,                // Initialisation des likes à 0
    			0,               // Initialize refrittages à 0
    			0,              //Initialisation des commentaires à 0
    			Fritter.genNumFritte() //méthode permettant de générer un numéro de Fritte
    			
    			);
		// On charge l'image dans la variable this.img
        try {
            this.img = ImageIO.read(new File(fichier));
            if (this.img == null) {// Vérification de la valeur présente dans this.img
                throw new SaisieIncorrecte("Erreur: Le fichier spécifié n'est pas une image valide.");
            }
        } catch (IOException e) {
            throw new IOException("Erreur: Impossible de charger l'image depuis le fichier: " + fichier, e);
        }

        // Initialisation des outils d'écriture
        try {
            this.filewrt = new FileWriter("asciiart.txt", true); // Append mode
            this.prntwrt = new PrintWriter(this.filewrt);
        } catch (IOException e) {
            throw new SaisieIncorrecte("Erreur: Impossible d'initialiser les fichiers pour l'ASCII art.");
        }

        // Permet de convertir l'image au format ASCII art
        convertToAscii(fichier);

        System.out.println("Fritte ASCII créée par: " + auteur.getPseudo());
    }
		
    /** Convertit une image au format ASCII
     * 
     * @param imgname
     */
    public void convertToAscii(String imgname) {
        StringBuilder asciiArt = new StringBuilder(); 

        try {
            img = ImageIO.read(new File(imgname));
            if (img == null) {
                throw new IOException("Image invalide ou introuvable.");
            }

            
            int targetWidth = img.getWidth() /2; // Ajustez la largeur de l'image pour minimiser la hauteur
            int targetHeight = img.getHeight() / 2; // Adjust la taille pour le format ASCII 
            BufferedImage scaledImg = new BufferedImage(targetWidth, targetHeight, img.getType());

            // Mettre à l'échelle l'image d'origine
            Graphics2D g2d = scaledImg.createGraphics();
            g2d.drawImage(img, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();

            img = scaledImg; // Utiliser l'image mise à l'échelle pour la conversion ASCII
        } catch (IOException e) {
            throw new RuntimeException("Erreur: Impossible de charger ou redimensionner l'image.", e);
        }

        // Convertir l'image mise à l'échelle en ASCII
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
                String character = strChar(pixval);

                // Ajouter un caractère à la chaîne ASCII
                asciiArt.append(character);

                // Écrire un caractère dans le fichier
                print(character);
            }

            // Ajouter un saut de ligne à la fin de chaque rangée
            asciiArt.append("\n");

            try {
                prntwrt.println(""); // Ajout d'un saut de ligne dans le fichier
                prntwrt.flush();
                filewrt.flush();
            } catch (Exception ex) {
            	// Gestion des exceptions
            }
        }

        // L'image convertie est nouvelle valeur de la  Fritte
        this.setContenu(asciiArt.toString());
    }

    /** Détermine le caractère ASCII correspondant à une valeur de luminosité donnée.
     * 
     * @param g
     * @return
     */
    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }
    
    /**Écrit un caractère dans le fichier ASCII associé à l'image convertie.
     * 
     * @param str
     */
    public void print(String str) {
        try {
            prntwrt.print(str);
            prntwrt.flush();
            filewrt.flush();
        } catch (Exception ex) {
        }
    }


}
