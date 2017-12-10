/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.amu.directorymanage.beans.PasswordResetToken;
import fr.amu.directorymanage.dao.PasswordResetTokenRepository;

/**
 * 
 * Classe qui implemente l'interface IPersonService
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Service
@Qualifier
public class PersonService implements IPersonService {
	
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    
	@Override
	public void createPasswordResetTokenForUser(Long personId, String token) {
		// TODO Auto-generated method stub
		final PasswordResetToken myToken = new PasswordResetToken(token, personId);
        passwordTokenRepository.save(myToken);

	}
	
	@Override
	public String hashPassword(String password) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		return hashedPassword;
	}
	
	@Override
	public boolean hasRole(String role) {
		  Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
		  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		  boolean hasRole = false;
		  for (GrantedAuthority authority : authorities) {
		     hasRole = authority.getAuthority().equals(role);
		     if (hasRole) {
			  break;
		     }
		  }
		  return hasRole;
	}

}
