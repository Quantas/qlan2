package com.quantasnet.qlan2.organization;

import java.util.Optional;

import com.quantasnet.qlan2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quantasnet.qlan2.user.User;

@Component
public class OrganizationGateKeeper {

	@Autowired
	private UserService userService;

	public Optional<Organization> hasPermissionToDo(final User user, final Long orgId, final boolean staff) {
		final Optional<OrganizationMember> org =
				userService.getUserById(user.getId())
						.getOrganizations()
						.stream()
						.filter(o -> o.getOrg().getId().equals(orgId))
						.findFirst();

    	if (org.isPresent()) {
			final Organization theOrg = org.get().getOrg();
			final Optional<OrganizationMember> member =
					theOrg.getMembers()
							.stream()
							.filter(m ->m.getUser().getId().equals(user.getId()))
							.findFirst();

			if (member.isPresent()) {
				if (staff) {
					if (member.get().isStaff()) {
						return Optional.of(theOrg);
					}
				} else {
					return Optional.of(theOrg);
				}
			}
    	}
    	
    	return Optional.empty();
	}
	
}
