/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.amu.directorymanage.beans.Group;
import fr.amu.directorymanage.dao.IDirectoryManagerGroup;

/**
 * 
 * Classe controller pour l'annuaire des groupes
 * recupere tous les chemin sous /group
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

@Controller()
@RequestMapping("/group")
public class GroupController {
	
//	@Autowired
//	IDirectoryManager directoryManager;
	
	@Autowired
	IDirectoryManagerGroup directoryManagerGroup;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * la methode qui renvoie la page principale de l'annuaire groupe
	 * @return objet de type ModelAndView, qui contient la page group
	 */
	@RequestMapping(value = "")
	public ModelAndView group() {
		logger.info("Get group.jsp");
		return new ModelAndView("group");
	}
	
	/**
	 * la methode qui definit un ModelAttribute qui renvoi 
	 * tous les noms des groupes
	 * @return objet de type Map qui contient les noms des groupes
	 *  references par leurs identifiants
	 */
	@ModelAttribute
	public Map<Long, String> groupNames() {
		return directoryManagerGroup.getGroupNames();
	}
	
	/**
	 * renvoi tous les groupes existent dans la base de donnees
	 *  par des lots de 10
	 * @param offset un entier qui definit le decalage pour la pagination 
	 * @return objet de type ModelAndView, qui contient la page groupshow
	 */
	@RequestMapping(value = "/actions/findallgroups", 
			method = RequestMethod.GET)
	public ModelAndView findAllGroups(@RequestParam (value = "offset", 
	required = false) Integer offset) {
		
		ModelAndView model = new ModelAndView();
		if (offset == null) offset = new Integer(0);
		Integer maxRows = new Integer(10);
		Integer count = new Integer(0);
		count = directoryManagerGroup.countGroups();
		model.addObject("offset", offset);
		model.addObject("maxRows", maxRows);
		model.addObject("count", count);
		model.addObject("limitGroups", directoryManagerGroup.getLimitGroupNames
				(offset, maxRows));
		model.setViewName("groupshow");
		logger.info("Find all groups");
		return model;
	}
	
	/**
	 * renvoi un groupe recherche par son nom
	 * @param groupName chaine de caractere contenant le nom du groupe recherche
	 * @return objet de type ModelAndView, qui contient la page groupshow
	 */
	@RequestMapping(value = "/actions/findonegroup", 
		 method = RequestMethod.POST)
	public ModelAndView findOneGroup(@RequestParam String groupName) {
		 
		Collection<Group> groups;
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("groupName", groupName);
		groups = directoryManagerGroup.findGroupsByName(groupName);
		modelAndView.addObject("groups", groups);
		modelAndView.setViewName("groupshow");
		logger.info("Find groups that contains"+groupName);
		return modelAndView;
	 }
	
	/**
	 * un model attribute au nom groupForm sert a l'insertion d'un groupe
	 * @return un objet group injecter dans la vue au nom "groupForm"
	 * @throws ParseException ParseException
	 */
	@ModelAttribute("groupForm")
	public Group newGroup() throws ParseException{
		Group group = new Group();
		group.setId(new Long(0));
		group.setName("");
		return group;
	}
	
	/**
	 * rend le formulaire d'ajout d'un groupe rempli par des valeurs par defaut
	 * @param model Model
	 * @return la vue au nom addfgroup (refernce a la page addgroup.jsp)
	 * @throws ParseException ParseException
	 */
	@RequestMapping(value = "/actions/addgroup", method = RequestMethod.GET)
	public String addGroupGet(Model model) throws ParseException {
		
		model.addAttribute("groupForm", newGroup());
		return "addgroup";
	}

	/**
	 * insere un group dans la base de donnees si il est valide, 
	 * renvoi un message d'erreur sinon
	 * @param group l'objet Group recupere apres la soumission
	 * dans le formulaire d'insertion du groupe 
	 * @param result objet BindingResult recupere apres la soumission
	 * dans le formulaire d'insertion du groupe sert la liaison entre le controlleur
	 * et la vue afin de gerer les erreurs ou autre 
	 * @return redirection vers la page findallgroups si l'insertion est validee
	 */
	@RequestMapping(value = "/actions/addgroup", 
				method = RequestMethod.POST)
	public String addGroupPost(@ModelAttribute("groupForm") 
	@Valid Group group, BindingResult result) {
		
		if (result.hasErrors()) {
			return "addgroup";
		}
		logger.info("Id for group " + group.getName() + " is "
				+group.getId());
		int n = directoryManagerGroup.saveGroupAuto(group);
		logger.info(n + " row(s) inserted in table Groupe");
		return "redirect:findallgroups";
	}
	
	/**
	 * renvoi la page d'ajout d'un groupe, reponse a la methode GET http
	 * @param groupId Long qui definit l'identifiant 
	 * de la personne a mettre a jour
	 * @return un objet de type ModelAndView contenant la vue updategroup
	 * et le group a mettre a jour injecte
	 */
	@RequestMapping(value = "/actions/updategroup", 
			method = RequestMethod.GET)
	public ModelAndView editGroup(@RequestParam("groupId") Long groupId ) {
		return new ModelAndView("updategroup", "group", 
				directoryManagerGroup.findGroup(groupId));
	}
	
	/**
	 * renvoi la page d'ajout d'un groupe, reponse a la methode POST http
	 * si des erreurs sont produites lors de la saisie, met a jour le groupe
	 * concerne et redirige vers la vue findallgroups sinon.
	 * @param group Long qui stocke l'identifiant du groupe a mettre a jour 
	 * @param result objet BindingResult recupere apres la soumission
	 * dans le formulaire de mise a jour du groupe sert la liaison entre le 
	 * controlleur et la vue afin de gerer les erreurs ou autre
	 * @return String contenant le nom de la vue retournee (ou une redirection)
	 */
	@RequestMapping(value = "/actions/updategroup", 
			method = RequestMethod.POST)
	public String editGroupPost(@ModelAttribute("groupForm") 
	@Valid Group group, BindingResult result) {
		
		if (result.hasErrors()) {
			return "updategroup";
		}
		logger.info("Id for group " + group.getName() + " is "
				+group.getId());
		int n = directoryManagerGroup.updateGroup(group);
		logger.info(n + " row(s) modified in table Groupe");
		return "redirect:findallgroups";
	}
	
	/**
	 * supprime un groupe de la base de donnees
	 * NOTE : pas de verification si le groupe existe ou pas
	 * @param groupId Long qui contient l'identifiant du groupe concerne
	 * @return String contenant le nom de la vue retournee (ou une redirection)
	 */
	@RequestMapping(value = "/actions/removeonegroup/{groupId}",
			method = RequestMethod.GET)
	public String removeOneGroup(@PathVariable("groupId") Long groupId) {
		int n = directoryManagerGroup.removeOneGroup(groupId);
		logger.info(n + " deleted group(s) " + groupId + "");
		return "redirect:../findallgroups";
	}
	
	/**
	 * supprime tous les groupes de la base de donnees
	 * NOTE : pas de verification si y en a des groupes dans la base de donnees
	 * @return String contenant le nom de la vue retournee (ou une redirection)
	 * avec le mot 'redirect:...'
	 */
	@RequestMapping(value = "/actions/removeallgroups", 
			method = RequestMethod.GET)
	public String removeAllGroups() {
		int n = directoryManagerGroup.removeAllGroups();
		logger.info(n + " deleted all groups");
		return "redirect:findallgroups";
	}
}