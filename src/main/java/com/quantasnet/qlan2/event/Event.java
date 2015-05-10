package com.quantasnet.qlan2.event;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.quantasnet.qlan2.organization.Organization;
import com.quantasnet.qlan2.organization.OrganizationMember;
import com.quantasnet.qlan2.user.User;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Event implements Serializable {

	private static final long serialVersionUID = -1470349927747123283L;

	@Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Event name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Start date is required.")
    @Column(name = "start_date", nullable = false)
    private DateTime start;

    @NotNull(message = "End date is required.")
    @Column(name = "end_date", nullable = false)
    private DateTime end;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id", nullable = false)
    private Set<OrganizationMember> members;
    
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="org_id", nullable = false)
    private Organization org;

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

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public Set<OrganizationMember> getMembers() {
        return members;
    }

    public void setMembers(Set<OrganizationMember> members) {
		this.members = members;
	}
    
    public Organization getOrg() {
		return org;
	}
    
    public void setOrg(Organization org) {
		this.org = org;
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().isAssignableFrom(this.getClass())) {
			final Long objId = ((Event) obj).getId();
			return null == objId ? false : objId.equals(this.id);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return null == id ? -1 : id.intValue();
	}
    
    public boolean containsUser(final User user) {
    	for (final OrganizationMember member : members) {
    		if (member.getUser().equals(user)) {
    			return true;
    		}
    	}
    	return false;
    }
}
