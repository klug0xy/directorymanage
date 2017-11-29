package fr.amu.directorymanage.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.amu.directorymanage.beans.Person;
import fr.amu.directorymanage.beans.User;
import fr.amu.directorymanage.business.IDirectoryManager;

@Controller()
@RequestMapping("user")
public class UserController {

	@Autowired
	IDirectoryManager directoryManager;
	@Autowired
	User user;
	
	Person person;
	
	SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler
	= new SimpleUrlAuthenticationSuccessHandler();
	
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "")
	public ModelAndView user() {
		logger.info("Get user.jsp");
		return new ModelAndView("user");
	}

	@RequestMapping(value = "/actions/findallpersons")
	public ModelAndView findAllPersons() {
		logger.info("Find all persons in all groups");
		return new ModelAndView("usershow", "allPersons", persons());
	}

	@ModelAttribute("allPersons")
	public Collection<Person> persons() {
		return directoryManager.findAllPersons();
	}
	
	@RequestMapping(value = "/actions/findallgrouppersons", 
			method = RequestMethod.POST)
	public ModelAndView findAllGroupPersons(@RequestParam Long groupId) {
		logger.info("Find all persons for group id : " + groupId);
		return new ModelAndView("usershow", "allPersons", 
				persons(user, groupId));
	}

	@ModelAttribute("allPersons")
	public Collection<Person> persons(User user, Long groupId) {
		return directoryManager.findAllGroupPersons(user, groupId);
	}
	
	 @RequestMapping(value = "/actions/findoneperson", method = RequestMethod.POST)
	 public ModelAndView findOne(@RequestParam Long personId) {
	 return new ModelAndView("userdetails", "onePerson", 
			 directoryManager.findPerson(user, personId));
	 }
	 
	 @RequestMapping(value = "/actions/userdetails/{personId}", 
			 method = RequestMethod.GET)
	 public ModelAndView userDetails(@PathVariable("personId") Long personId) {
	 return new ModelAndView("userdetails", "onePerson", 
			 directoryManager.findPerson(user, personId));
	 }
	 
	 @RequestMapping(value = "/actions/removeallpersons",
				method = RequestMethod.GET)
		public ModelAndView removeAllPersons() {
			int n = directoryManager.removeAllPersons();
			logger.info(n + " deleted all persons from all groups ");
			return new ModelAndView("user"/*, "allPersons", persons()*/);
	 	}
	 
	 @RequestMapping(value = "/actions/removeallpersonsgroup/{groupId}",
				method = RequestMethod.GET)
		public ModelAndView removeAllPersonsGroup(@PathVariable("groupId")
													Long groupId) {
			int n = directoryManager.removeAllPersonsGroup(groupId);
			logger.info(n + " deleted all persons from group " + 
			groupNames().get(groupId) + "");
			return new ModelAndView("user"/*, "allPersons", persons()*/);
		}

	@RequestMapping(value = "/actions/removeoneperson/{personId}",
			method = RequestMethod.GET)
	public ModelAndView removeOne(@PathVariable("personId") Long personId) {
		int n = directoryManager.removeOnePerson(personId);
		logger.info(n + " deleted person(s) " + personId + "");
		return new ModelAndView("user"/*, "allPersons", persons()*/);
	}

	@ModelAttribute("personForm")
	public Person newPerson(/*@RequestParam(value = "id", required = false) 
	Long personId*/) throws ParseException{
		// if (personId != null) {
		// logger.info("find person " + personId);
		// return dirmang.findPerson(user, personId);
		// }
		Person person = new Person();
		person.setId(new Long(0));
		person.setFirstName("");
		person.setLastName("");
		String strDate = "01/01/1969";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		java.util.Date date = sdf.parse(strDate);
		java.sql.Date sqlDate = new Date(date.getTime());
		person.setBirthday(sqlDate);
		person.setMail("controller@value.fr");
		person.setGroupId(new Long(0));
		logger.info("new person = " + person);
		return person;
	}

	@RequestMapping(value = "/actions/addperson", method = RequestMethod.GET)
	public String addPersonGet(Model model) throws ParseException {
		
		model.addAttribute("personForm", newPerson());
		return "addperson";
	}

	@RequestMapping(value = "/actions/addperson", method = RequestMethod.POST)
	public String addPersonPost(@ModelAttribute("personForm") 
								@Valid Person person, BindingResult result) {
		
		if (result.hasErrors()) {
			return "addperson";
		}
		logger.info("Mail for person " + person.getFirstName() + " is "
				+person.getMail());
		int n = directoryManager.savePersonAuto(person);
		logger.info(n + " row(s) inserted");
		return "redirect:userdetails/"+person.getId();
	}
	
	@RequestMapping(value = "/actions/editperson", method = RequestMethod.GET)
	public ModelAndView editPerson(@RequestParam("personId") Long personId ) {
		ModelAndView model = new ModelAndView();
		String username = "";
		Long authUserId = new Long(0);
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() != null) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			username = userDetail.getUsername();
		}
		if (username != "" && username != null) {
			person = directoryManager.getPersonByEmail(username);
			authUserId = person.getId();
		}
		if (personId == authUserId) {
			model.addObject("person", directoryManager.findPerson(user, personId));
			model.setViewName("editperson");
		}
		else if (personId != authUserId) {
			model.addObject("username", username);	
			model.setViewName("403");
		}
		return model;
		
	}
	
	@RequestMapping(value = "/actions/editperson", method = RequestMethod.POST)
	public String editPersonPost(@ModelAttribute @Valid Person person, 
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "editperson";
		}
		
		int n = directoryManager.updatePerson(person);
		logger.info("Person " + person.getId() + " is Modified");
		logger.info(n + " row(s) modified");
		return "redirect:userdetails/"+person.getId();
	}

	@ModelAttribute("groupNames")
	public Map<Long, String> groupNames() {
		return directoryManager.getGroupNames();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor customDateEditor1 = new CustomDateEditor
				(dateFormat1, false);
		CustomDateEditor customDateEditor2 = new CustomDateEditor
				(dateFormat2, false);
		CustomDateEditor birthdayEditor =  new CustomDateEditor
				(new BirthdayEditor(), false);
        binder.registerCustomEditor(Date.class, customDateEditor1);
        binder.registerCustomEditor(Date.class, "birthday", customDateEditor2);
        binder.registerCustomEditor(Date.class, birthdayEditor);
	}
}
