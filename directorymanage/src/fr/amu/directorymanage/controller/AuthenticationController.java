/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.amu.directorymanage.beans.PasswordResetToken;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.business.MailService;
import fr.amu.directorymanage.dao.IDirectoryManagerPerson;
import fr.amu.directorymanage.dao.PasswordResetTokenRepository;

/**
 * 
 * Classe controller pour les mecanismes de l'authentification
 * et recuperation de mot de passe et la page d'accueil
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Controller()
@RequestMapping("")
public class AuthenticationController {
	
//	@Autowired
//	IDirectoryManager directoryManager;
	@Autowired
	IDirectoryManagerPerson directoryManagerPerson;
	
	@Autowired
	private MailService mailService;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	private JavaMailSender mailSender;
	
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * la methode qui fait le mapping vers la page d'accueil
	 * @return objet de type ModelAndView, qui contient la page index
	 */
	@RequestMapping(value = {"/" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "You're welcome!");
		model.addObject("message", "Index of directory manager!");
		
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().
				getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken) && auth.
				getPrincipal() != null) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}
		model.setViewName("index");
		return model;

	}

	/**
	 * la methode qui fait le mapping vers la page admin
	 * NOTE: not implemented yet!
	 * @return objet de type ModelAndView, qui contient la page admin
	 */
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database "
				+ "Authentication");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");
		return model;

	}

	/**
	 * la methode qui fait le mapping vers la page login
	 * @param error chaine de caractere a recevoir si une erreur est produite
	 * qui sert a renvoyer un message d'erreur a la vue
	 * @param logout chaine de caractere a recevoir si la personne
	 * s'est deconnecte qui sert a deconnecter la personne
	 * @param request HttpServletRequest pour decrire la requete http
	 * @param response HttpServletResponse pour decrire la reponse http
	 * @return objet de type ModelAndView, qui contient la page login avec 
	 * comme parametre soit logout soit error
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",
	required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("GET login page");
		ModelAndView model = new ModelAndView();
		 
		if (SecurityContextHolder.getContext().getAuthentication().
				getPrincipal() != null) {
			Object principal = SecurityContextHolder.getContext().
					getAuthentication().getPrincipal();
			if (principal instanceof User) {
				User authUser = (User) principal;
				logger.info(authUser.getUsername());
			}	
		}
		
		if (error != null) {
			model.addObject("error", "Invalid email and password!");
		}

		if (logout != null) {
			 Authentication auth = SecurityContextHolder.getContext().
					 getAuthentication();
			 if (auth != null){    
			        new SecurityContextLogoutHandler().
			        logout(request, response, auth);
			    }
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		model.setViewName("login");
		
		return model;
	}
	
	/**
	 * la methode qui fait le mapping vers la page recover
	 * qui sert a la recuperation du mot de passe reponse a la methode GET http 
	 * @return objet de type ModelAndView, qui contient la page recover 
	 */
	@RequestMapping(value = "/recover", method = RequestMethod.GET)
	public ModelAndView recoverPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("recover");
		return model;
		
	}
	
	/**
	 * la methode qui fait le mapping vers la page recover
	 * repondant a la methode POST http, recupere le mail tape par la personne
	 * recupere la personne de la base de donnees via son email,genere le token
	 * cree l'objet PasswordResetToken correspondant,
	 * stocke ce dernier dans la base de donnees,
	 * appel le service mail pour l'envoi du mail de recuperation
	 * renvoi un message de succes a la vue si tout est bien passe
	 * ou un message d'echec si le mail n'est pas reconnu 
	 * @param personEmail chaine de caractere contenant le mail  
	 * @return objet de type ModelAndView, qui contient la page recover
	 */
	@RequestMapping(value="/recover" , method=RequestMethod.POST)
	public ModelAndView resetRequest(@RequestParam(value="email") 
		String personEmail)
	{
		ModelAndView model = new ModelAndView();
		//check if the email id is valid and registered with us.
		try {
			
			Person person = directoryManagerPerson.getPersonByEmail(personEmail);
			if (person != null) {
				//Generate a token
				String token = UUID.randomUUID().toString();
				Long personId = person.getId();
				PasswordResetToken myToken = new PasswordResetToken(token,personId);
				passwordResetTokenRepository.save(myToken);
				
				mailService.init();
				mailService.setMailSender(mailSender);
				mailService.sendMail(personEmail, personId, token);
				mailService.close();
				model.addObject("validEmail", "Check your email box");
				model.setViewName("recover");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		model.addObject("invalidEmail", "We don't recognize this email address");
		model.setViewName("recover");
		return model;
		
	}
	
	/**
	 * la methode qui fait le mapping vers la page newpassword
	 * reponse a la methode GET http, verifie si le token est correct
	 * et si il est pas expire, si tout est ok, la personne aura acces
	 * aux formulaire de reinitialisation de mot de passe ou une redirection
	 * a une page d'erreur est faite sinon
	 * @param personId Long indiquant l'identifiant de la personne 
	 * @param token String contenant le token de la personne
	 * @return objet de type ModelAndView, qui contient la page recover
	 */
	@RequestMapping(value = "/newpassword", method = RequestMethod.GET)
	public ModelAndView newPassword(@RequestParam(value = "personId")
		Long personId , @RequestParam(value = "token") String token){
		
		ModelAndView model = new ModelAndView();
		
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.
				findByPersonId(personId);
		String dbToken = passwordResetToken.getToken();
		Date expiryDate = passwordResetToken.getExpiryDate();
		Date today = new Date();
		java.sql.Date expirySqlDate = new java.sql.Date(expiryDate.getTime());
		java.sql.Date todaySqlDate = new java.sql.Date(today.getTime());
		logger.info(dbToken + " | " + token);
		logger.info("from db : "+expirySqlDate + " | Today : " + todaySqlDate);
		
		if ( (dbToken.equals(token)) && (expirySqlDate.after(todaySqlDate) || 
				expirySqlDate.equals(todaySqlDate))) {
			model.addObject("token",token);
			model.addObject("personId", personId);
			model.setViewName("newpassword");
		}
		else {
			model.addObject("tokenExpiredMsg", "Your token was expired, please"
					+ " click the link below to reset your password!");
			model.setViewName("error");
		}
		return model;
		
	}
	
	/**
	 * la methode qui fait le mapping vers la page newpassword
	 * reponse a la methode POST http, verifie si les deux mot de passe
	 * tape par la personne sont bien identiques si c'est bon, le mot de passe
	 * est mis a jour dans la base et un lien de login est renvoye,
	 *  un message d'erreur est renvoye sinon
	 * @param password1 le premier mot de passe tape
	 * @param password2 le deuxieme mot de passe tape
	 * @param personId Long identifiant de la personne
	 * @return objet de type ModelAndView, qui contient la page newpassord
	 */
	@RequestMapping(value = "/newpassword", method = RequestMethod.POST)
	public ModelAndView newPasswordPost(@RequestParam(value = "password1")
		String password1, @RequestParam(value = "password2") String password2,
		@RequestParam(value = "personId") Long personId){
		
		ModelAndView model = new ModelAndView();
		
		if (password1.equals(password2) && !(password1.isEmpty()) && 
				!(password2.isEmpty()) && personId != null) {
			directoryManagerPerson.updatePersonPasswordById(personId, password1);
			model.addObject("successChangePassMsg", 
					"Your password updated succesfully, try to login <a href='/directorymanage/login'>Here</a>");
			model.setViewName("newpassword");
		}
		else {
			model.addObject("failChangePassMsg", "Password change failed!");
			model.setViewName("newpassword");
		}
		return model;
		
	}
	
	/**
	 * la methode qui fait le mapping vers la page 403,
	 * reponse a la methode GET http, les demandes sont renvoye vers cette page
	 * si un acces a un contenu interdit par une personne est survenu
	 * verifie si la personne est authentifie et il n'est pas anonymous pour
	 * afficher son nom d'utilisateur (son mail), si c'est pas le cas la page
	 * est renvoye normalement
	 * @return objet de type ModelAndView, qui contient la page 403
	 */
	@RequestMapping(value = "/errors/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().
				getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken) && auth.
				getPrincipal() != null) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}

}
