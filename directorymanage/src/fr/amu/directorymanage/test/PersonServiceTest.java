/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.amu.directorymanage.business.PersonService;

/**
 * 
 * Classe qui teste la classe PersonService
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class PersonServiceTest {
	
	PersonService personService = new PersonService();
	@Test
	public void testHashPassword() {
		String password = "azertyui";
		String ActualHashedPassword = personService.hashPassword(password);
		//assertTrue(ActualHashedPassword.equals(personService.hashPassword(password)));
		System.out.println(ActualHashedPassword +" "+ ActualHashedPassword.length());
	}

}
