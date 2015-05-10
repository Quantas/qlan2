package com.quantasnet.qlan2.organization;

import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

interface OrganizationRepository extends JpaRepository<Organization, Long> {

	@QueryHints({@QueryHint(name="org.hibernate.cacheable", value = "true")})
	@Query("SELECT o FROM Organization o")
	Set<Organization> getAllOrgs();
	
	@QueryHints({@QueryHint(name="org.hibernate.cacheable", value = "true")})
	@Query("SELECT o FROM OrganizationMember o WHERE o.user.id = ?1")
	Set<OrganizationMember> getUserOrgs(Long userId);
	
}
