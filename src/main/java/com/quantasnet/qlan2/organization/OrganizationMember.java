package com.quantasnet.qlan2.organization;

import javax.persistence.*;

import com.quantasnet.qlan2.user.User;

@Entity
public class OrganizationMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "staff")
	private boolean staff;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "org_id", nullable = false)
	private Organization org;

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
}