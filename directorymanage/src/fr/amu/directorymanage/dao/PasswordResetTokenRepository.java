package fr.amu.directorymanage.dao;

import java.util.Date;
import java.util.stream.Stream;

import fr.amu.directorymanage.beans.PasswordResetToken;

public interface PasswordResetTokenRepository {
	
	int save(PasswordResetToken myToken);
	
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByPersonId(Long personId);
	
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	void deleteByExpiryDateLessThan(Date now);
	
	void deleteAllExpiredSince(Date now);

}
