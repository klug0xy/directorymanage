package fr.amu.directorymanage.exceptions;

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
