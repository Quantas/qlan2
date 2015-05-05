package com.quantasnet.qlan2.organization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quantasnet.qlan2.user.User;

interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	@Query("SELECT o FROM Organization o JOIN o.members m WHERE m.user = ?1")
	List<Organization> findOrganizationsByUser(User user);
	
	@Query("SELECT o FROM Organization o JOIN o.members m WHERE m.user = ?1 AND o.id = ?2")
	Organization findOrgByUserAndId(User user, Long id);
	
}
