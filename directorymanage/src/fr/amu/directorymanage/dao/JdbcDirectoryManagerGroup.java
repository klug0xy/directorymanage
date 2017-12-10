/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.dao;

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

/**
 * 
 * Classe qui implemente l'interface IDirectoryManagerGroup pour JDBC
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Service
public class JdbcDirectoryManagerGroup implements IDirectoryManagerGroup {
	
	private JdbcTemplate jdbcTemplate;
	NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = 
			new NamedParameterJdbcDaoSupport();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcDaoSupport.setDataSource(dataSource);
		
	}
	
	private BeanPropertyRowMapper<Group> groupPropertyRowMapper = 
			new BeanPropertyRowMapper<Group>(Group.class);
	
	Group group;
	
	Collection<Group> groups;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@PostConstruct
	public void init() {
		logger.info("Init "+this.getClass());
	}

	@Override
	public int saveGroup(Group g) {
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
	public int updateGroup(Group group) {
		
		String sql = "UPDATE Groupe SET name = ? WHERE id = ?;";
		int modifiedRows;
		Object[] args = { group.getName(), group.getId() };
		int[] types = { Types.VARCHAR, Types.BIGINT };
		modifiedRows = jdbcTemplate.update(sql, args, types);
		return modifiedRows;
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
	public Collection<Group> findGroupsByName(String groupName) {
		
		groupName = "%" + groupName + "%";
		String sql = "SELECT * FROM Groupe WHERE name LIKE ?;";
		Object[] args = { groupName};
		int[] argTypes = { Types.VARCHAR};
		groups = jdbcTemplate.query(sql,args,argTypes, groupPropertyRowMapper);
		
		return groups;
	}

	@Override
	public Map<Long, String> getLimitGroupNames(Integer offset, Integer maxRows) {
		
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
	public Map<Long, String> getGroupNames() {
		
		Map<Long, String> groupNames = new LinkedHashMap<>();
		
		String sql = "SELECT id,name FROM Groupe";
		
		groups = jdbcTemplate.query(sql, groupPropertyRowMapper);
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
	public int removeOneGroup(Long groupId) {
		
		String sql = "DELETE FROM Groupe WHERE id = ?";
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
	
	@PreDestroy
	public void close() {
		logger.info("Close "+this.getClass());
	}

}
