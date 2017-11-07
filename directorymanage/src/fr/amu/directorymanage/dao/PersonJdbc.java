package fr.amu.directorymanage.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.amu.directorymanage.dao.DAOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.bus.PersonManager;
import fr.amu.directorymanage.interfaces.PersonDao;
import fr.amu.directorymanage.jdbc.JdbcTools;

@Service
@Qualifier("jdbcimp")
public class PersonJdbc implements PersonDao {

	JdbcTools jdbc = new JdbcTools();
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	PersonManager pm = new PersonManager();

	public int executeUpdate(String query, Object... parameters) throws SQLException, InterruptedException {
		int i;
		try (Connection conn = jdbc.newConnection()) {

			// conn.setAutoCommit(false);

			PreparedStatement st = conn.prepareStatement(query, ResultSet.CONCUR_UPDATABLE);

			for (int k = 0; k < parameters.length; k++) {

				if (parameters[k] instanceof Long) {
					st.setLong(k + 1, (Long) parameters[k]);
				} else if (parameters[k] instanceof String) {
					st.setObject(k + 1, (String) parameters[k]);
				} else if (parameters[k] instanceof java.sql.Date) {
					st.setDate(k + 1, (java.sql.Date) parameters[k]);
				}
			}
			i = st.executeUpdate();
			// Thread.sleep(6000);
			// conn.commit();
		}
		return i;
	}

	@Override
	public Collection<Group> findAllGroups() throws DAOException {

		final String SQL_SELECT_ALL_GROUPS = "SELECT id,name FROM Groupe;";

		Group group = new Group();
		Collection<Group> clg = new ArrayList<Group>();

		try {
			jdbc.init();
			connexion = jdbc.bds.getConnection();
			preparedStatement = connexion.prepareStatement(SQL_SELECT_ALL_GROUPS, ResultSet.CONCUR_UPDATABLE);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");

				group.setId(id);
				group.setName(name);
				clg.add(group);

			}
			return clg;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Collection<Person> findAllPersons(long groupId) throws DAOException {

		final String SQL_SELECT_ALL_PERSONS = "SELECT id,firstName,lastName,"
				+ "birthday,mail,groupId FROM Person WHERE groupId = ?;";

		Person person = new Person();
		Collection<Person> clp = new ArrayList<Person>();

		try {
			jdbc.init();
			connexion = jdbc.bds.getConnection();
			preparedStatement = connexion.prepareStatement(SQL_SELECT_ALL_PERSONS, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, groupId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				long number = resultSet.getLong("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				Date birthday = resultSet.getDate("birthday");
				String mail = resultSet.getString("mail");
				long grId = resultSet.getLong("groupId");

				person.setId(number);
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setBirthday(birthday);
				person.setMail(mail);
				person.setGroupId(grId);
				clp.add(person);

			}
			return clp;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public Person findPerson(long id) throws DAOException {

		final String SQL_SELECT_PERSON = "SELECT id,firstName,lastName,birthday"
				+ ",mail,groupId FROM Person WHERE id = ?";
		Person person = new Person();

		try {
			jdbc.init();
			connexion = jdbc.bds.getConnection();
			preparedStatement = connexion.prepareStatement(SQL_SELECT_PERSON, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				long number = resultSet.getLong("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				Date birthday = resultSet.getDate("birthday");
				String mail = resultSet.getString("mail");
				long groupId = resultSet.getLong("groupId");

				person.setId(number);
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setBirthday(birthday);
				person.setMail(mail);
				person.setGroupId(groupId);

				// resultSet.close();
				// preparedStatement.close();
				// connexion.close();
			}
			return person;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void savePerson(Person p) throws DAOException {

		pm.check(p);
		if (p.valid) {
			try {
				executeUpdate(
						"REPLACE INTO Person (id, firstName, lastName,"
								+ "birthday, mail, groupId) VALUES(?,?,?,?,?,?)",
						p.getId(), p.getFirstName(), p.getLastName(), p.getBirthday(), p.getMail(), p.getGroupId());
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			throw new DAOException("Invalid person");
		}
	}

	@Override
	public void saveGroup(Group g) throws DAOException {

		try {
			executeUpdate("REPLACE INTO Groupe (id, name) VALUES(?,?);", g.getId(), g.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
