package com.quantasnet.qlan2.organization;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quantasnet.qlan2.user.User;

@Component
public class OrganizationGateKeeper {

	@Autowired
	private OrganizationService orgService;
	
	public Optional<Organization> hasPermissionToDo(final User user, final Long orgId, final boolean staff) {
		final Organization theOrg = orgService.getOrgByUserAndId(user, orgId);
		
    	if (null != theOrg) {
    		for (final OrganizationMember orgMember : theOrg.getMembers()) {
    			if (orgMember.getUser().getId().equals(user.getId())) {
    				if (staff) {
    					if (orgMember.isStaff()) {
    						return Optional.of(theOrg);
    					}
    				} else {
    					return Optional.of(theOrg);
    				}
    			}
    		}
    	}
    	
    	return Optional.empty();
	}
	
}
