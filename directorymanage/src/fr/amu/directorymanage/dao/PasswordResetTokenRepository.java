/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

import java.util.Date;
import java.util.stream.Stream;

import fr.amu.directorymanage.beans.PasswordResetToken;

/**
 * 
 * Interface qui definit les methodes DAO liees a la table PersonsTokens
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public interface PasswordResetTokenRepository {
	
	/**
	 * la methode qui insere un objet de type PasswordToken
	 * @param myToken objet de type PasswordResetToken a insere
	 * @return un entier indique le nombre de ligne inseree
	 */
	int save(PasswordResetToken myToken);
	
	/**
	 * la methode qui cherche l'objet PasswordResetToken avec le token
	 * @param token chaine de caractere du token cherche
	 * @return un objet de type PasswordResetToken correspondant 
	 */
	PasswordResetToken findByToken(String token);
	
	/**
	 * la methode qui retourne un l'objet PasswordResetToken
	 * par l'identifiant de la personne
	 * @param personId l'identifiant de la personne qu'on veut recuperer
	 *  son objet correspondant
	 * @return un objet de type PasswordResetToken correspond a la personne
	 */
	PasswordResetToken findByPersonId(Long personId);
	
	/**
	 * la methode qui cherche tous les objets PasswordResetToken qui sont
	 * inférieure a la date actuelle 
	 * @param now objet de type Date a voir les objets PasswordResetToken
	 * qui sont inferieure a lui 
	 * @return objet de type Stream d'objet PasswordResetToken 
	 */
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	/**
	 * la methode qui efface tous les lignes qui sont inferieure
	 * a la date passe en parametre
	 * @param now objet de type Date, a efface toutes les lignes
	 * PasswordResetToken qui sont inferieure a lui
	 */
	void deleteByExpiryDateLessThan(Date now);
	
	/**
	 * la methode qui efface toutes les lignes depuis une date donne
	 * @param now objet de type Date, a efface toutes les lignes
	 * PasswordResetToken depuis lui
	 */
	void deleteAllExpiredSince(Date now);

}
