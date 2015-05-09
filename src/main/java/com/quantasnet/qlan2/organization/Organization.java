package com.quantasnet.qlan2.organization;

import com.quantasnet.qlan2.event.Event;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "description")
	private String description;

	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "org")
	private Set<OrganizationMember> members;

	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "org")
	private Set<Event> events;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<OrganizationMember> getMembers() {
		return members;
	}

	public void setMembers(Set<OrganizationMember> members) {
		this.members = members;
	}
	
	public Set<Event> getEvents() {
		return events;
	}
	
	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public boolean isUserStaff(final Long id) {
		for (final OrganizationMember member : members) {
			if (member.getUser().getId().equals(id)) {
				return member.isStaff();
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().isAssignableFrom(this.getClass())) {
			return ((Organization) obj).getId().equals(this.id);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return null == id ? -1 : id.intValue();
	}
}
