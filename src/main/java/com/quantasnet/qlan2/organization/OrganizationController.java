package com.quantasnet.qlan2.organization;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
			final Set<OrganizationMember> myOrgs = orgService.getUserOrgs(user);
			final Set<Organization> notMyOrgs = new HashSet<>();
			
			for (final Organization org : orgService.getAllOrgs()) {
				boolean contains = false;
				for (final OrganizationMember member : myOrgs) {
					if (org.getMembers().contains(member)) {
						contains = true;
						break;
					}
				}
				
				if (!contains) {
					notMyOrgs.add(org);
				}
				
			}
			
			model.addAttribute("allOrgs", notMyOrgs);
			model.addAttribute("orgs", myOrgs);
		}
		
		return "org/home";
	}
	
	@RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
	public String single(@PathVariable final Long orgId, final Model model) {
		final Optional<Organization> org = orgService.getOrgById(orgId);

		if (org.isPresent()) {
			model.addAttribute("org", org.get());
			return "org/single";
		}

		return "forward:/org/notFound";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@RequestMapping(value = "/notFound")
	public String notFound() {
		return "org/notFound";
	}

	@HasUserRole
	@RequestMapping(value = "/join/{orgId}", method = RequestMethod.GET)
	public String joinOrg(@PathVariable final Long orgId, @AuthenticationPrincipal final User user) {
		orgService.addOrgMember(orgId, user);
		return "redirect:/org/" + orgId;
	}

	@HasUserRole
	@RequestMapping(value = "/leave/{orgId}", method = RequestMethod.GET)
	public String leaveOrg(@PathVariable final Long orgId, @AuthenticationPrincipal final User user) {
		orgService.removeOrgMember(orgId, user);
		return "redirect:/org";
	}

	@HasUserRole
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newOrg(final Model model) {
        if (!model.containsAttribute(ORG_FORM)) {
            model.addAttribute(ORG_FORM, new Organization());
        }
        return "org/create";
    }

    @HasUserRole
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrg(@Valid final Organization orgForm, final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final User user) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + ORG_FORM, bindingResult);
            redirectAttributes.addFlashAttribute(ORG_FORM, orgForm);
            return "redirect:/event/new";
        }
        
        orgService.createOrganization(orgForm, user);
        return "redirect:/org";
    }
	
}
