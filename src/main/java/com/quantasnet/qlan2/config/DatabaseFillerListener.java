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
import com.quantasnet.qlan2.event.EventService;
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
    
    @Autowired
    private EventService eventService;

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

                orgService.createOrganization(org, adminUser);

                orgService.addOrgMember(org, userUser, false);
                
                // create events
                final Event event1 = new Event();
                event1.setName("Admin Event");
                event1.setStart(DateTime.now());
                event1.setEnd(DateTime.now().plusDays(3));
                
                eventService.createEvent(event1, org);
                
                final Event event2 = new Event();
                event2.setName("User Event");
                event2.setStart(DateTime.now().plusDays(7));
                event2.setEnd(DateTime.now().plusDays(10));
                
                eventService.createEvent(event2, org);
                
            } else {
                log.info("DB already populated");
            }
        } catch(final Exception e) {
            log.error("Error filling the database", e);
            throw e;
        }
    }
}
