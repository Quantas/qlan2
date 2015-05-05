package com.quantasnet.qlan2.event;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantasnet.qlan2.organization.Organization;
import com.quantasnet.qlan2.organization.OrganizationGateKeeper;
import com.quantasnet.qlan2.organization.OrganizationService;
import com.quantasnet.qlan2.user.User;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Service
public class EventService {

	@Autowired
	private OrganizationGateKeeper gateKeeper;
	
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationService orgService;
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public boolean createEvent(final Event event, final Long id, final User user) {
    	
    	final Optional<Organization> org = gateKeeper.hasPermissionToDo(user, id, true);
    	
    	if (org.isPresent()) {
    		org.get().getEvents().add(event);
    		orgService.save(org.get());
    		return true;
    	}
    	
    	return false;
    }
}
