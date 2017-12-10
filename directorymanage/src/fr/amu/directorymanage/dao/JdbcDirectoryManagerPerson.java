/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

import java.sql.Types;
import java.util.Collection;

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

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.business.IPersonService;

/**
 * 
 * Classe qui implemente l'interface IDirectoryManagerPerson pour JDBC
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Service
public class JdbcDirectoryManagerPerson implements IDirectoryManagerPerson {
	
	private JdbcTemplate jdbcTemplate;
	NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = 
			new NamedParameterJdbcDaoSupport();
	
	private BeanPropertyRowMapper<Person> personPropertyRowMapper = 
			new BeanPropertyRowMapper<Person>(Person.class);
	
	Person person;
	
	Collection<Person> persons;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcDaoSupport.setDataSource(dataSource);
		
	}
	
	@Autowired
	IPersonService personService;
	
	@PostConstruct
	public void init() {
		logger.info("Init "+this.getClass());
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
	public int savePerson(Person p) {
		
		String sql = "INSERT INTO Person"
				+ " (id,firstName,lastName,mail,website,birthday,password,"
				+ "groupId) VALUES (?,?,?,?,?,?,?,?)";
		int insertedRows;
		Object[] args = { person.getId(), person.getFirstName(), 
				person.getLastName(), person.getMail(), person.getWebsite(),
				person.getBirthday(), person.getPassword(), person.getGroupId()
				};
		int[] types = {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, 
				Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, 
				Types.BIGINT};
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
				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.BIGINT, 
				Types.BIGINT};
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
	public Person findPersonById(Long personId) {
		
		String sql = "SELECT id,firstName,lastName,mail,website,birthday"
				+ ",password,groupId FROM Person WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { personId }, personPropertyRowMapper);
		
		person = (Person)queryForObject;

		return person;
	}

	@Override
	public Collection<Person> findPersonByName(String personName) {
		
		personName = "%" + personName + "%";
		String sql = "SELECT * FROM Person WHERE firstName LIKE ? OR lastName "
				+ "LIKE ?;";
		Object[] args = { personName, personName };
		int[] argTypes = { Types.VARCHAR, Types.VARCHAR };
		persons = jdbcTemplate.query(sql, args, argTypes, 
				personPropertyRowMapper);
		return persons;
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
	public Collection<Person> findLimitGroupPersonsById(Long groupId, Integer offset, Integer maxRows) {
		
		String sql = "SELECT * FROM Person WHERE groupId = ? LIMIT ?,?;";
		
		Object[] args = { groupId, offset, maxRows };
		int[] types = {Types.BIGINT, Types.INTEGER, Types.INTEGER};
		persons = jdbcTemplate.query(sql, args, types,personPropertyRowMapper);
		return persons;
	}

	@Override
	public Collection<Person> findLimitGroupPersonsByGroupName(String groupName, Long groupId, Integer offset,
			Integer maxRows) {
		
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

	@Override
	public Collection<Person> findLimitPersons(Integer offset, Integer maxRows) {
		
		String sql = "SELECT * FROM Person LIMIT ?,?;";
		
		Object[] args = { offset, maxRows };
		int[] types = {Types.INTEGER, Types.INTEGER};
		persons = jdbcTemplate.query(sql, args, types,personPropertyRowMapper);
		return persons;
	}

	@Override
	public Collection<Person> findAllGroupPersonsById(Long groupId) {
		
		String sql = "SELECT * FROM Person WHERE groupId = ?";
		
		persons = jdbcTemplate.query(sql,
				new Object[] { groupId }, personPropertyRowMapper);
		return persons;
	}

	@Override
	public Collection<Person> findAllPersons() {
		
		String sql = "SELECT * FROM Person";
		
		persons = jdbcTemplate.query(sql, personPropertyRowMapper);
		return persons;
	}

	@Override
	public Integer countGroupPersons(Long groupId) {
		
		Integer personsCount = new Integer(0);
		String sql = "SELECT count(*) FROM Person WHERE groupId = ?;";
		Object[] args = { groupId };
		int[] types = {Types.BIGINT};
		personsCount = jdbcTemplate.queryForObject(sql, args, types, 
				Integer.class);
		return personsCount;
	}

	@Override
	public Integer countPersons() {
		
		Integer personsCount = new Integer(0);
		String sql = "SELECT count(*) FROM Person";
		
		personsCount = jdbcTemplate.queryForObject(sql, Integer.class);
		return personsCount;
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
	public int removeAllPersonsGroup(Long groupId) {
		
		String sql = "DELETE FROM Person WHERE groupId = ?";
		int deletedRows;
		Object[] args = { groupId };
		int[] types = {Types.BIGINT};
		deletedRows = jdbcTemplate.update(sql, args, types);
		return deletedRows;
	}

	@Override
	public int removeAllPersons() {
		
		String sql = "DELETE FROM Person";
		int deletedRows;
		deletedRows = jdbcTemplate.update(sql);
		return deletedRows;
	}
	
	@PreDestroy
	public void close() {
		logger.info("Close "+this.getClass());
	}

}
