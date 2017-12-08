package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.business.IDirectoryManager;
import fr.amu.directorymanage.business.IPersonService;

public class JdbcDirectoryManagerPersonTest {
	
	@Autowired
	IDirectoryManager directoryManager;
	
	@Autowired
	IPersonService personService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//All test methods for Person
	
	@Test
	public void testSavePersonAutoInsertedRowsCount(){
		
		Person person = new Person();
		String expectedFirstName = "Houssem";
		String expectedLastName = "Mjid";
		String expectedMail = "mjidhoussem@gmail.com";
		String expectedWebsite = "www.mjid.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-07-30");
		String expectedPassword = "$2a$10$W9oRWeFmOT0bByL5fmAceucetmEYFg2yzq3e"
				+ "50mcu.CO7rUDb/poG";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(1);
		person.setFirstName(expectedFirstName);
		person.setLastName(expectedLastName);
		person.setMail(expectedMail);
		person.setWebsite(expectedWebsite);
		person.setBirthday(expectedBirthday);
		person.setPassword(expectedPassword);
		person.setGroupId(expectedGroupId);
		int expectedInsertedRows = 1;
		int actualInsertedRows = directoryManager.savePersonAuto(person);
		assertEquals(expectedInsertedRows, actualInsertedRows);
	}
	
	@Test
	public void testSavePersonAuto(){
		
		Person person = new Person();
		Collection<Person> personsByName;
		String expectedFirstName = "Houssem";
		String expectedLastName = "Mjid";
		String expectedMail = "mjidhoussem@gmail.com";
		String expectedWebsite = "www.mjid.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-07-30");
		String expectedPassword = "$2a$10$W9oRWeFmOT0bByL5fmAceucetmEYFg2yzq3e"
				+ "50mcu.CO7rUDb/poG";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(1);
		person.setFirstName(expectedFirstName);
		person.setLastName(expectedLastName);
		person.setMail(expectedMail);
		person.setWebsite(expectedWebsite);
		person.setBirthday(expectedBirthday);
		person.setPassword(expectedPassword);
		person.setGroupId(expectedGroupId);
		directoryManager.savePersonAuto(person);
		personsByName = directoryManager.findPersonByName(expectedLastName);
		int expectedSize = 1;
		int actualSize = personsByName.size();
		assertEquals(expectedSize, actualSize);
		for (Person actualPerson : personsByName){
			String actualFirstName = actualPerson.getFirstName();
			String actualLastName = actualPerson.getLastName();
			assertEquals(expectedFirstName, actualFirstName);
			assertEquals(expectedLastName, actualLastName);
		}
	}
	
	@Test(expected = Exception.class)
	public void testSavePersonAutoEmailExist(){
		
		Person person = new Person();
		String expectedFirstName = "Houssem";
		String expectedLastName = "Mjid";
		String expectedMail = "mjidhoussem@gmail.com";
		String expectedWebsite = "www.mjid.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-07-30");
		String expectedPassword = "$2a$10$W9oRWeFmOT0bByL5fmAceucetmEYFg2yzq3e"
				+ "50mcu.CO7rUDb/poG";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(1);
		person.setFirstName(expectedFirstName);
		person.setLastName(expectedLastName);
		person.setMail(expectedMail);
		person.setWebsite(expectedWebsite);
		person.setBirthday(expectedBirthday);
		person.setPassword(expectedPassword);
		person.setGroupId(expectedGroupId);
		directoryManager.savePersonAuto(person);
	}
	
	@Test
	public void testGetPersonByEmail(){
		Person actualPerson = new Person();
		String expectedPersonLastName = "Risch";
		String expectedPersonMail = "risch@value.fr";
		actualPerson = directoryManager.getPersonByEmail(expectedPersonMail);
		String actualPersonLastName = actualPerson.getLastName();
		assertEquals(expectedPersonLastName, actualPersonLastName);
		
	}
	
	@Test
	public void testFindPersonByNameReturnedSize(){
		Collection<Person> persons;
		String expectedPersonName = "Mjid"; 
		persons = directoryManager.findPersonByName(expectedPersonName);
		Integer expectedSize = 1;	
		Integer actualSize = persons.size();
		assertEquals(expectedSize, actualSize);
	}
		
	@Test
	public void testFindPersonByNameReturnedPerson(){
		Collection<Person> persons;
		String expectedPersonName = "Mjid";
		String actualPersonName = ""; 
		persons = directoryManager.findPersonByName(expectedPersonName);
			
		for (Person person : persons) {
			actualPersonName = person.getLastName();
		}
		assertEquals(expectedPersonName, actualPersonName);
	}
	
	@Test
	public void testFindPersonById(){
		Person person = new Person();
		Long expectedPersonId = new Long(3);
		
		person = directoryManager.findPersonById(expectedPersonId);
		Long actualPersonId = person.getId();
		assertEquals(expectedPersonId, actualPersonId);
	}
	
	@Test
	public void testFindPersonByIdReturnName(){
		Person person = new Person();
		Long personId = new Long(3);
		String expectedPersonName = "Adem";
		person = directoryManager.findPersonById(personId);
		String actualPersonName = person.getFirstName();
		assertEquals(expectedPersonName, actualPersonName);
	}
	
	@Test
	public void testFindLimitPersonsSize(){
		Collection<Person> limitPersons;
		Integer offset = 0;
		Integer maxRows = 10;
		Integer expectedSize = 10;
		limitPersons = directoryManager.findLimitPersons(offset, maxRows);
		Integer actualSize = limitPersons.size();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void testFindLimitGroupPersonsByGroupNameSize() {
		Collection<Person> limitGroupPersons; 
		String expectedGroupName = "isl";
		Long expectedGroupId = new Long (1);
		Integer offset = 0;
		Integer maxRows = 10;
		Integer expectedSize = 10;
		limitGroupPersons = directoryManager.findLimitGroupPersonsByGroupName
				(expectedGroupName, expectedGroupId, offset, maxRows);
		Integer actualSize = limitGroupPersons.size();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void testFindLimitGroupPersonsByGroupNameReturnNames() {
		Collection<Person> limitGroupPersons; 
		String expectedGroupName = "isl";
		Long expectedGroupId = new Long (1);
		Integer offset = 0;
		Integer maxRows = 10;
		String[] expectedPersons = {"Houssem_Mjid", "Hichem_Bouhlel", 
				"Adem_Bekouche"};
		limitGroupPersons = directoryManager.findLimitGroupPersonsByGroupName
				(expectedGroupName, expectedGroupId, offset, maxRows);
		Integer idx = 0;
		for (Person actualPerson : limitGroupPersons){
			String actualFullName = actualPerson.getFirstName() + "_" 
					+ actualPerson.getLastName();
			assertTrue(expectedPersons[idx].equals(actualFullName));
			idx++;
		}
	}
	
	@Test
	public void testUpdatePersonSize(){
		Person expectedPerson = new Person();
		String expectedFirstName = "Philip";
		String expectedLastName = "Risch";
		expectedPerson.setFirstName(expectedFirstName);
		expectedPerson.setLastName(expectedLastName);
		String expectedMail = "risch@risch.fr"; //updated value
		String expectedWebsite = "www.risch.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-09-30");
		String expectedPassword = "$2a$10$H8TN7LEs2U1MmhHUfUMfV.ttRCiEtXG/4KU/"
				+ "cNnV3rsMJLyOxDuau";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(2);
		expectedPerson.setFirstName(expectedFirstName);
		expectedPerson.setLastName(expectedLastName);
		expectedPerson.setMail(expectedMail);
		expectedPerson.setWebsite(expectedWebsite);
		expectedPerson.setBirthday(expectedBirthday);
		expectedPerson.setPassword(expectedPassword);
		expectedPerson.setGroupId(expectedGroupId);
		Integer expectedUpdatedRows = 1;
		Integer actualUpdatedRows = directoryManager.
				updatePerson(expectedPerson);
		assertEquals(expectedUpdatedRows, actualUpdatedRows);
	}
	
	@Test
	public void testUpdatePersonMail(){
		Person expectedPerson = new Person();
		String expectedFirstName = "Philip";
		String expectedLastName = "Risch";
		String expectedMail = "risch@risch.fr"; //updated value
		String expectedWebsite = "www.risch.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-09-30");
		String expectedPassword = "$2a$10$H8TN7LEs2U1MmhHUfUMfV.ttRCiEtXG/4KU/"
				+ "cNnV3rsMJLyOxDuau";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(2);
		expectedPerson.setFirstName(expectedFirstName);
		expectedPerson.setLastName(expectedLastName);
		expectedPerson.setMail(expectedMail);
		expectedPerson.setWebsite(expectedWebsite);
		expectedPerson.setBirthday(expectedBirthday);
		expectedPerson.setPassword(expectedPassword);
		expectedPerson.setGroupId(expectedGroupId);
		
		directoryManager.updatePerson(expectedPerson);
		Person actualPerson =  directoryManager.getPersonByEmail(expectedMail);
		String actualPersonMail = actualPerson.getMail();
		assertEquals(expectedMail, actualPersonMail);
	}
	
	@Test
	public void testUpdatePersonPasswordByIdSize(){
		Integer expectedUpdatedRows = 1;
		Long personId = new Long(4);
		String password = "trossimple";
		Integer actualUpdatedRows = directoryManager.
				updatePersonPasswordById(personId, password);
		assertEquals(expectedUpdatedRows, actualUpdatedRows);
	}
	
	@Test
	public void testUpdatePersonPasswordById(){
		Person actualPerson = new Person();
		String expectedPersonMail = "namassi@gmail.fr";
		Long expectedPersonId = new Long(4);
		String expectedPersonPassword = "trossimple";
		String expectedPersonHashedPassword = personService.hashPassword
				(expectedPersonPassword);
		directoryManager.
				updatePersonPasswordById(expectedPersonId, 
						expectedPersonPassword);
		actualPerson = directoryManager.getPersonByEmail(expectedPersonMail);
		String actualPersonHashedPassword = actualPerson.getPassword();
		assertEquals(expectedPersonHashedPassword, actualPersonHashedPassword);
	}
	
	@Test
	public void testCountPersons() {
		Integer expectedPersonsCount = 5; 
		Integer actualPersonsCount = directoryManager.
				countPersons();
		assertEquals(expectedPersonsCount, actualPersonsCount);
		
	}
		
	@Test
	public void testCountGroupPersons() {
		Long groupId = new Long(1);
		Integer expectedPersonsGroupCount = 3;  
		Integer actualPersonsGroupCount = directoryManager.
				countGroupPersons(groupId);
		assertEquals(expectedPersonsGroupCount, actualPersonsGroupCount);
		
	}
	
	@Test(expected = Exception.class)
	public void testRemoveOnePerson(){
		Long expectedPersonId = new Long(4);
		String personMail = "namassi@gmail.fr";
		directoryManager.removeOnePerson(expectedPersonId);
		directoryManager.getPersonByEmail(personMail);
	}
	
	@Test
	public void testRemoveAllPersonsGroup(){
		String expectedGroupName = "M2-FSI";
		Long expectedGroupId = new Long(2);
		Integer offset = 0;
		Integer maxRows = 10;
		Integer expectedGroupPersonsCount = 0;
		Collection<Person> actualGroupPersons;
		directoryManager.removeAllPersonsGroup(expectedGroupId);
		actualGroupPersons = directoryManager.
				findLimitGroupPersonsByGroupName(expectedGroupName, 
						expectedGroupId, offset, maxRows);
		Integer actualGroupPersonsCount = actualGroupPersons.size();
		assertEquals(expectedGroupPersonsCount, actualGroupPersonsCount);
	}
	
	@Test
	public void testRemoveAllPersonsRemovedRows(){
		
		Integer expectedPersonsRemovedCount = 5;
		
		Integer actualPersonsRemoved = directoryManager.removeAllPersons();
		
		assertEquals(expectedPersonsRemovedCount, actualPersonsRemoved);
	}
}