package com.quantasnet.qlan2.event;

import com.quantasnet.qlan2.organization.Organization;
import com.quantasnet.qlan2.organization.OrganizationGateKeeper;
import com.quantasnet.qlan2.organization.OrganizationMember;
import com.quantasnet.qlan2.organization.OrganizationService;
import com.quantasnet.qlan2.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Transactional
@Service
public class EventService {

	@Autowired
	private OrganizationGateKeeper gateKeeper;
	
	@Autowired
	private OrganizationService orgService;
	
    @Autowired
    private EventRepository eventRepository;
    
    public Optional<Event> getEvent(final Long eventId) {
        return Optional.ofNullable(eventRepository.findOne(eventId));
    }

    public boolean createEvent(final Event event, final Long id, final User user) {
    	
    	final boolean canDo = gateKeeper.hasPermissionToDo(user, id, true);
    	
    	if (canDo) {
    		final Optional<Organization> org = orgService.getOrgById(id);
    		if (org.isPresent()) {
	    		event.setOrg(org.get());
	    		org.get().getEvents().add(event);
	    		eventRepository.save(event);
	    		return true;
    		}
    	}
    	
    	return false;
    }

    public void createEvent(final Event event, final Organization org) {
        event.setOrg(org);
        org.getEvents().add(event);
    }

    public void joinEvent(final Long id, final User user) {
        final Event event = eventRepository.findOne(id);
        if (null != event) {
            final boolean canDo = gateKeeper.hasPermissionToDo(user, event.getOrg().getId(), false);
            if (canDo) {
            	for (final OrganizationMember member : user.getOrganizations()) {
            		if (member.getOrg().getId().equals(event.getOrg().getId())) {
            			member.getEvents().add(event);
            			event.getMembers().add(member);
            			return;
            		}
            	}
            }
        }
    }

    public void leaveEvent(final Long id, final User user) {
        final Event event = eventRepository.findOne(id);
        if (null != event) {
			user.getOrganizations().forEach(member -> {
				if (member.getOrg().getId().equals(event.getOrg().getId())) {
					member.getEvents().remove(event);
					event.getMembers().remove(member);
				}
			});
        }
    }
}
