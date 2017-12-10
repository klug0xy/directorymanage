/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

import fr.amu.directorymanage.beans.Person;

/**
 * 
 * Interface de la couche DAO definit toutes les methodes d'acces 
 * a la table User_roles
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public interface IDirectoryManagerUserRoles {
	
	/**
	 * la methode qui met a jour la colonne Username,
	 * si une personne est modifiee
	 * @param person l'objet Person modifiee (mi a jour)
	 * @return un entier des nombres des lignes modifiees
	 */
	int updateUsernameUserRoles(Person person);
	
	/**
	 * la methode qui cherche le username (mail)  
	 * @param personMail String contenant le mail de la personne recherche
	 * @return une chaine de caractere qui stocke
	 * le username (mail de la personne) retournee
	 */
	String findUsernameUserRolesByUsername(String personMail);

}
