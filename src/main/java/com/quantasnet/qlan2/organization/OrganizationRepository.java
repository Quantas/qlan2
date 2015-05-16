package com.quantasnet.qlan2.organization;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.Set;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {

	@QueryHints({@QueryHint(name="org.hibernate.cacheable", value = "true")})
	@Query("SELECT o FROM Organization o")
	Set<Organization> getAllOrgs();
	
	@QueryHints({@QueryHint(name="org.hibernate.cacheable", value = "true")})
	@Query("SELECT o FROM OrganizationMember o WHERE o.user.id = ?1")
	Set<OrganizationMember> getUserOrgs(Long userId);
	
}
