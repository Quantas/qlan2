package com.quantasnet.qlan2.organization;

import com.quantasnet.qlan2.user.User;
import com.quantasnet.qlan2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private OrganizationMemberRepository orgMemberRepository;

	@Autowired
	private UserService userService;

	public List<Organization> getUsersOrgs(final User user) {
		return userService.getUserById(user.getId())
				.getOrganizations()
				.stream()
				.map(OrganizationMember::getOrg)
				.collect(Collectors.toList());
	}
	
	public List<Organization> getAllOrgs() {
		return organizationRepository.findAll();
	}
	
	public Organization getOrgById(final Long id) {
		return organizationRepository.findOne(id);
	}

	@Transactional
	public Organization save(final Organization org) {
		return organizationRepository.save(org);
	}

	@Transactional
	public void createOrganization(final Organization org, final User user) {
		this.save(org);
		addOrgMember(org, user, true);
	}

	@Transactional
	public OrganizationMember addOrgMember(final Long orgId, final User user) {
		final Organization org = organizationRepository.findOne(orgId);

		for (final OrganizationMember member : org.getMembers()) {
			if (member.getUser().getId().equals(user.getId())) {
				return member;
			}
		}

		return addOrgMember(org, user, false);
	}

	@Transactional
	public OrganizationMember addOrgMember(final Organization org, final User user, final boolean staff) {
		final OrganizationMember member = new OrganizationMember();
		if (staff) {
			member.setRole("Admin");
		} else {
			member.setRole("User");
		}
		member.setStaff(staff);
		member.setUser(user);
		member.setOrg(org);
		return orgMemberRepository.save(member);
	}

	@Transactional
	public void removeOrgMember(final OrganizationMember member) {
		member.getOrg().getMembers().remove(member);
		removeUserFromOrgEvents(member);
	}

	@Transactional
	public void removeOrgMember(final Long orgId, final User user) {
		for (final Organization org : getUsersOrgs(user)) {
			if (org.getId().equals(orgId)) {
				for (final OrganizationMember member : org.getMembers()) {
					if (member.getUser().getId().equals(user.getId())) {
						member.getOrg().getMembers().remove(member);
						removeUserFromOrgEvents(member);
						return;
					}
				}
			}
		}
	}

	private void removeUserFromOrgEvents(final OrganizationMember member) {
		member.getOrg().getEvents().forEach(event -> event.getUsers().remove(member.getUser()));
	}
}
