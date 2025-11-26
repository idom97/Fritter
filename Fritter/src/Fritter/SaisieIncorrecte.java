/**
 * Nom ......... : SaisieIncorrecte.java
 * Rôle ........ : Sous-classe SaisieIncorrecte.java 
 * Auteur ...... : Dominique, ERIN 
 * Version ..... : V0.1 du 20/12/2024
 * Licence ..... : Réalisé dans le cadre du Cours POO 
 */
package Fritter;

public class SaisieIncorrecte extends Exception {

	private static final long serialVersionUID = 1L;

	public SaisieIncorrecte(String message) {
		super(message);
	}
	
	public SaisieIncorrecte(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SaisieIncorrecte(Throwable cause) {
		super(cause);
	}

}
