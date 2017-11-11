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
import fr.amu.directorymanage.beans.PersonMail;
import fr.amu.directorymanage.beans.PersonMailEditor;
import fr.amu.directorymanage.beans.User;
import fr.amu.directorymanage.business.JdbcDirectoryManager;

@Controller()
@RequestMapping("/user")
public class UserController {

	@Autowired
	JdbcDirectoryManager dirmang;
	@Autowired
	User user;
	Person p;

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "")
	public ModelAndView user() {
		logger.info("Get user.jsp");
		return new ModelAndView("user");
	}

	@RequestMapping(value = "/findallpersons")
	public ModelAndView findAllPersons() {
		logger.info("Find all persons in all groups");
		return new ModelAndView("usershow", "allPersons", persons());
	}

	@ModelAttribute("allPersons")
	public Collection<Person> persons() {
		return dirmang.findAllPersons();
	}
	
	@ModelAttribute("groupId")
	public void provideGroupId(Model model){
	      model.addAttribute("groupId", new Long(2));
	 }

	@RequestMapping(value = "/findallgrouppersons", method = RequestMethod.POST)
	public ModelAndView findAllGroupPersons(@RequestParam Long groupId) {
		logger.info("Find all persons for group id : " + groupId);
		return new ModelAndView("usershow", "allPersons", persons(user, groupId));
	}

	@ModelAttribute("allPersons")
	public Collection<Person> persons(User user, Long groupId) {
		return dirmang.findAllGroupPersons(user, groupId);
	}
	
	 @RequestMapping(value = "/findoneperson", method = RequestMethod.POST)
	 public ModelAndView findOne(@RequestParam Long personId) {
	 return new ModelAndView("userdetails", "onePerson", dirmang.findPerson(user, personId));
	 }
	 
	 @RequestMapping(value = "/userdetails/{personId}", method = RequestMethod.GET)
	 public ModelAndView userDetails(@PathVariable("personId") Long personId) {
	 return new ModelAndView("userdetails", "onePerson", dirmang.findPerson(user, personId));
	 }
	
//	 @ModelAttribute("allPersons")
//	 public Person onePerson(User user, Long id) {
//	 return dirmang.findPerson(user, id);
//	 }

	@RequestMapping(value = "/removeoneperson/{personId}",
			method = RequestMethod.GET)
	public ModelAndView removeOne(@PathVariable("personId") Long personId) {
		int n = dirmang.removeOnePerson(personId);
		logger.info(n + " deleted person(s) " + personId + "");
		return new ModelAndView("usershow", "allPersons", persons());
	}

	@ModelAttribute
	public Person newPerson(@RequestParam(value = "id", required = false) 
	Long personId) throws ParseException {
		// if (personId != null) {
		// logger.info("find person " + personId);
		// return dirmang.findPerson(user, personId);
		// }
		Person p = new Person();
		PersonMail pm = new PersonMail();
		p.setId(new Long(0));
		p.setFirstName("");
		p.setLastName("");
		String strDate = "30/07/1993";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		java.util.Date date = sdf.parse(strDate);
		java.sql.Date sqlDate = new Date(date.getTime());
		p.setBirthday(sqlDate);
		pm.setMail("controller@value.fr");
		p.setMail("controller@value.fr");
		p.setGroupId(new Long(0));
		logger.info("new person = " + p);
		return p;
	}

	@RequestMapping(value = "/addperson", method = RequestMethod.GET)
	public ModelAndView addPersonGet() {
		return new ModelAndView("addperson");
	}

	@RequestMapping(value = "/addperson", method = RequestMethod.POST)
	public String addPersonPost(@ModelAttribute @Valid Person p,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "addperson";
		}
		logger.info("Mail for person " + p.getFirstName() + " is "
				+p.getMail());
		int n = dirmang.savePerson(user, p);
		logger.info(n + " row(s) inserted");
		return "redirect:findallpersons";
	}
	
	@RequestMapping(value = "/addperson/{personId}", method = RequestMethod.GET)
	public ModelAndView editPerson(@PathVariable("personId") Long personId ) {
		return new ModelAndView("addperson");
	}


	@ModelAttribute("groupNames")
	public Map<Long, String> productTypes() {
		return dirmang.getGroupNames();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //yyyy-MM-dd'T'HH:mm:ssZ example
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "birthday", new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(PersonMail.class, "personMail", new PersonMailEditor());
	}
}
