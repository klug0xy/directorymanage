package fr.amu.directorymanage.business;

import fr.amu.directorymanage.beans.Person;

public interface IPersonService {
	
	void createPasswordResetTokenForUser(Long personId, String token);
	
	Person findPersonByEmail(String email);
	
	boolean hasRole(String role);
	
	String hashPassword(String password);


}
