/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.exceptions;

/**
 * 
 * Classe qui intercepte les exceptions si une personne n'est pas trouvee
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class PersonNotFoundException extends RuntimeException {
	
	 private static final long serialVersionUID = 5861310537366287163L;

	    public PersonNotFoundException() {
	        super();
	    }

	    public PersonNotFoundException(final String message, final Throwable cause) {
	        super(message, cause);
	    }

	    public PersonNotFoundException(final String message) {
	        super(message);
	    }

	    public PersonNotFoundException(final Throwable cause) {
	        super(cause);
	    }


}
