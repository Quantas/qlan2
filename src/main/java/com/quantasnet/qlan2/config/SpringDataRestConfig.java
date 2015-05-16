package com.quantasnet.qlan2.config;

import com.quantasnet.qlan2.event.Event;
import com.quantasnet.qlan2.organization.Organization;
import com.quantasnet.qlan2.organization.OrganizationMember;
import com.quantasnet.qlan2.user.User;
import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

/**
 * Created by andrewlandsverk on 5/13/15.
 */
@Configuration
public class SpringDataRestConfig extends SpringBootRepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBaseUri("/api");
        config.exposeIdsFor(Organization.class, Event.class, OrganizationMember.class, User.class);
    }
}
