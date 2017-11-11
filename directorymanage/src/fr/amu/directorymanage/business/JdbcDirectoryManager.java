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
import org.springframework.stereotype.Service;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.beans.PersonMail;
import fr.amu.directorymanage.beans.User;

@Service
public class JdbcDirectoryManager implements IDirectoryManager {
	
	private JdbcTemplate jdbcTemplate;
	private BeanPropertyRowMapper<Person> beanPropertyRowMapper = 
			new BeanPropertyRowMapper<Person>(Person.class);
	private BeanPropertyRowMapper<Group> groupPropertyRowMapper = 
			new BeanPropertyRowMapper<Group>(Group.class);
	Person p;
	
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
	}
	
	static private Person personMapper(ResultSet resultSet, int rank) throws SQLException {
		Person person = new Person();
		PersonMail personMail = new PersonMail();
		Long number = resultSet.getLong("id");
		String firstName = resultSet.getString("firstName");
		String lastName = resultSet.getString("lastName");
		Date birthday = resultSet.getDate("birthday");
		String mail = resultSet.getString("mail");

		person.setId(number);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setBirthday(birthday);
		person.setMail(mail);
		person.setPersonMail(personMail);
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
				new Object[] { groupId }, beanPropertyRowMapper);
		return persons;
	}

	@Override
	public Person findPerson(User user, Long id) {
		
		String sql = "SELECT * FROM Person WHERE id = ?";
		
		Object queryForObject = jdbcTemplate.queryForObject(sql,
				new Object[] { id }, beanPropertyRowMapper);
		
		p = (Person)queryForObject;

		return p;
//		return this.jdbcTemplate.que
//				("SELECT * FROM Person WHERE id ='"+personId+"'",
//				JdbcDirectoryManager::personMapper);
	}

	@Override
	public Group findGroup(User user, Long groupId) {
		// TODO Auto-generated method stub
		return null;
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
	public int savePerson(User user, Person p) {
		String sql = "INSERT INTO Person"
				+ " (id,firstName,lastName,birthday,mail,groupId)"
				+ " VALUES (?,?,?,?,?,?)";
		int insertedRows;
		Object[] args = { p.getId(), p.getFirstName(), p.getLastName(),
				p.getBirthday(), p.getMail(), p.getGroupId() };
		int[] types = {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.DATE,
				Types.VARCHAR, Types.BIGINT};
		insertedRows = jdbcTemplate.update(sql, args, types);
		return insertedRows;
		

	}

	@Override
	public Collection<Person> findAllPersons() {
		
		String sql = "SELECT * FROM Person";
		
		persons = jdbcTemplate.query(sql, beanPropertyRowMapper);
		return persons;
	}

	@Override
	public void saveGroup(Group g) {
		// TODO Auto-generated method stub
		
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
	public void removeOneGroup(Long groupId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Person getPersonByEmail(String personEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmailByPersonId(Long personId) {
		// TODO Auto-generated method stub
		return null;
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

}
