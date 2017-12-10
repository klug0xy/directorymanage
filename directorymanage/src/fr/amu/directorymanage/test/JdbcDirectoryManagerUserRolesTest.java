/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.dao.IDirectoryManager;

/**
 * 
 * Classe qui teste la classe JdbcDirectoryManagerUserRoles
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class JdbcDirectoryManagerUserRolesTest {
	
	@Autowired
	IDirectoryManager directoryManager;

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

	@Test
	public void testUpdateUsernameUserRolesUpdatedRows() {
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
		Integer expectedUpdatedRows = 1;
		Integer actualUpdatedRows = directoryManager.updateUsernameUserRoles
				(expectedPerson);
		assertEquals(expectedUpdatedRows, actualUpdatedRows);
	}
	
	@Test
	public void testUpdateUsernameUserRolesUpdatedUsername() {
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
		
		directoryManager.updateUsernameUserRoles
				(expectedPerson);
		String actualUsername = directoryManager.
				findUsernameUserRolesByUsername(expectedMail);
		assertEquals(expectedMail, actualUsername);
	}

}
