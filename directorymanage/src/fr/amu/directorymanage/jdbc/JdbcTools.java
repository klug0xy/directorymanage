package fr.amu.directorymanage.jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.dbcp2.BasicDataSource;

public class JdbcTools {

	//private String url = "jdbc:mysql://dbs-perso.luminy.univmed.fr/m15022593";
	//private String user = "m15022593";
	//private String password = "mNk5R647";

	private String url = "jdbc:mysql://localhost/directorymanage";
	private String user = "mjid";
	private String password = "hmac2017.";
	private String driverName = "com.mysql.jdbc.Driver";
	
	public BasicDataSource bds = new BasicDataSource();
	
	void loadDriver() throws ClassNotFoundException {
		Class.forName(driverName);
	}
	
	@PostConstruct
	public void init() /*throws ClassNotFoundException*/{

		System.err.println("Init " + this);
		//loadDriver();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost/directorymanage");
		bds.setUsername("mjid");
		bds.setPassword("hmac2017.");
		bds.setInitialSize(5); // ouvrir cinq connexions
		bds.setMaxTotal(5);    // pas plus de cinq connexions
		
	}

	public Connection newConnection() throws SQLException {

		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	@PreDestroy
	public void close(Connection c) throws SQLException { 
		c.close();

	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
