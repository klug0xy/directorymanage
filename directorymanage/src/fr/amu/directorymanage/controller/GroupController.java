package fr.amu.directorymanage.controller;

import java.text.ParseException;
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
import fr.amu.directorymanage.beans.User;
import fr.amu.directorymanage.business.IDirectoryManager;

@Controller()
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	IDirectoryManager directoryManager;
	
	@Autowired
	User user;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "")
	public ModelAndView group() {
		logger.info("Get group.jsp");
		return new ModelAndView("group");
	}
	
	@ModelAttribute
	public Map<Long, String> groupNames() {
		return directoryManager.getGroupNames();
	}
	
	@RequestMapping(value = "/findallgroups")
	public ModelAndView findAllGroups() {
		logger.info("Find all groups");
		return new ModelAndView("groupshow", "groupNames", groupNames());
	}
	
	 @RequestMapping(value = "/findonegroup", method = RequestMethod.POST)
	 public ModelAndView findOneGroup(@RequestParam Long groupId) {

		 logger.info("Find "+groupNames().get(groupId) +" group");
		 return new ModelAndView("groupshow", "oneGroup", 
			 directoryManager.findGroup(user, groupId));
	 }
	 
	 @ModelAttribute("groupForm")
		public Group newGroup(/*@RequestParam(value = "id", required = false) 
		Long personId*/) throws ParseException{
			// if (personId != null) {
			// logger.info("find person " + personId);
			// return dirmang.findPerson(user, personId);
			// }
			Group group = new Group();
			group.setId(new Long(0));
			group.setName("");
			return group;
		}
	 
	 @RequestMapping(value = "/addgroup", method = RequestMethod.GET)
		public String addGroupGet(Model model) throws ParseException {
			
			model.addAttribute("groupForm", newGroup());
			return "addgroup";
		}

		@RequestMapping(value = "/addgroup", method = RequestMethod.POST)
		public String addGroupPost(@ModelAttribute("groupForm") @Valid Group group, 
				BindingResult result) {
			
			if (result.hasErrors()) {
				return "addgroup";
			}
			logger.info("Id for group " + group.getName() + " is "
					+group.getId());
			int n = directoryManager.saveGroupAuto(group);
			logger.info(n + " row(s) inserted in table Groupe");
			return "redirect:findallgroups";
		}
		
		@RequestMapping(value = "/updategroup", method = RequestMethod.GET)
		public ModelAndView editGroup(@RequestParam("groupId") Long groupId ) {
			return new ModelAndView("updategroup", "group", 
					directoryManager.findGroup(user, groupId));
		}
		
		@RequestMapping(value = "/updategroup", method = RequestMethod.POST)
		public String editGroupPost(@ModelAttribute("groupForm") @Valid Group group, 
				BindingResult result) {
			
			if (result.hasErrors()) {
				return "updategroup";
			}
			logger.info("Id for group " + group.getName() + " is "
					+group.getId());
			int n = directoryManager.updateGroup(group);
			logger.info(n + " row(s) modified in table Groupe");
			return "redirect:findallgroups";
		}
		
		
		@RequestMapping(value = "/removeonegroup/{groupId}",
				method = RequestMethod.GET)
		public String removeOneGroup(@PathVariable("groupId") Long groupId) {
			int n = directoryManager.removeOneGroup(groupId);
			logger.info(n + " deleted group(s) " + groupId + "");
			return "redirect:../findallgroups";
		}
		
		@RequestMapping(value = "/removeallgroups", method = RequestMethod.GET)
		public String removeAllGroups() {
			int n = directoryManager.removeAllGroups();
			logger.info(n + " deleted all groups");
			return "redirect:findallgroups";
		}

}
