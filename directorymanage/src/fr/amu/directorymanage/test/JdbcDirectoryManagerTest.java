package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

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
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.business.IDirectoryManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
   TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup("/resources/directoryManagerDbTest.xml")
public class JdbcDirectoryManagerTest {
	
	@Autowired
	IDirectoryManager directoryManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	@DatabaseTearDown("/resources/directoryManagerDbTest.xml")
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	public void testFindLimitGroupPersonsByGroupName() {
		Collection<Person> persons; 
		String expectedGroupName = "isl";
		Long expectedGroupId = new Long (1);
		Integer offset = 0;
		Integer maxRows = 10;
		
		persons = directoryManager.findLimitGroupPersonsByGroupName
				(expectedGroupName, expectedGroupId, offset, maxRows);
		for (Person person : persons){
			System.out.println(person.getFirstName());
		}
	}
	
	@Test
	public void testFindPersonByNameReturnedSize(){
		Collection<Person> persons;
		String expectedPersonName = "Mjid";
		int expectedSize = 1 ;
		persons = directoryManager.findPersonByName(expectedPersonName);
		int actualSize = persons.size();
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

}
