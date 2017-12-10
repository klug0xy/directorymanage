/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.business.IPersonService;
import fr.amu.directorymanage.dao.IDirectoryManager;

/**
 * 
 * Classe qui teste la classe JdbcDirectoryManagerPerson
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
   /*TransactionalTestExecutionListener.class,*/
    DbUnitTestExecutionListener.class })

@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
public class JdbcDirectoryManagerPersonTest {
	
	@Autowired
	IDirectoryManager directoryManager;
	
	@Autowired
	IPersonService personService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	@DatabaseTearDown(value = "/directoryManagerDbTest.xml")
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
	value = "/directoryManagerDbTest.xml")
	public void setUp() throws Exception {
	}

	@After
	
	public void tearDown() throws Exception {
	}

	//All test methods for Person
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
	value = "/setupDataUpdatePerson.xml")
	public void testSavePersonAutoInsertedRowsCount(){
		
		Person person = new Person();
		String expectedFirstName = "Houssem";
		String expectedLastName = "Mjid";
		String expectedMail = "mjid1@gmail.com";
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
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
	value = "/setupDataUpdatePerson.xml")
	public void testSavePersonAuto(){
		
		Person person = new Person();
		Collection<Person> personsByName;
		String expectedFirstName = "Houssem";
		String expectedLastName = "Mjid";
		String expectedMail = "mjid@gmail.com";
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
		Integer expectedSize = 2;
		Integer actualSize = personsByName.size();
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
	@DatabaseSetup("/setupDataUpdatePerson.xml")
	public void testGetPersonByEmail(){
		Person actualPerson = new Person();
		String expectedPersonLastName = "Risch";
		String expectedPersonMail = "risch@value.fr";
		actualPerson = directoryManager.getPersonByEmail(expectedPersonMail);
		String actualPersonLastName = actualPerson.getLastName();
		assertEquals(expectedPersonLastName, actualPersonLastName);
		
	}
	
	@Test
	@DatabaseSetup("/setupDataUpdatePerson.xml")
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
	@DatabaseSetup("/setupDataUpdatePerson.xml")
	public void testFindPersonByIdReturnName(){
		Person person = new Person();
		Long personId = new Long(3);
		String expectedPersonName = "Adem";
		person = directoryManager.findPersonById(personId);
		String actualPersonName = person.getFirstName();
		assertEquals(expectedPersonName, actualPersonName);
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, 
		value = "/testFindLimitPersonsSize.xml")
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
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, 
	value = "/testFindLimitPersonsSize.xml")
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
	@DatabaseSetup("/setupLimitGroupPersonsNames.xml")
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
	@DatabaseSetup(value = "/setupDataUpdatePerson.xml")
	@ExpectedDatabase(value = "/expectedDataUpdatePerson.xml", table="Groupe")
	public void testUpdatePersonSize(){
		Person expectedPerson = new Person();
		Long expectedPersonId = new Long(5);
		String expectedFirstName = "Philip";
		String expectedLastName = "Risch";
		String expectedMail = "risch@risch.fr"; //updated value
		String expectedWebsite = "www.risch.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-09-30");
		String expectedPassword = "$2a$10$H8TN7LEs2U1MmhHUfUMfV.ttRCiEtXG/4KU/"
				+ "cNnV3rsMJLyOxDuau";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(2);
		expectedPerson.setId(expectedPersonId);
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
	@DatabaseSetup(value = "/setupDataUpdatePerson.xml")
	@ExpectedDatabase(value = "/expectedDataUpdatePerson.xml", table="Groupe")
	public void testUpdatePersonMail(){
		Person expectedPerson = new Person();
		Long expectedPersonId = new Long(5);
		String expectedFirstName = "Philip";
		String expectedLastName = "Risch";
		String expectedMail = "risch@risch.fr"; //updated value
		String expectedWebsite = "www.risch.fr";
		Date expectedBirthday = java.sql.Date.valueOf("1993-09-30");
		String expectedPassword = "$2a$10$H8TN7LEs2U1MmhHUfUMfV.ttRCiEtXG/4KU/"
				+ "cNnV3rsMJLyOxDuau";
		//il faut que le groupe est deja insere
		Long expectedGroupId = new Long(2);
		expectedPerson.setId(expectedPersonId);
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
	@DatabaseSetup(value = "/setupDataUpdatePerson.xml")
	@ExpectedDatabase(value = "/expectedDataUpdatePerson.xml", table="Groupe")
	public void testUpdatePersonPasswordByIdSize(){
		Integer expectedUpdatedRows = 1;
		Long personId = new Long(4);
		String password = "trossimple";
		Integer actualUpdatedRows = directoryManager.
				updatePersonPasswordById(personId, password);
		assertEquals(expectedUpdatedRows, actualUpdatedRows);
	}
	
	@Ignore
	@DatabaseSetup(value = "/setupDataUpdatePerson.xml")
	@ExpectedDatabase(value = "/expectedDataUpdatePersonPassword.xml", table="Groupe")
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
	@DatabaseSetup("/setupDataUpdatePerson.xml")
	public void testCountPersons() {
		Integer expectedPersonsCount = 5; 
		Integer actualPersonsCount = directoryManager.
				countPersons();
		assertEquals(expectedPersonsCount, actualPersonsCount);
		
	}
		
	@Test
	@DatabaseSetup("/setupDataUpdatePerson.xml")
	public void testCountGroupPersons() {
		Long groupId = new Long(1);
		Integer expectedPersonsGroupCount = 3;  
		Integer actualPersonsGroupCount = directoryManager.
				countGroupPersons(groupId);
		assertEquals(expectedPersonsGroupCount, actualPersonsGroupCount);
		
	}
	
	
	@Test/*(expected = Exception.class)*/
	@DatabaseSetup(value = "/setupRemovePerson.xml")
	@ExpectedDatabase(value = "/expectedDataRemoveOnePerson.xml", table="Groupe")
	//@DatabaseTearDown(value = "/directoryManagerDbTest.xml")
	public void testRemoveOnePerson(){
		Long expectedPersonId = new Long(4);
		//String personMail = "namassi@gmail.fr";
		directoryManager.removeOnePerson(expectedPersonId);
		//directoryManager.getPersonByEmail(personMail);
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
	@DatabaseSetup(value = "/setupRemovePerson.xml")
	public void testRemoveAllPersonsRemovedRows(){
		
		Integer expectedPersonsRemovedCount = 5;
		
		Integer actualPersonsRemoved = directoryManager.removeAllPersons();
		
		assertEquals(expectedPersonsRemovedCount, actualPersonsRemoved);
	}
}