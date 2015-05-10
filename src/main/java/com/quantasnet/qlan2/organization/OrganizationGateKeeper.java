package com.quantasnet.qlan2.organization;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.quantasnet.qlan2.user.User;

@Component
public class OrganizationGateKeeper {

	public boolean hasPermissionToDo(final User user, final Long orgId, final boolean staff) {
		final Optional<OrganizationMember> org =
				user
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
						return true;
					}
				} else {
					return true;
				}
			}
    	}
    	
    	return false;
	}
	
}
