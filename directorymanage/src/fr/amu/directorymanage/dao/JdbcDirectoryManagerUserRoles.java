/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

import java.sql.Types;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import fr.amu.directorymanage.beans.Person;

/**
 * 
 * Classe qui implemente l'interface IDirectoryManagerUserRoles pour JDBC
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Service
public class JdbcDirectoryManagerUserRoles implements IDirectoryManagerUserRoles {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@PostConstruct
	public void init() {
		logger.info("Init "+this.getClass());
	}

	@Override
	public int updateUsernameUserRoles(Person person) {
		
		String sql = "UPDATE User_roles SET username = ? "
				+ "WHERE username = ?;";
		int modifiedRows;
		Object[] args = { person.getMail(), person.getMail()};
		int [] argsTypes = {Types.VARCHAR, Types.VARCHAR};
		modifiedRows = jdbcTemplate.update(sql, args, argsTypes);
		return modifiedRows;
	}

	@Override
	public String findUsernameUserRolesByUsername(String personMail) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PreDestroy
	public void close() {
		logger.info("Close "+this.getClass());
	}

}
