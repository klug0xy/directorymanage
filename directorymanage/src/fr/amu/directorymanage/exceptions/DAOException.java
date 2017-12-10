/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.exceptions;

/**
 * 
 * Classe qui intercepte les exceptions liees a l'acces aux donnes DAO
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class DAOException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9213483901982202006L;

	public DAOException( String message ) {

        super( message );

    }

    public DAOException( String message, Throwable cause ) {

        super( message, cause );

    }

    public DAOException( Throwable cause ) {

        super( cause );

    }

}