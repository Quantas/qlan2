package com.quantasnet.qlan2.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.quantasnet.qlan2.event.Event;
import com.quantasnet.qlan2.organization.Organization;
import com.quantasnet.qlan2.organization.OrganizationMember;
import com.quantasnet.qlan2.organization.OrganizationService;
import com.quantasnet.qlan2.user.Role;
import com.quantasnet.qlan2.user.RoleService;
import com.quantasnet.qlan2.user.User;
import com.quantasnet.qlan2.user.UserService;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Transactional
@Component
public class DatabaseFillerListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(DatabaseFillerListener.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService orgService;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        try {
            final List<Role> roles = roleService.findAll();

            if (roles.isEmpty()) {
                // create roles
                final Set<Role> rolesAll = new HashSet<>();
                final Set<Role> rolesUser = new HashSet<>();

                final Role user = roleService.save("ROLE_USER");
                final Role admin = roleService.save("ROLE_ADMIN");

                rolesAll.add(user);
                rolesAll.add(admin);

                rolesUser.add(user);

                // create users
                final User adminUser = userService.save("admin", "Admin", "Administrator", "admin@test.com", "admin", rolesAll);
                final User userUser = userService.save("user", "User", "Userton", "user@test.com", "user", rolesUser);

                // create organization
                final Organization org = new Organization();
                org.setName("The QLANs");
                org.setDescription("Just a test");
                
                final OrganizationMember adminMember = new OrganizationMember();
                adminMember.setRole("Admin");
                adminMember.setStaff(true);
                adminMember.setUser(adminUser);
                
                final OrganizationMember userMember = new OrganizationMember();
                userMember.setRole("User");
                userMember.setStaff(false);
                userMember.setUser(userUser);
                
                final Set<OrganizationMember> members = new HashSet<OrganizationMember>();
                members.add(adminMember);
                members.add(userMember);
                
                org.setMembers(members);
                
                final Set<Event> events = new HashSet<Event>();
                
                // create events
                final Event event1 = new Event();
                event1.setName("Admin Event");
                event1.setStart(DateTime.now());
                event1.setEnd(DateTime.now().plusDays(3));
                
                final Event event2 = new Event();
                event2.setName("User Event");
                event2.setStart(DateTime.now().plusDays(7));
                event2.setEnd(DateTime.now().plusDays(10));

                events.add(event1);
                events.add(event2);
                
                org.setEvents(events);
                
                orgService.save(org);
                
            } else {
                log.info("DB already populated");
            }
        } catch(final Exception e) {
            log.error("Error filling the database", e);
        }
    }
}
