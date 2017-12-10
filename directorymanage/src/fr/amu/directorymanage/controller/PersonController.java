/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import fr.amu.directorymanage.business.IPersonService;
import fr.amu.directorymanage.dao.IDirectoryManagerGroup;
import fr.amu.directorymanage.dao.IDirectoryManagerPerson;
import fr.amu.directorymanage.dao.IDirectoryManagerUserRoles;

/**
 * 
 * Classe controller pour l'annuaire des groupes
 * recupere tous les chemin sous /user
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Controller()
@RequestMapping("user")
public class PersonController {

//	@Autowired
//	IDirectoryManager directoryManager;
	
	@Autowired
	IDirectoryManagerGroup directoryManagerGroup;
	
	@Autowired
	IDirectoryManagerPerson directoryManagerPerson;
	
	@Autowired
	IDirectoryManagerUserRoles directoryManagerUserRoles;
	
	@Autowired
	IPersonService personService;
	
	Person person;
	
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * renvoi la page user.jsp par defaut
	 * @return objet de type ModelAndView, qui contient la vue user (user.jsp)
	 */
	@RequestMapping(value = "")
	public ModelAndView user() {
		logger.info("Get user.jsp");
		return new ModelAndView("user");
	}

	/**
	 * renvoi la vue de toutes les personnes existant dans la base de donnees
	 * par des lots de 10 par page
	 * @param offset un entier qui definit le decalage pour la pagination 
	 * @return objet de type ModelAndView, qui contient la vue userdetails,
	 * les objets injectes qui servent a la pagination et la liste de 10
	 * personnes 
	 */
	@RequestMapping(value = "/actions/findallpersons", 
			method = RequestMethod.GET)
	public ModelAndView findAllPersons(@RequestParam (value = "offset", 
	required = false) Integer offset) {
		ModelAndView model = new ModelAndView();
		if (offset == null) offset = new Integer(0);
		
		Integer maxRows = new Integer(10);
		Integer count = new Integer(0);
		count = directoryManagerPerson.countPersons();
		model.addObject("offset", offset);
		model.addObject("maxRows", maxRows);
		model.addObject("count", count);
		model.addObject("persons", directoryManagerPerson.findLimitPersons(offset,
				maxRows));
		model.setViewName("userdetails");
		logger.info("Find all persons in all groups");
		return model;
	}

	/**
	 * un model attribute au nom "allPersons" renvoi toutes les personnes
	 * de base de donnees
	 * @return Collection represente toutes les personnes trouvees
	 * dans la base de donnees
	 */
	@ModelAttribute("allPersons")
	public Collection<Person> persons() {
		return directoryManagerPerson.findAllPersons();
	}
	
	/**
	 * extrait l'identifiant du groupe par son nom
	 * renvoi la liste des personnes d'un groupe par l'identifiant et le nom
	 * de ce dernier
	 * @param groupName String contenant le nom du groupe recherche
	 * @param offset un entier qui definit le decalage pour la pagination 
	 * @param groupId Long indique l'identifiant du groupe recherche
	 * @return objet de type ModelAndView, qui contient la vue userdetails,
	 * les objets injectes qui servent a la pagination, la liste de 10
	 * personnes, le nom et l'identifiant du groupe 
	 */
	@RequestMapping(value = "/actions/findallgrouppersons", 
			method = { RequestMethod.POST, RequestMethod.GET})
	public ModelAndView findAllGroupPersons(@RequestParam (value = "groupName",
	required = false) String groupName, @RequestParam (value = "offset", 
	required = false) Integer offset, @RequestParam (value = "groupId", 
	required = false) Long groupId){
		
		ModelAndView model = new ModelAndView();
		Collection<Person> persons;
		if (offset == null) offset = new Integer(0);
		//extract groupName with groupId
		for ( Long key : groupNames().keySet() ){
			String actualGroupName = groupNames().get(key);
			logger.info("Actual group name : "+actualGroupName+" groupName = "
			+groupName);
			if (groupName != null && actualGroupName.contains
					(groupName.toUpperCase())){
				groupId = key;
			}
		}
		Integer maxRows = new Integer(10);
		Integer count = new Integer(0);
		count = directoryManagerPerson.countGroupPersons(groupId);
		model.addObject("offset", offset);
		model.addObject("maxRows", maxRows);
		model.addObject("count", count);
		if (groupName != null) {
			model.addObject("groupName", groupName);
		}
		if (groupId != null) {
			model.addObject("groupId", groupId);
		}
		else {
			logger.info("WARNING : groupId = "+groupId);
		}
		persons  = directoryManagerPerson.
				findLimitGroupPersonsByGroupName(groupName, groupId, offset,
						maxRows);
		model.addObject("persons", persons);
		model.addObject("groupNames", groupNames());
		model.setViewName("userdetails");
		logger.info("Find all persons for "+groupName+" group");
		return model;
	}
	
	/**
	 * un model attribute au nom "allPersonsGroup" renvoi toutes les personnes
	 * d'un groupe donne
	 * @return Collection represente toutes les personnes trouvees
	 * du groupe concerne
	 * @param groupId Long identifiant du groupe recherche 
	 * @return Collection contient toutes les personnes du groupe
	 */
	@ModelAttribute("allPersonsGroup")
	public Collection<Person> persons(Long groupId) {
		return directoryManagerPerson.findAllGroupPersonsById(groupId);
	}
	
	/**
	 * renvoi et affiche les details d'une personne apres la soumission, 
	 * reponse a la methode POST http
	 * @param personId Long identifiant de la personne recherche 
	 * @return objet de type ModelAndView, qui contient la vue userdetails 
	 * (userdetails.jsp) et l'objet Person injecte
	 */
	@RequestMapping(value = "/actions/findoneperson", method = 
		 RequestMethod.POST)
	public ModelAndView findOne(@RequestParam Long personId) {
		return new ModelAndView("userdetails", "onePerson",
				directoryManagerPerson.findPersonById(personId));
	}
	
	/**
	 * renvoi et affiche une ou plusieurs personne(s) recherche par mot cle
	 * @param personName String mot cle de recherche pour une ou plusieurs
	 * personnes 
	 * @return objet de type ModelAndView, qui contient la vue userdetails 
	 * (userdetails.jsp), la collection contenant la ou les personne(s) trouvees
	 *  et l'objet personName injectes
	 */
	@RequestMapping(value = "/actions/findpersons", method = 
		 RequestMethod.POST)
	public ModelAndView findPersons(@RequestParam String personName) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("persons", directoryManagerPerson.
				 findPersonByName(personName));
		modelAndView.addObject("personName", personName);
		modelAndView.setViewName("userdetails");
		return modelAndView;
	}
	
	/**
	 * affiche les details d'une personnes recherche par son identifiant
	 * @param personId Long identifiant de la personne recherche
	 * @return objet de type ModelAndView, qui contient la vue userdetails 
	 * (userdetails.jsp), l'objet de type Person trouvee
	 */
	@RequestMapping(value = "/actions/userdetails/{personId}", 
		 method = RequestMethod.GET)
	public ModelAndView userDetails(@PathVariable("personId") Long personId) {
	return new ModelAndView("userdetails", "onePerson", 
		 directoryManagerPerson.findPersonById(personId));
	}
	
	/**
	 * supprime toutes les personnes existant dans la base de donnees
	 * et renvoi la vue user si la suppression est bien passee
	 * NOTE : pas de verification s'il existe des personnes 
	 * @return objet de type ModelAndView, qui contient la vue user (user.jsp),
	 */
	@RequestMapping(value = "/actions/removeallpersons",
			method = RequestMethod.GET)
	public ModelAndView removeAllPersons() {
		int n = directoryManagerPerson.removeAllPersons();
		logger.info(n + " deleted all persons from all groups ");
		return new ModelAndView("user");
	}
	
	/**
	 * supprime toutes les personnes d'un groupe par l'identifiant
	 * de ce dernier et renvoi la vue user si la suppression est bien passee
	 * NOTE : pas de verification s'il existe des personnes 
	 * @param groupId Long identifiant du groupe concerne
	 * @return objet de type ModelAndView, qui contient la vue user (user.jsp),
	 */
	@RequestMapping(value = "/actions/removeallpersonsgroup/{groupId}",
				method = RequestMethod.GET)
	public ModelAndView removeAllPersonsGroup(@PathVariable("groupId") 
	Long groupId) {
		int n = directoryManagerPerson.removeAllPersonsGroup(groupId);
		logger.info(n + " deleted all persons from group " + 
		groupNames().get(groupId) + "");
		return new ModelAndView("user");
	}

	/**
	 * supprime une personne par son identifiant et renvoi la vue user si
	 * la suppression a eu lieu
	 * @param personId Long identifiant de la personne qu'on souhaite supprimer
	 * @return objet de type ModelAndView, qui contient la vue user (user.jsp),
	 */
	@RequestMapping(value = "/actions/removeoneperson/{personId}",
			method = RequestMethod.GET)
	public ModelAndView removeOne(@PathVariable("personId") Long personId) {
		int n = directoryManagerPerson.removeOnePerson(personId);
		logger.info(n + " deleted person(s) " + personId + "");
		return new ModelAndView("user");
	}

	/**
	 * model attribute au nom "personForm" chargé au moment d'insertion d'une
	 * personne 
	 * @return Person charge au moment de l'insertion 
	 * @throws ParseException ParseException
	 */
	@ModelAttribute("personForm")
	public Person newPerson() throws ParseException {
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

	/**
	 * renvoi le formulaire d'insertion d'une personne, la vue addperson
	 * (addperson.jsp),  reponse a la methode GET http 
	 * @param model objet de type Model sert a l'injection l'objet Person
	 * cree par defaut dans le formulaire
	 * @return String contenant le nom de la vue reference
	 * a la page addperson.jsp
	 * @throws ParseException ParseException
	 */
	@RequestMapping(value = "/actions/addperson", method = RequestMethod.GET)
	public String addPersonGet(Model model) throws ParseException {
		
		model.addAttribute("personForm", newPerson());
		return "addperson";
	}

	/**
	 * recupere la personne a inserer et verifie s'il est valide ou nn,
	 * dans le cas d'erreur la meme page d'insertion est renvoye 
	 * sinon une redirection vers la page userdetails pour afficher les details
	 * de la nouvelle personne inseree
	 * @param person Person a inseree
	 * @param result objet BindingResult recupere apres la soumission
	 * dans le formulaire d'insertion du groupe, sert la liaison entre le controlleur
	 * et la vue afin de gerer les erreurs ou autre 
	 * @return String mentionnant le nom de la vue retournee
	 */
	@RequestMapping(value = "/actions/addperson", method = RequestMethod.POST)
	public String addPersonPost(@ModelAttribute("personForm") 
								@Valid Person person, BindingResult result) {
		
		if (result.hasErrors()) {
			return "addperson";
		}
		logger.info("Mail for person " + person.getFirstName() + " is "
				+person.getMail());
		int n = directoryManagerPerson.savePersonAuto(person);
		logger.info(n + " row(s) inserted");
		return "redirect:userdetails/"+person.getId();
	}
	
	/**
	 * edit une personne via un formulaire, verifie si y en a une personne
	 * authentifie, verfie si la cette personne a le droit de modifier
	 * les informations de la personne concerne,  
	 * @param personId Long identifiant de la personne concernee 
	 * @param personMail String le mail de la personne concernee 
	 * @return objet de type ModelAndView contenant la vue du formulaire
	 * si la personne qui tente a le droit, la page 403 sinon
	 */
	@RequestMapping(value = "/actions/editperson", method = RequestMethod.GET)
	public ModelAndView editPerson(@RequestParam(value = "personId", 
	required = false) Long personId,  @RequestParam(value = "personMail", 
	required = false) String personMail) {
		ModelAndView model = new ModelAndView();
		String username = "";
		Long authUserId = new Long(0);
		Authentication auth = SecurityContextHolder.getContext().
				getAuthentication();
		if ( (auth.getPrincipal() != null) && (auth.getPrincipal() 
				instanceof UserDetails) ) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			username = userDetail.getUsername();
		}
		if (username != "" && username != null) {
			person = directoryManagerPerson.getPersonByEmail(username);
			authUserId = person.getId();
		}
		if (personMail != null) personId = person.getId();
		if (personId == authUserId || personService.hasRole("ROLE_ADMIN")) {
			model.addObject("person", directoryManagerPerson.
					findPersonById(personId));
			model.setViewName("editperson");
		}
		else if (personId != authUserId) {
			model.addObject("username", username);	
			model.setViewName("403");
		}
		return model;
		
	}
	
	/**
	 * editer une personne, reponse a la methode POST http,
	 * modifier succesivement la colonne Username de la table User_roles
	 * @param person Person recupere lors de la modification, valide
	 * @param result objet BindingResult recupere apres la soumission
	 * dans le formulaire d'insertion du groupe, sert la liaison entre le controlleur
	 * et la vue afin de gerer les erreurs ou autre 
	 * @return String contenant le nom de la vue ou une redirection
	 */
	@RequestMapping(value = "/actions/editperson", method = RequestMethod.POST)
	public String editPersonPost(@ModelAttribute @Valid Person person, 
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "editperson";
		}
		
		int personModifiedRows = directoryManagerPerson.updatePerson(person);
		int userRolesModifiedRows = directoryManagerUserRoles.updateUsernameUserRoles(person);
		logger.info("Person " + person.getId() + " is Modified");
		logger.info(personModifiedRows + " row(s) modified in table Person");
		logger.info(userRolesModifiedRows + " row(s) modified in table User_roles");
		return "redirect:findallpersons";
	}

	/**
	 * la methode qui definit un ModelAttribute qui renvoi 
	 * tous les noms des groupes
	 * @return objet de type Map qui contient les noms des groupes
	 *  references par leurs identifiants
	 */
	@ModelAttribute("groupNames")
	public Map<Long, String> groupNames() {
		return directoryManagerGroup.getGroupNames();
	}

	/**
	 * sert a faire initialiser la liaison entre la vue et le controlleur
	 * de facon personaliser quelques format des champs saisies 
	 * @param binder WebDataBinder objet de la liaison, permet d'enregistrer 
	 * les editeurs personnalises
	 */
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
