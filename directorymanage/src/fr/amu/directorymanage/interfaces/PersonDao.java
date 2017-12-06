package fr.amu.directorymanage.interfaces;

import java.util.Collection;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.exceptions.DAOException;

public interface PersonDao {

	   // récupérer les groupes
	   Collection<Group> findAllGroups() throws DAOException;

	   // récupérer les personnes
	   Collection<Person> findAllPersons(long groupId) throws DAOException;

	   // lire une personne
	   Person findPerson(long id) throws DAOException;

	   // modification ou ajout d'une nouvelle personne
	   void savePerson(Person p) throws DAOException;

	   // modification ou ajout d'une nouvelle personne
	   void saveGroup(Group g) throws DAOException;
}