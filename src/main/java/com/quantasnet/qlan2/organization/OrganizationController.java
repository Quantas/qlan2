package com.quantasnet.qlan2.organization;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quantasnet.qlan2.security.HasUserRole;
import com.quantasnet.qlan2.user.User;

@Controller
@RequestMapping("/org")
public class OrganizationController {

	private static final String ORG_FORM = "orgForm";
	
	@Autowired
	private OrganizationService orgService;
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String home(@AuthenticationPrincipal final User user, final Model model) {
		if (null == user) {
			model.addAttribute("allOrgs", orgService.getAllOrgs());
		} else {
			final List<Organization> allOrgs = orgService.getAllOrgs();
			final List<Organization> myOrgs = orgService.getUsersOrgs(user);
			
			allOrgs.removeAll(myOrgs);
			
			model.addAttribute("allOrgs", allOrgs);
			model.addAttribute("orgs", myOrgs);
		}
		
		return "org/home";
	}
	
	@RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
	public String single(@PathVariable final Long orgId, final Model model) {
		model.addAttribute("org", orgService.getOrgById(orgId));
		return "org/single";
	}
	
	@HasUserRole
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEvent(final Model model) {
        if (!model.containsAttribute(ORG_FORM)) {
            model.addAttribute(ORG_FORM, new Organization());
        }
        return "org/create";
    }

    @HasUserRole
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createEvent(@Valid final Organization orgForm, final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final User user) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + ORG_FORM, bindingResult);
            redirectAttributes.addFlashAttribute(ORG_FORM, orgForm);
            return "redirect:/event/new";
        }
        
        orgService.save(orgForm, user);
        return "redirect:/org";
    }
	
}
