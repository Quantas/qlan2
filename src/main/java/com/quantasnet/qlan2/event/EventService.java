package com.quantasnet.qlan2.event;

import com.quantasnet.qlan2.organization.Organization;
import com.quantasnet.qlan2.organization.OrganizationGateKeeper;
import com.quantasnet.qlan2.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Service
public class EventService {

	@Autowired
	private OrganizationGateKeeper gateKeeper;
	
    @Autowired
    private EventRepository eventRepository;

    public Optional<Event> getEvent(final Long eventId) {
        return Optional.ofNullable(eventRepository.findOne(eventId));
    }

    public boolean createEvent(final Event event, final Long id, final User user) {
    	
    	final Optional<Organization> org = gateKeeper.hasPermissionToDo(user, id, true);
    	
    	if (org.isPresent()) {
    		event.setOrg(org.get());
    		eventRepository.save(event);
    		return true;
    	}
    	
    	return false;
    }

    public void createEvent(final Event event, final Organization org) {
        event.setOrg(org);
        eventRepository.save(event);
    }

    public Optional<Event> joinEvent(final Long id, final User user) {
        final Event event = eventRepository.findOne(id);
        if (null != event) {
            final Optional<Organization> org = gateKeeper.hasPermissionToDo(user, event.getOrg().getId(), false);
            if (org.isPresent()) {
                event.getUsers().add(user);
                eventRepository.save(event);
            }
        }
        return Optional.ofNullable(event);
    }

    public Optional<Event> leaveEvent(final Long id, final User user) {
        final Event event = eventRepository.findOne(id);
        if (null != event) {
            event.getUsers().remove(user);
            eventRepository.save(event);
        }
        return Optional.ofNullable(event);
    }
}
