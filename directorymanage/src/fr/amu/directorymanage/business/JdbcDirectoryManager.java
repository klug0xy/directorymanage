package fr.amu.directorymanage.business;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import fr.amu.directorymanage.beans.User;

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
	
	static private Person personMapper(ResultSet resultSet, int rank) throws SQLException {
		Person person = new Person();
		Long number = resultSet.getLong("id");
		String firstName = resultSet.getString("firstName");
		String lastName = resultSet.getString("lastName");
		String mail = resultSet.getString("mail");
		String website = resultSet.getString("website");
		Date birthday = resultSet.getDate("birthday");
		String password = resultSet.getString("password");
		

		person.setId(number);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setMail(mail);
		person.setWebsite(website);
		person.setBirthday(birthday);
		person.setPassword(password);
		return person;
	}


	@Override
	public User newUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<Person> findAllGroupPersons(User user, Long groupId) {
		String sql = "SELECT * FROM Person WHERE groupId = ?";
		
		persons = jdbcTemplate.query(sql,
				new Object[] { groupId }, personPropertyRowMapper);
		return persons;
	}

	@Override
	public Person findPerson(User user, Long id) {
		
		String sql = "SELECT id,firstName,lastName,mail,website,birthday"
				+ ",password,groupId FROM Person WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { id }, personPropertyRowMapper);
		
		person = (Person)queryForObject;

		return person;
//		return this.jdbcTemplate.que
//				("SELECT * FROM Person WHERE id ='"+personId+"'",
//				JdbcDirectoryManager::personMapper);
	}

	@Override
	public Group findGroup(User user, Long groupId) {
		String sql = "SELECT id,name FROM Groupe WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { groupId }, groupPropertyRowMapper);
		
		group = (Group)queryForObject;

		return group;
	}

	@Override
	public boolean login(User user, Long personId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout(User user) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int savePersonAuto(Person person) {
		int insertedRows = 0;
		String sql = "INSERT INTO Person"
				+ " (firstName,lastName,mail,website,birthday,password"
				+ ",groupId)"
				+ " VALUES (:firstName,:lastName,:mail,:website,:birthday"
				+ ",:password,:groupId)";

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
	public int savePerson(User user, Person person) {
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
				+ "SET id = ?, firstName = ?, lastName = ?, mail = ?, "
				+ "website = ?, birthday = ?, password = ?, groupId = ?"
				+ " WHERE id = ?;";
		int modifiedRows;
		Object[] args = { person.getId(), person.getFirstName(), person.getLastName(),
				person.getMail(), person.getWebsite(), person.getBirthday(),
				person.getPassword(), person.getGroupId(), person.getId() };
		int[] types = {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.BIGINT, Types.BIGINT};
		modifiedRows = jdbcTemplate.update(sql, args, types);
		return modifiedRows;
	}

	@Override
	public Collection<Person> findAllPersons() {
		
		String sql = "SELECT * FROM Person";
		
		persons = jdbcTemplate.query(sql, personPropertyRowMapper);
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
	public String getGroupName(Long groupId) {
		// TODO Auto-generated method stub
		return null;
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

}
