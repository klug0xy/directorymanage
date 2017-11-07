/**
 * @author Houssem MJID
 * @author Mohamed ABDELNABI
 */
package fr.amu.directorymanage.beans;

public class User {
	
	public long personId;
	public String password;
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
