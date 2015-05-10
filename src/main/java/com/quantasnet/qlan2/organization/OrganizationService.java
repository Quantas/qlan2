package com.quantasnet.qlan2.organization;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quantasnet.qlan2.user.User;

@Transactional
@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private OrganizationMemberRepository orgMemberRepository;
	
	public Set<Organization> getAllOrgs() {
		return organizationRepository.getAllOrgs();
	}
	
	public Set<OrganizationMember> getUserOrgs(final User user) {
		return organizationRepository.getUserOrgs(user.getId());
	}
	
	public Optional<Organization> getOrgById(final Long id) {
		return Optional.ofNullable(organizationRepository.findOne(id));
	}

	public void createOrganization(final Organization org, final User user) {
		org.setEvents(new HashSet<>());
		org.setMembers(new HashSet<>());
		addOrgMember(org, user, true);
		organizationRepository.save(org);
	}

	public void addOrgMember(final Long orgId, final User user) {
		final Organization org = organizationRepository.findOne(orgId);

		for (final OrganizationMember member : org.getMembers()) {
			if (member.getUser().getId().equals(user.getId())) {
				return;
			}
		}
		addOrgMember(org, user, false);
	}

	private void addOrgMember(final Organization org, final User user, final boolean staff) {
		final OrganizationMember member = new OrganizationMember();
		if (staff) {
			member.setRole("Admin");
		} else {
			member.setRole("User");
		}
		member.setStaff(staff);
		member.setUser(user);
		member.setOrg(org);
		
		user.getOrganizations().add(member);
		org.getMembers().add(member);
	}

	public void removeOrgMember(final Long orgId, final User user) {
		final Organization org = organizationRepository.findOne(orgId);
		if (org.getId().equals(orgId)) {
			for (final OrganizationMember member : org.getMembers()) {
				if (member.getUser().getId().equals(user.getId())) {
					org.getEvents().forEach(event -> event.getMembers().remove(member));
					org.getMembers().remove(member);
					return;
				}
			}
		}
	}
}
