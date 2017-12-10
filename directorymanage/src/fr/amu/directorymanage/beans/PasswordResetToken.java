/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.beans;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * Classe qui definit le bean PasswordResetToken 
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class PasswordResetToken {
	
	// le token expire au bout de 24h
	private static final int EXPIRATION = 60 * 24;
	
	private Long id;
	
	private Long personId;
	
    private String token;
    
    //private String personMail;
    //private Person person;
    
    private Date expiryDate;

    public PasswordResetToken() {}

    public PasswordResetToken(final String token) {

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public PasswordResetToken(final String token, final Long personId) {

        this.token = token;
        //this.person = person;
        this.personId = personId;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

//    public Person getPerson() {
//        return person;
//    }

//    public void setPerson(final Person person) {
//        this.person = person;
//    }

//    public Date getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(final Date expiryDate) {
//        this.expiryDate = expiryDate;
//    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.
//			hashCode());
//        result = prime * result + ((token == null) ? 0 : token.hashCode());
//        result = prime * result + ((person == null) ? 0 : person.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final PasswordResetToken other = (PasswordResetToken) obj;
//        if (expiryDate == null) {
//            if (other.expiryDate != null) {
//                return false;
//            }
//        } else if (!expiryDate.equals(other.expiryDate)) {
//            return false;
//        }
//        if (token == null) {
//            if (other.token != null) {
//                return false;
//            }
//        } else if (!token.equals(other.token)) {
//            return false;
//        }
//        if (person == null) {
//            if (other.person != null) {
//                return false;
//            }
//        } else if (!person.equals(other.person)) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append
        ("[Expires").append(expiryDate).append("]");
        return builder.toString();
    }

//	public String getPersonMail() {
//		return personMail;
//	}
//
//	public void setPersonMail(String personMail) {
//		this.personMail = personMail;
//	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

}
