package fr.amu.directorymanage.business;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.beans.User;

public interface IDirectoryManager {

	// créer un utilisateur anonyme
	User newUser();

	// chercher un groupe
	Group findGroup(User user, Long groupId);

	// chercher les personnes d'un groupe
	Collection<Person> findAllGroupPersons(User user, Long groupId);

	// chercher tous les personnes de tous les groupes
	Collection<Person> findAllPersons();

	// chercher une personne
	Person findPerson(User user, Long personId);

	// identifier un utilisateur
	boolean login(User user, Long personId, String password);

	// oublier l'utilisateur
	void logout(User user);
	
	//enregistrer un groupe
	int saveGroup(Group g);

	// enregistrer une personne
	int savePerson(User user, Person p);
	
	int updatePerson(Person person);
	//supprimer une personne
	int removeOnePerson(Long personId);
	
	int removeOneGroup(Long groupId);
	
	Person getPersonByEmail(String personEmail);
	
	String getEmailByPersonId(Long personId);
	
	Map<Long, String> getGroupNames();
	
	String getGroupName(Long groupId);

	int removeAllPersons();

	int removeAllPersonsGroup(Long groupId);

	int removeAllGroups();

	int savePersonAuto(Person person);

	int saveGroupAuto(Group group);
	
	int updateGroup(Group group);

}