package com.quantasnet.qlan2.event;

import com.quantasnet.qlan2.user.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Entity
public class Event implements Serializable {

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

    @ManyToOne
    private User owner;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    private Set<User> users;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
    }
}
