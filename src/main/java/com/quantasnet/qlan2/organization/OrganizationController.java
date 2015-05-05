package com.quantasnet.qlan2.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quantasnet.qlan2.user.User;

@Controller
@RequestMapping("/org")
public class OrganizationController {

	@Autowired
	private OrganizationService orgService;
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String home(@AuthenticationPrincipal final User user, final Model model) {
		if (null == user) {
			
		} else {
			model.addAttribute("orgs", orgService.getUsersOrgs(user));
		}
		
		return "org/home";
	}
	
	@RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
	public String single(@PathVariable final Long orgId, final Model model) {
		model.addAttribute("org", orgService.getOrgById(orgId));
		return "org/single";
	}
	
}
