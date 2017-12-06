package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.amu.directorymanage.business.PersonService;

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
