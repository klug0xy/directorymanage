package fr.amu.directorymanage.business;

import java.sql.Types;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;

@Service
public class JdbcDirectoryManager implements IDirectoryManager {
	
	private JdbcTemplate jdbcTemplate;
	NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = 
			new NamedParameterJdbcDaoSupport();
	
	private BeanPropertyRowMapper<Person> personPropertyRowMapper = 
			new BeanPropertyRowMapper<Person>(Person.class);
	private BeanPropertyRowMapper<Group> groupPropertyRowMapper = 
			new BeanPropertyRowMapper<Group>(Group.class);
	Person person;
	Group group;
	
	@Autowired
	IPersonService personService;
	
	Collection<Person> persons;
	Collection<Group> groups;

	protected final Log logger = LogFactory.getLog(getClass());

	@PostConstruct
	public void init() {
		logger.info("Init "+this.getClass());
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcDaoSupport.setDataSource(dataSource);
		
	}
	
	@Override
	public Collection<Person> findAllGroupPersons(Long groupId) {
		String sql = "SELECT * FROM Person WHERE groupId = ?";
		
		persons = jdbcTemplate.query(sql,
				new Object[] { groupId }, personPropertyRowMapper);
		return persons;
	}
	
	@Override
	public Integer countGroupPersons(Long groupId) {
		Integer personsCount = new Integer(0);
		String sql = "SELECT count(*) FROM Person WHERE groupId = ?;";
		Object[] args = { groupId };
		int[] types = {Types.BIGINT};
		personsCount = jdbcTemplate.queryForObject(sql, args, types, Integer.class);
		return personsCount;
	}
	
	@Override
	public Collection<Person> findLimitGroupPersons(Long groupId, 
			Integer offset, Integer maxRows) {

		String sql = "SELECT * FROM Person WHERE groupId = ? LIMIT ?,?;";
		
		Object[] args = { groupId, offset, maxRows };
		int[] types = {Types.BIGINT, Types.INTEGER, Types.INTEGER};
		persons = jdbcTemplate.query(sql, args, types, personPropertyRowMapper);
		return persons;
	}

	@Override
	public Person findPerson(Long id) {
		
		String sql = "SELECT id,firstName,lastName,mail,website,birthday"
				+ ",password,groupId FROM Person WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { id }, personPropertyRowMapper);
		
		person = (Person)queryForObject;

		return person;
	}

	@Override
	public Group findGroup(Long groupId) {
		String sql = "SELECT id,name FROM Groupe WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { groupId }, groupPropertyRowMapper);
		
		group = (Group)queryForObject;

		return group;
	}
	
	@Override
	public int savePersonAuto(Person person) {
		int insertedRows = 0;
		String sql = "INSERT INTO Person"
				+ " (firstName,lastName,mail,website,birthday,password"
				+ ",groupId)"
				+ " VALUES (:firstName,:lastName,:mail,:website,:birthday"
				+ ",:password,:groupId)";
		String password = person.getPassword();
		String hashedPassword = personService.hashPassword(password);
		person.setPassword(hashedPassword);
		SqlParameterSource fileParameters = 
				new BeanPropertySqlParameterSource(person);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertedRows = namedParameterJdbcDaoSupport.
				getNamedParameterJdbcTemplate().update(sql, fileParameters, 
						keyHolder);
		 
		person.setId(keyHolder.getKey().longValue());
		return insertedRows;
	}

	@Override
	public int savePerson(Person person) {
		String sql = "INSERT INTO Person"
				+ " (id,firstName,lastName,mail,website,birthday,password,groupId)"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		int insertedRows;
		Object[] args = { person.getId(), person.getFirstName(), person.getLastName(),
				person.getMail(), person.getWebsite(), person.getBirthday(), person.getPassword(),
				person.getGroupId() };
		int[] types = {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.BIGINT};
		insertedRows = jdbcTemplate.update(sql, args, types);
		return insertedRows;
	}
	
