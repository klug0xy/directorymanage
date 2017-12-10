/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.business;

/**
 * 
 * Interface des services lies a la personne
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public interface IPersonService {
	
	/**
	 * la methode qui cree un objet de type PasswordResetToken, pour la personne
	 * qui demande la recuperation de son mot de passe, 
	 * qui sert a inserer cet objet a la base de donnees
	 * @param personId l'identifiant de la personne
	 * qui veut recuperer son mot de passe
	 * @param token le token genere pour la personne
	 */
	void createPasswordResetTokenForUser(Long personId, String token);
	
	/**
	 * la methode qui verifie pour la personne authentifie
	 * s'il a un role donne ou non
	 * @param role une chaine de caractere indiquant le role a verifier
	 * @return un booleen repond si la personne authentifie
	 * a le droit passe en parametre ou non
	 */
	boolean hasRole(String role);
	
	/**
	 * la methode qui calcul le hash d'un mot de passe
	 * donne par l'algorithme bcrypt
	 * @param password une chaine de caractere represente le mot de passe a hashe
	 * @return le hash bcrypt du mot de passe passe en parametre 
	 */
	String hashPassword(String password);


}
