/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.exceptions;

/**
 * 
 * Classe qui intercepte les exceptions liees
 * a la configuration de la couche DAO
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class DAOConfigurationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9014306469742980421L;

	public DAOConfigurationException( String message ) {

        super( message );

    }

    public DAOConfigurationException( String message, Throwable cause ) {

        super( message, cause );

    }

    public DAOConfigurationException( Throwable cause ) {

        super( cause );

    }

}
