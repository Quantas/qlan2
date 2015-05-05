package com.quantasnet.qlan2.organization;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantasnet.qlan2.user.User;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	public List<Organization> getUsersOrgs(final User user) {
		return organizationRepository.findOrganizationsByUser(user);
	}
	
	public List<Organization> getAllOrgs() {
		return organizationRepository.findAll();
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
	
	public Organization save(final Organization org, final User user) {
		final OrganizationMember member = new OrganizationMember();
        member.setRole("Admin");
        member.setStaff(true);
        member.setUser(user);
        
        final Set<OrganizationMember> members = new HashSet<OrganizationMember>();
        members.add(member);
        
        org.setMembers(members);
		
		return this.save(org);
	}
}
