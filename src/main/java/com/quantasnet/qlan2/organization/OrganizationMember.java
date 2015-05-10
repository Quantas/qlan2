package com.quantasnet.qlan2.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.quantasnet.qlan2.event.Event;
import com.quantasnet.qlan2.user.User;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class OrganizationMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)	
	@ManyToOne
	private User user;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "staff")
	private boolean staff;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@ManyToOne(fetch = FetchType.EAGER)
	private Organization org;
	
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Event> events = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStaff() {
		return staff;
	}

	public void setStaff(boolean staff) {
		this.staff = staff;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
	public Set<Event> getEvents() {
		return events;
	}
	
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().isAssignableFrom(this.getClass())) {
			final Long objId = ((OrganizationMember) obj).getId();
			return null == objId ? false : objId.equals(this.id);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return null == id ? -1 : id.intValue();
	}
	
}