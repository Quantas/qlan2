package com.quantasnet.qlan2.organization;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantasnet.qlan2.user.User;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	public Set<Organization> getUsersOrgs(final User user) {
		return organizationRepository.findOrganizationsByUser(user);
	}
	
	public Organization getOrgByUserAndId(final User user, final Long id) {
		return organizationRepository.findOrgByUserAndId(user, id);
	}
	
	public Organization getOrgById(final Long id) {
		return organizationRepository.findOne(id);
	}
	
	public Organization save(final Organization org) {
		return organizationRepository.save(org);
	}
}
