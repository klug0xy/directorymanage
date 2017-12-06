package fr.amu.directorymanage.business;

import java.util.Collection;
import java.util.Map;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;

public interface IDirectoryManager {

	// chercher un groupe
	Group findGroup(Long groupId);

	Collection<Group> findGroupsByName(String groupName);

	// chercher les personnes d'un groupe
	Collection<Person> findAllGroupPersons(Long groupId);

	// chercher tous les personnes de tous les groupes
	Collection<Person> findAllPersons();

	// chercher une personne
	Person findPerson(Long personId);

	Collection<Person> findPersonByName(String personName);

	// enregistrer un groupe
	int saveGroup(Group g);

	// enregistrer une personne
	int savePerson(Person p);

	int updatePerson(Person person);
	
	int updateUsernameUserRoles(Person person);
	// supprimer une personne
	int removeOnePerson(Long personId);

	int removeOneGroup(Long groupId);

	Person getPersonByEmail(String personEmail);

	String getEmailByPersonId(Long personId);

	Map<Long, String> getGroupNames();

	int removeAllPersons();

	int removeAllPersonsGroup(Long groupId);

	int removeAllGroups();

	int savePersonAuto(Person person);

	int saveGroupAuto(Group group);

	int updateGroup(Group group);

	public int updatePersonPasswordById(Long personId, String password);

	Collection<Person> findLimitPersons(Integer offset, Integer maxRows);

	public Map<Long, String> getLimitGroupNames(Integer offset,
			Integer maxRows);

	public Integer countPersons();

	public Integer countGroups();

	public Integer countGroupPersons(Long groupId);

	Collection<Person> findLimitGroupPersons(Long groupId, Integer offset,
			Integer maxRows);
	
	Collection<Person> findLimitGroupPersonsByGroupName(String groupName,
			Long groupId, Integer offset, Integer maxRows);

}