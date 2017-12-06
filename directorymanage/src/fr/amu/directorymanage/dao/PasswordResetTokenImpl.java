package fr.amu.directorymanage.dao;


import java.util.Date;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import fr.amu.directorymanage.beans.PasswordResetToken;

@Service
public class PasswordResetTokenImpl implements PasswordResetTokenRepository {
	
	PasswordResetToken passwordResetToken;
	
	NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = 
			new NamedParameterJdbcDaoSupport();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcDaoSupport.setDataSource(dataSource);
		
	}
	
	private BeanPropertyRowMapper<PasswordResetToken> 
		PasswordResetTokenPropertyRowMapper = new
		BeanPropertyRowMapper<PasswordResetToken>(PasswordResetToken.class);
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@PostConstruct
	public void init() {
		logger.info("Init "+this.getClass());
	}
	
	@Override
	public PasswordResetToken findByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PasswordResetToken findByPersonId(Long personId) {
		
		String sql = "SELECT * FROM PersonsTokens WHERE personId = :personId";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource
				("personId", (personId));
		NamedParameterJdbcTemplate namedParameterJdbcTemplate =
				namedParameterJdbcDaoSupport.getNamedParameterJdbcTemplate();
		PasswordResetToken passwordResetToken = (PasswordResetToken) 
				namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
						PasswordResetTokenPropertyRowMapper);
		
		return passwordResetToken;
	}

	@Override
	public Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByExpiryDateLessThan(Date now) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllExpiredSince(Date now) {
		// TODO Auto-generated method stub

	}

	@Override
	public int save(PasswordResetToken myToken) {
		int insertedRows = 0;
		
		String sql = "INSERT INTO PersonsTokens"
				+ " (personId,token,expiryDate)"
				+ " VALUES (:personId,:token,:expiryDate)";
		
		SqlParameterSource passwordResetTokenParameters = 
				new BeanPropertySqlParameterSource(myToken);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertedRows = namedParameterJdbcDaoSupport.
				getNamedParameterJdbcTemplate().update(sql, passwordResetTokenParameters, 
						keyHolder);
		 
		myToken.setId(keyHolder.getKey().longValue());
		return insertedRows;
		
	}
	
	@PreDestroy
	public void close() {
		logger.info("Close "+this.getClass());
		
	}

}
