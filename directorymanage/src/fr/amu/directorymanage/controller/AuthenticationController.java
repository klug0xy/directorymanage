package fr.amu.directorymanage.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.amu.directorymanage.beans.PasswordResetToken;
import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.business.IDirectoryManager;
import fr.amu.directorymanage.business.MailService;
import fr.amu.directorymanage.dao.PasswordResetTokenRepository;
import fr.amu.directorymanage.exceptions.PersonNotFoundException;

@Controller()
@RequestMapping("")
public class AuthenticationController {
	
	@Autowired
	IDirectoryManager directoryManager;
	@Autowired
	private MailService mailService;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	private JavaMailSender mailSender;
	
	protected final Log logger = LogFactory.getLog(getClass());

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

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database "
				+ "Authentication");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");
		return model;

	}

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
	
	@RequestMapping(value = "/recover", method = RequestMethod.GET)
	public ModelAndView recoverPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("recover");
		return model;
		
	}
	
	@RequestMapping(value="/recover" , method=RequestMethod.POST)
	public ModelAndView resetRequest(@RequestParam(value="email") String personEmail)
	{
		ModelAndView model = new ModelAndView();
		//check if the email id is valid and registered with us.
		try {
			
			Person person = directoryManager.getPersonByEmail(personEmail);
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
	
	@RequestMapping(value = "/newpassword", method = RequestMethod.POST)
	public ModelAndView newPasswordPost(@RequestParam(value = "password1")
		String password1, @RequestParam(value = "password2") String password2,
		@RequestParam(value = "personId") Long personId){
		
		ModelAndView model = new ModelAndView();
		
		if (password1.equals(password2) && !(password1.isEmpty()) && 
				!(password2.isEmpty()) && personId != null) {
			directoryManager.updatePersonPasswordById(personId, password1);
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

	// for 403 access denied page
	@RequestMapping(value = "/errors/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
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
