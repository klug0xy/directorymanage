package fr.amu.directorymanage.test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.exceptions.DAOException;
import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.interfaces.PersonDao;
import fr.amu.directorymanage.jdbc.JdbcTools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")	
public class PersonJdbcTest {
	
	Person p = new Person();
	
	@Autowired
    private PersonDao personDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		JdbcTools jdbc = new JdbcTools();
		jdbc.bds.close();
	}

	@Before
	public void setUp() throws Exception {
//		JdbcTools jdbc = new JdbcTools();
//		jdbc.init();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testSaveGroup() {
		Group g = new Group();
		long l = 45855;
		g.setId(l);
		g.setName("les fous");
		personDao.saveGroup(g);
	}
	
	@Test
	public void testFindAllGroups() throws ParseException, ClassNotFoundException {
		Collection<Group> clg = new ArrayList<Group>();
		clg = personDao.findAllGroups();
		assertNotNull(clg);
	}
	
	@Test
	public void testFindPerson() {
		p = personDao.findPerson(55884);
		assertNotNull(p);
	}
	
	@Test
	public void testFindNotExistPerson() {
		p = personDao.findPerson(1);
		assertEquals(null, p.getFirstName());
	}
	
	@Test
	public void testFindAllPersons() {
		Collection<Person> clp = new ArrayList<Person>();
		long l = 45855;
		clp = personDao.findAllPersons(l);
		assertNotNull(clp);
	}
	
	@Test
	public void testFindAllPersonsNotExists() {
		Collection<Person> clp = new ArrayList<Person>();
		long l = 445;
		clp = personDao.findAllPersons(l);
		assertEquals(0, clp.size());
	}

	@Test
	public void testSavePerson() throws ParseException, ClassNotFoundException {
		
		p.setId(new Long(55884));
		p.setFirstName("test");
		p.setLastName("testii");
		String strDate = "2011-12-31 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        java.util.Date date = sdf.parse(strDate);
        java.sql.Date sqlDate = new Date(date.getTime());
		p.setBirthday(sqlDate);
		p.setMail("dej@mail.fr");
		p.setGroupId(new Long(45855));
		personDao.savePerson(p);
	}
	
	@Test(expected=DAOException.class)
	public void testSaveInvalidNamePerson() throws ParseException, ClassNotFoundException {
		
		p.setId(new Long(55884));
		p.setFirstName("");
		p.setLastName("testii");
		String strDate = "2011-12-31 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        java.util.Date date = sdf.parse(strDate);
        java.sql.Date sqlDate = new Date(date.getTime());
		p.setBirthday(sqlDate);
		p.setMail("dej@mail.fr");
		p.setGroupId(new Long(45855));
		personDao.savePerson(p);
	}
	
	@Test(expected=DAOException.class)
	public void testSaveInvalidMailPerson() throws ParseException, ClassNotFoundException {
		
		p.setId(new Long(55884));
		p.setFirstName("Houssem");
		p.setLastName("testii");
		String strDate = "2011-12-31 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        java.util.Date date = sdf.parse(strDate);
        java.sql.Date sqlDate = new Date(date.getTime());
		p.setBirthday(sqlDate);
		p.setMail("dejmail.fr");
		p.setGroupId(new Long(45855));
		personDao.savePerson(p);
	}

}