	@Override
	public int updatePerson(Person person) {
		String sql = "UPDATE Person "
				+ "SET firstName = ?, lastName = ?, mail = ?, "
				+ "website = ?, birthday = ?, password = ?, groupId = ?"
				+ " WHERE id = ?;";
		String password = person.getPassword();
		String hashedPassword = personService.hashPassword(password);
		person.setPassword(hashedPassword);
		int modifiedRows;
		Object[] args = { person.getFirstName(), person.getLastName(),
				person.getMail(), person.getWebsite(), person.getBirthday(),
				person.getPassword(), person.getGroupId(), person.getId() };
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.BIGINT, Types.BIGINT};
		modifiedRows = jdbcTemplate.update(sql, args, types);
		return modifiedRows;
	}
	
	public int updateUsernameUserRoles(Person person){
		String sql = "UPDATE User_roles SET username = ? "
				+ "WHERE username = ?;";
		int modifiedRows;
		Object[] args = { person.getMail(), person.getMail()};
		int [] argsTypes = {Types.VARCHAR, Types.VARCHAR};
		modifiedRows = jdbcTemplate.update(sql, args, argsTypes);
		return modifiedRows;
	}

	@Override
	public Collection<Person> findAllPersons() {
		
		String sql = "SELECT * FROM Person";
		
		persons = jdbcTemplate.query(sql, personPropertyRowMapper);
		return persons;
	}
	
	@Override
	public Integer countPersons() {
		Integer personsCount = new Integer(0);
		String sql = "SELECT count(*) FROM Person";
		
		personsCount = jdbcTemplate.queryForObject(sql, Integer.class);
		return personsCount;
	}
	
	@Override
	public Collection<Person> findLimitPersons(Integer offset, Integer maxRows) {

		String sql = "SELECT * FROM Person LIMIT ?,?;";
		
		Object[] args = { offset, maxRows };
		int[] types = {Types.INTEGER, Types.INTEGER};
		persons = jdbcTemplate.query(sql, args, types, personPropertyRowMapper);
		return persons;
	}

	@Override
	public int saveGroup(Group group) {
		
		final String sql = "INSERT INTO Groupe (id,name) VALUES (?,?)";
		
		int insertedRows;
		Object[] args = { group.getId(), group.getName() };
		int[] types = {Types.BIGINT, Types.VARCHAR};
		insertedRows = jdbcTemplate.update(sql, args, types);
		return insertedRows;
		
	}
	
	@Override
	public int saveGroupAuto(Group group) {
		int insertedRows = 0;
		String sql = "INSERT INTO Groupe (name) VALUES (:name)";

		SqlParameterSource fileParameters = 
				new BeanPropertySqlParameterSource(group);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertedRows = namedParameterJdbcDaoSupport.
				getNamedParameterJdbcTemplate().update(sql, fileParameters, 
						keyHolder);
		 
		group.setId(keyHolder.getKey().longValue());
		return insertedRows;
	}

	@Override
	public int removeOnePerson(Long personId) {
		
		String sql = "DELETE FROM Person WHERE id = ?";
		int deletedRows;
		Object[] args = { personId };
		int[] types = {Types.BIGINT};
		deletedRows = jdbcTemplate.update(sql, args, types);
		return deletedRows;
		
	}

	@Override
	public int removeOneGroup(Long groupId) {
		
		String sql = "DELETE FROM Groupe WHERE id = ?";
		int deletedRows;
		Object[] args = { groupId };
		int[] types = {Types.BIGINT};
		deletedRows = jdbcTemplate.update(sql, args, types);
		return deletedRows;
		
	}

	@Override
	public Person getPersonByEmail(String personEmail) {
		String sql = "SELECT * FROM Person WHERE mail = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { personEmail }, personPropertyRowMapper);
		
		person = (Person)queryForObject;

		return person;
	}

	@Override
	public String getEmailByPersonId(Long personId) {
		
		String sql = "SELECT mail FROM Person WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { personId }, personPropertyRowMapper);
		
		String mail = (String)queryForObject;

		return mail;
	}
	
	@Override
	public Map<Long, String> getGroupNames(){
		Map<Long, String> groupNames = new LinkedHashMap<>();
		
		String sql = "SELECT id,name FROM Groupe";
		
		groups = jdbcTemplate.query(sql, groupPropertyRowMapper);
		for (Group g : groups) {
			groupNames.put(g.getId(), g.getName());
		}
		return groupNames;
	}
	
	@Override
	public Map<Long, String> getLimitGroupNames(Integer offset, Integer maxRows){
		Map<Long, String> groupNames = new LinkedHashMap<>();
		
		String sql = "SELECT id,name FROM Groupe LIMIT ?,?;";
		Object[] args = { offset, maxRows };
		int[] types = {Types.INTEGER, Types.INTEGER};
		groups = jdbcTemplate.query(sql, args, types, groupPropertyRowMapper);
		for (Group g : groups) {
			groupNames.put(g.getId(), g.getName());
		}
		return groupNames;
	}
	
	@Override
	public Integer countGroups() {
		Integer groupsCount = new Integer(0);
		String sql = "SELECT count(*) FROM Groupe";
		
		groupsCount = jdbcTemplate.queryForObject(sql, Integer.class);
		return groupsCount;
	}

	@Override
	public int removeAllPersons() {
		String sql = "DELETE FROM Person";
		int deletedRows;
		deletedRows = jdbcTemplate.update(sql);
		return deletedRows;
	}

	@Override
	public int removeAllPersonsGroup(Long groupId) {
		String sql = "DELETE FROM Person WHERE groupId = ?";
		int deletedRows;
		Object[] args = { groupId };
		int[] types = {Types.BIGINT};
		deletedRows = jdbcTemplate.update(sql, args, types);
		return deletedRows;
	}

	@Override
	public int removeAllGroups() {
		String sql = "DELETE FROM Groupe";
		int deletedRows;
		deletedRows = jdbcTemplate.update(sql);
		return deletedRows;
	}

	@Override
	public int updateGroup(Group group) {
		
		String sql = "UPDATE Groupe SET name = ? WHERE id = ?;";
		int modifiedRows;
		Object[] args = { group.getName(), group.getId() };
		int[] types = { Types.VARCHAR, Types.BIGINT };
		modifiedRows = jdbcTemplate.update(sql, args, types);
		return modifiedRows;
	}
	
	@Override
	public int updatePersonPasswordById(Long personId, String password) {
		
		String sql = "UPDATE Person SET password = ? WHERE id = ?;";
		int modifiedRows;
		password = personService.hashPassword(password);
		Object[] args = { password, personId };
		int[] types = { Types.VARCHAR, Types.BIGINT };
		modifiedRows = jdbcTemplate.update(sql, args, types);
		return modifiedRows;
		
	}
	
	@Override
	public Collection<Person> findPersonByName(String personName) {
		
		personName = "%" + personName + "%";
		String sql = "SELECT * FROM Person WHERE firstName LIKE ? OR lastName LIKE ?;";
		Object[] args = { personName, personName };
		int[] argTypes = { Types.VARCHAR, Types.VARCHAR };
		persons = jdbcTemplate.query(sql, args, argTypes, personPropertyRowMapper);
		return persons;
	}
	
	public Collection<Group> findGroupsByName(String groupName) {
		
		groupName = "%" + groupName + "%";
		String sql = "SELECT * FROM Groupe WHERE name LIKE ?;";
		Object[] args = { groupName};
		int[] argTypes = { Types.VARCHAR};
		groups = jdbcTemplate.query(sql, args, argTypes, groupPropertyRowMapper);
		
		return groups;
	}
	
	public Collection<Person> findLimitGroupPersonsByGroupName(String groupName	
			, Long groupId, Integer offset, Integer maxRows){
		
		groupName = "%" + groupName + "%";
		String sql = "SELECT p.id, p.firstName, p.lastName, p.mail, p.website,"
				+ " p.birthday, p.password, p.groupId, p.enabled "
				+ "FROM Person p, Groupe g WHERE p.groupId = g.id "
				+ "AND p.groupId = ? AND g.name LIKE ? LIMIT ?,?;";
		Object[] args = {groupId, groupName, offset, maxRows};
		int[] argTypes = {Types.BIGINT, Types.VARCHAR, Types.INTEGER,
				Types.INTEGER};
		persons = jdbcTemplate.query(sql, args, argTypes, 
				personPropertyRowMapper);
		return persons;
	}

	@PreDestroy
	public void close() {
		logger.info("Close "+this.getClass());
	}
}
