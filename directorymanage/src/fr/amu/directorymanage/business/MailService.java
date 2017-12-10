/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.business;

import org.springframework.mail.javamail.JavaMailSender;

/**
 * 
 * Interface qui definit les methodes du service mail
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public interface MailService {
	
	/**
	 * initialisation du service mail
	 */
	public void init();
	
	/**
	 * la methode qui retourne l'objet JavaMailSender,
	 * qui sert a envoye un mail
	 * @return un objet de type JavaMailSender
	 */
	public JavaMailSender getMailSender();
	
	/**
	 * la methode qui fixe l'objet JavaMailSender
	 * pour la classe qui implemente cette interface
	 * @param mailSender l'objet JavaMailSender a fixer 
	 */
	public void setMailSender(JavaMailSender mailSender);
	
	/**
	 * la methode qui envoi un mail
	 * @param emailId chaine de caractere contenant le mail de destination
	 * @param personId Long de la personne qui a demande 
	 * la recuperation du mot de passe
	 * @param token chaine de caractere contenant le token genere
	 */
	public void sendMail(String emailId, Long personId, String token);
	
	/**
	 * fermeture du service mail
	 */
	public void close();

}
