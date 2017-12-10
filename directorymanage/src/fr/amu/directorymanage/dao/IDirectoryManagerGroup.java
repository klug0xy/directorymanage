/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

import java.util.Collection;
import java.util.Map;

import fr.amu.directorymanage.beans.Group;

/**
 * 
 * Interface de la couche DAO definit toutes les methodes d'acces a la table Groupe
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public interface IDirectoryManagerGroup {
	
	/**
	 * la methode sert a inserer un objet de type Group dans la base de donnees
	 * @deprecated
	 * @param g variable de type Group
	 * @return un entier indique le nombre de ligne insere
	 */
	int saveGroup(Group g);
	
	/**
	 * la methode sert a inserer un groupe avec un identifiant auto-increment
	 * @param group l'objet group a inserer
	 * @return un entier indique le nombre de ligne insere
	 */
	
	int saveGroupAuto(Group group);
	
	/**
	 * la methode pour modifier (mettre a jour) les informations d'un groupe
	 * @param group l'objet group a mettre a jour
	 * @return un entier indiquant le nombre de ligne mis a jour
	 */

	int updateGroup(Group group);
	
	/**
	 * la methode pour chercher un groupe par son identifiant
	 * @param groupId l'identifiant du groupe
	 * @return un objet de type Group 
	 */
	
	Group findGroup(Long groupId);
	
	/**
	 * la methode pour chercher un groupe par son nom
	 * @param groupName le nom du groupe ou le mot cle recherche
	 * @return Objet de type Collection de Group trouve 
	 */

	Collection<Group> findGroupsByName(String groupName);
	
	/**
	 * la methode qui retourne un lot de 10 groupes
	 * @param offset le decalage pour satisfaire la pagination pour un nombre de groupe important
	 * @param maxRows le maximum des lignes retournees par requete
	 * @return une Map des identifiants des groupes (le lot du 10) a leurs noms
	 */
	
	public Map<Long, String> getLimitGroupNames(Integer offset,
			Integer maxRows);
	
	/**
	 * la methode qui retourne tous les noms des groupes sans limite
	 * @deprecated
	 * @return une Map de tous les identifiants des groupes a leurs noms
	 */
	
	Map<Long, String> getGroupNames();
	
	/**
	 * la methode qui compte tous les groupes
	 * @return un entier de type Integer indiquant le nombre total des groupes
	 */
	
	public Integer countGroups();
	
	/**
	 * la methode qui supprime un groupe par son identifiant
	 * @param groupId un Long qui represente l'identifiant du groupe a supprimer
	 * @return un entier informant le nombre de ligne supprimee(s) apres la requete
	 */
	
	int removeOneGroup(Long groupId);
	
	/**
	 * la methode qui supprime un groupe par son identifiant
	 * @return un entier informant le nombre de ligne supprimees apres la requete
	 */
	
	int removeAllGroups();

}
