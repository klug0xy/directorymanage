/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

import java.util.Collection;

import fr.amu.directorymanage.beans.Person;

/**
 * 
 * Interface de la couche DAO definit toutes les methodes d'acces 
 * a la table Person
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public interface IDirectoryManagerPerson {
	
	/**
	 * la methode qui sert a inserer un objet de type Person avec un 
	 * identifiant auto-increment 
	 * @param person l'objet de type Person a inserer
	 * @return un entier qui stocke le nombre des lignes insere apres 
	 * la requete
	 */
	int savePersonAuto(Person person);
	
	/**
	 * la methode qui sert a enregistrer un objet de type Person dans 
	 * la base de donnees
	 * @param p objet de type Person a inserer
	 * @return un entier qui stocke le nombre des lignes insere apres 
	 * la requete
	 */
	int savePerson(Person p);
	
	/**
	 * la methode qui sert a mettre a jour un objet de type Person dans 
	 * la base de donnees
	 * @param person objet de type Person a modifier
	 * @return un entier qui indique le nombre des lignes modifiees 
	 * apres la requete
	 */
	int updatePerson(Person person);
	
	/**
	 * la methode qui sert a mettre a jour le mot de passe d'une personne 
	 * par son identifiant
	 * @param personId identifiant de la personne celui qu'on veut modifier 
	 * son mot de passe 
	 * @param password le nouveau mot de passe a sauvgarder
	 * @return le nombre de ligne mis a jour apres la requete
	 */
	public int updatePersonPasswordById(Long personId, String password);
	
	/**
	 * la methode qui sert a rechercher une personne par son identifiant
	 * @param personId identifiant de la personne recherche
	 * @return l'objet de type Person representant la personne recherche
	 */
	Person findPersonById(Long personId);

	/**
	 * la methode qui retourne une (ou plusieurs) personne(s) recherchee(s) 
	 * par son nom (ou un mot cle)
	 * @param personName une variable de type String represente le nom 
	 * de la personne recherche
	 * (ou le mot cle). la methode compare la chaine mentionnee aux
	 *  nom et prenom. 
	 * @return un objet de type Collection des objet Person representant 
	 * la ou les personne(s) trouvees
	 * ou meme une Collection vide si rien n'est trouve
	 */
	Collection<Person> findPersonByName(String personName);
	
	/**
	 * la methode renvoi une personne recherche par son adresse mail 
	 * @param personEmail l'adresse de la personne recherche
	 * @return un objet de type Person representant la personne recherche
	 */
	Person getPersonByEmail(String personEmail);

	/**
	 * la methode renvoi l'email d'une personne recherche l'identifiant 
	 * de la personne
	 * @param personId l'identifiant de la personne celui qu'on veut son mail
	 * @return une chaine de caractere indiquant le mail de la personne 
	 */
	String getEmailByPersonId(Long personId);
	
	/**
	 * la methode recherche les membres (personnes) d'un groupe par 
	 * l'identifiant de ce dernier
	 * en divisant l'ensemble trouve sur des lots de 10
	 * @param groupId l'identifiant du groupe 
	 * @param offset le decalage qui sert a la pagination pour un nombre de 
	 * ligne important a retourner
	 * @param maxRows le maximum des lignes retournees par requete
	 * @return un objet de type Collection des objets Person representant
	 *  les personnes trouvees
	 */
	Collection<Person> findLimitGroupPersonsById(Long groupId, Integer offset,
			Integer maxRows);
	
	/**
	 * la methode recherche les membres (personnes) d'un groupe par 
	 * le nom de ce dernier et son identifiant en plus
	 * @param groupName le nom du groupe recherche (tape par l'utilisateur)
	 * @param groupId l'identifiant de la groupe recherche
	 * (identifie au niveau du controlleur)
	 * @param offset le decalage qui sert a la pagination pour un nombre de 
	 * ligne important a retourner
	 * @param maxRows le maximum des lignes retournees par requete
	 * @return un objet de type Collection des objets Person representant
	 *  les personnes trouvees
	 */
	Collection<Person> findLimitGroupPersonsByGroupName(String groupName,
			Long groupId, Integer offset, Integer maxRows);
	
	/**
	 * la methode qui retourne toutes les personnes par des lots
	 * @param offset le decalage qui sert a la pagination pour un nombre de 
	 * ligne important a retourner
	 * @param maxRows le maximum des lignes retournees par requete
	 * @return un objet de type Collection des objets Person representant
	 *  les personnes trouvees
	 */
	Collection<Person> findLimitPersons(Integer offset, Integer maxRows);
	
	/**
	 * la methode retourne toutes les personnes d'un groupe donnee
	 * @param groupId l'identifiant du groupe des personnes recherchees
	 * @return un objet de type Collection des objets Person representant
	 *  les personnes trouvees
	 */
	Collection<Person> findAllGroupPersonsById(Long groupId);
	
	/**
	 * la methode qui retourne toutes les personnes dans la base de donnees
	 * @return  un objet de type Collection des objets Person representant
	 *  les personnes trouvees
	 */
	Collection<Person> findAllPersons();
	
	/**
	 * la methode qui retourne le nombre des personnes d'un groupe donnee
	 * @param groupId l'identifiant du groupe des personnes 
	 * @return un objet de type Integer informant 
	 * le nombre des personnes trouvees pour le groupe donne
	 */
	public Integer countGroupPersons(Long groupId);
	
	/**
	 * la methode qui retourne le nombre de toutes les personnes existent
	 * dans la base de donnees  
	 * @return un objet de type Integer representant le nombre des personnes
	 * trouvees dans la base de donnees
	 */
	public Integer countPersons();
	
	/**
	 * la methode qui sert a supprimer une personne par son identifiant donne.
	 * @param personId l'identifiant de la personne a supprimer
	 * @return un entier indiquant le nombre des lignes supprimees.
	 */
	int removeOnePerson(Long personId);
	
	/**
	 * la methode qui supprime toutes les personnes d'un groupe 
	 * par l'identifiant de ce dernier 
	 * @param groupId l'identifiant du groupe 
	 * @return un entier indiquant le nombre de(s) ligne(s) supprimee(s)
	 */
	int removeAllPersonsGroup(Long groupId);
	
	/**
	 * la methode qui supprime toutes les personnes existent
	 * dans la base de donnes
	 * @return un entier qui stocke le nombre des lignes supprimees.
	 */
	int removeAllPersons();

}
