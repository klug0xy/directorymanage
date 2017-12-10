/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.dao.IDirectoryManager;
import fr.amu.directorymanage.dao.IDirectoryManagerGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
   TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })

/**
 * 
 * Classe qui teste la classe JdbcDirectoryManagerGroup
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
public class JdbcDirectoryManagerGroupTest {
	
//	@Autowired
//	IDirectoryManager directoryManager;
	
	@Autowired
	IDirectoryManagerGroup directoryManagerGroup;

	@BeforeClass
	
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	@DatabaseTearDown("/directoryManagerDbTest.xml")
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	//@DatabaseSetup("/resources/directoryManagerDbTest.xml")
	//@ExpectedDatabase("/resources/directoryManagerDbTest.xml")
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSaveGroupAuto(){
		Group expectedGroup = new Group();
		Collection<Group> returnedGroups;
		String expectedGroupName = "M2-GL";
		String actualGroupName = "";
		expectedGroup.setName(expectedGroupName);
		directoryManagerGroup.saveGroupAuto(expectedGroup);
		returnedGroups = directoryManagerGroup.findGroupsByName(expectedGroupName);
		for (Group actualGroup : returnedGroups) {
			actualGroupName = actualGroup.getName();
		}
		assertEquals(expectedGroupName, actualGroupName);
	}
	
	@Test
	public void testSaveGroupAutoInsertedRowsCount(){
		Group expectedGroup = new Group();
		expectedGroup.setName("M2-IF");
		int expectedInsertedGroupCount = 1;
		int actualInsertedGroupCount = directoryManagerGroup.saveGroupAuto(expectedGroup);
		assertEquals(expectedInsertedGroupCount, actualInsertedGroupCount);
	}
	
	@Test
	public void testFindGroupReturnedId() {
		Long expectedGroupId = new Long(1);
		Group expectedGroup = directoryManagerGroup.findGroup(expectedGroupId);
		Long actualGroupId = expectedGroup.getId();
		assertEquals(actualGroupId, expectedGroupId);
	}
	
	@Test
	public void testFindGroupReturnedName() {
		Long expectedGroupId = new Long(1);
		String expectedGroupName = "M2-ISL";
		Group expectedGroup = directoryManagerGroup.findGroup(expectedGroupId);
		String actualGroupName = expectedGroup.getName();
		assertEquals(expectedGroupName, actualGroupName);
		
	}
	
	@Test
	public void testFindGroupsByNameReturnedSize(){
		
		Collection<Group> returnedGroups;
		String expectedGroupName = "M2-ISL";
		int expectedReturnedSize = 1 ;
		int actualReturnedSize = 0;
		returnedGroups = directoryManagerGroup.findGroupsByName(expectedGroupName);
		actualReturnedSize = returnedGroups.size();
		assertEquals(expectedReturnedSize, actualReturnedSize);
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
    value = "directoryManagerDbTest.xml")
	public void testFindGroupsByNameReturnedName(){
		
		Collection<Group> returnedGroups;
		String expectedGroupName = "M2-ISL";
		String actualGroupName = "";
		returnedGroups = directoryManagerGroup.findGroupsByName(expectedGroupName);
		for (Group group : returnedGroups) {
			actualGroupName = group.getName();
		}
		assertEquals(expectedGroupName, actualGroupName);
	}
	
	//test de la methode FindGroupsByName le nombre de groupe retourné,
	//un group attendu
	@Test
	public void testFindGroupsByNameReturnedSizeOne(){
		String expectedGroupsName = "M2-ISL";
		Collection<Group> actualGroups;
		int expectedSize = 1;
		int actualSize = 0 ;
		actualGroups = directoryManagerGroup.findGroupsByName(expectedGroupsName);
		actualSize = actualGroups.size();
		assertEquals(expectedSize, actualSize);
	}
	
	//test de la methode FindGroupsByName le nombre de groupe retourné,
	// quatre group attendu
	@Test
	public void testFindGroupsByNamesReturnedSizeFour(){
		String expectedGroupsName = "M2";
		Collection<Group> actualGroups;
		int expectedSize = 4;
		int actualSize = 0 ;
		actualGroups = directoryManagerGroup.findGroupsByName(expectedGroupsName);
		actualSize = actualGroups.size();
		assertEquals(expectedSize, actualSize);
	}
	
	//test de la methode FindGroupsByName pour un groupe retournée
	@Test
	public void testFindGroupsByNameOne(){
		String expectedGroupsName = "M2-ISL";
		Long expectedGroupId = new Long(1);
		String actualGroupsName = "";
		Long actualGroupsId = new Long(1); 
		Group expectedGroup = new Group();
		Collection<Group> expectedGroups = new ArrayList<Group>();
		Collection<Group> actualGroups = new ArrayList<Group>();
		expectedGroup.setId(expectedGroupId);
		expectedGroup.setName(expectedGroupsName);
		expectedGroups.add(expectedGroup);
		actualGroups = directoryManagerGroup.findGroupsByName(expectedGroupsName);
		for (Group actualGroup : actualGroups) {
			actualGroupsId = actualGroup.getId();
			actualGroupsName = actualGroup.getName();
			assertEquals(expectedGroupId, actualGroupsId);
			assertEquals(expectedGroupsName, actualGroupsName);
		}
	}
	
	//test de la methode FindGroupsByName pour 4 groupe retournee avec un mot 
	//cle "M2"
	@Test
	public void testFindGroupsByNameFour(){
		String[] expectedGroupsName = { "M2-ISL", "M2-FSI", "M2-ID", "M2-SIR" };
		Long[] expectedGroupsIds = { new Long(1), new Long(2), new Long(3),
				new Long(4)};
		String searchedName = "M2";
		Long actualGroupId = new Long(0);
		String actualGroupName = "";
		Collection<Group> actualGroups = new ArrayList<Group>();
		actualGroups = directoryManagerGroup.findGroupsByName(searchedName);
		int idx = 0;
		for (Group actualGroup : actualGroups) {
			actualGroupId = actualGroup.getId();
			actualGroupName = actualGroup.getName();
			assertEquals(expectedGroupsIds[idx], actualGroupId);
			assertEquals(expectedGroupsName[idx], actualGroupName);
			idx++;
		}
	}
	
	//test de la methode findGroupsByName avec un mot cle "",tout les groupes 
	// sont retournes
	@Test
	public void testFindGroupsByNameNothing(){
		String[] expectedGroupsName = { "M2-ISL", "M2-FSI", "M2-ID", "M2-SIR" };
		String searchedName = "";
		String actualGroupName = "";
		Collection<Group> actualGroups = new ArrayList<Group>();
		actualGroups = directoryManagerGroup.findGroupsByName(searchedName);
		int idx = 0;
		for (Group actualGroup : actualGroups) {
			actualGroupName = actualGroup.getName();
			assertEquals(expectedGroupsName[idx], actualGroupName);
			idx++;
		}
	}
	
	//test de la methode FindGroupsByName avec un mot cle n'existe pas,
	@Test
	public void testFindGroupsByNameNotExist(){
		int expectedSize = 0;
		String searchedName = "*";
		Collection<Group> actualGroups = new ArrayList<Group>();
		actualGroups = directoryManagerGroup.findGroupsByName(searchedName);
		int actuaulSize = actualGroups.size();
		assertEquals(expectedSize, actuaulSize);
	}
	
	//a faire
	@Test
	@DatabaseSetup(value = "/directoryManagerDbTest.xml")
	public void testGetLimitGroupNamesReturnedRows(){
		Map<Long, String> groupNames = new LinkedHashMap<>();
		Integer expectedReturnedRows = 10;
		int offset = 10;
		int maxRows = 10;
		groupNames = directoryManagerGroup.getLimitGroupNames(offset, maxRows);
		Integer actualReturnedRows = groupNames.size();
		assertEquals(expectedReturnedRows, actualReturnedRows);
	}
	
	@Test
	public void testGetGroupNames(){
		Map<Long, String> expectedGroupNames = new LinkedHashMap<>();
		Map<Long, String> actualGroupNames = new LinkedHashMap<>();
		expectedGroupNames.put(new Long(1), "M2-ISL");
		expectedGroupNames.put(new Long(2), "M2-FSI");
		expectedGroupNames.put(new Long(3), "M2-ID");
		expectedGroupNames.put(new Long(4), "M2-SIR");
		actualGroupNames = directoryManagerGroup.getGroupNames();
		//assertTrue(actualGroupNames.equals(expectedGroupNames));
		Long key = new Long(1);
		for (Map.Entry<Long, String> actualGroup : actualGroupNames.entrySet()){
			assertTrue(actualGroup.getValue() == expectedGroupNames.get(key));
			key++;
		}
	}
	//test de la methode countGroups
	@Test
	public void testCountGroups(){
		Integer expectedGroupCounts = 4;
		Integer actualGroupCounts = 0;
		actualGroupCounts = directoryManagerGroup.countGroups();
		assertEquals(expectedGroupCounts, actualGroupCounts);
	}
	
	//test de la methode updateGroup, le nombre de ligne modifie
	@Test
	public void testUpdateGroupChangedRows(){
		String changedValue = "M2-GL";
		Long groupId = new Long(4);
		Group changedGroup = new Group();
		changedGroup.setId(groupId);
		changedGroup.setName(changedValue);
		int expectedChangedRows = 1;
		int actualChangedRows = 0;
		actualChangedRows = directoryManagerGroup.updateGroup(changedGroup);
		assertEquals(expectedChangedRows, actualChangedRows);
	}
		
	//test de la methode updateGroup, le nombre de ligne modifie
	@Test
	public void testUpdateGroup(){
		String changedGroupName = "M2-GL";
		Long groupId = new Long(4);
		Group changedGroup = new Group();
		Collection<Group> actualGroups;
		changedGroup.setId(groupId);
		changedGroup.setName(changedGroupName);
		directoryManagerGroup.updateGroup(changedGroup);
		actualGroups = directoryManagerGroup.findGroupsByName(changedGroupName);
		for (Group actualGroup : actualGroups){
			String actualGroupName = actualGroup.getName();
			assertEquals(changedGroupName, actualGroupName);
		}				
	}
	
	@Test
	public void testRemoveAllGroups(){

		
	}
	
	@Test
	public void testRemoveOneGroup(){

		
	}
}
