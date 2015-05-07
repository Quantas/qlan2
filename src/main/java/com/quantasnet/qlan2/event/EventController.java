package com.quantasnet.qlan2.event;

import com.quantasnet.qlan2.security.HasUserRole;
import com.quantasnet.qlan2.user.User;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Controller
@RequestMapping("/event")
public class EventController {

    private static final String EVENT_FORM = "eventForm";

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String eventList(final Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "event/list";
    }

    @HasUserRole
    @RequestMapping(value = "/join/{eventId}", method = RequestMethod.GET)
    public String joinEvent(@PathVariable final Long eventId, @AuthenticationPrincipal final User user) {
        eventService.joinEvent(eventId, user);
        return "redirect:/org";
    }

    @HasUserRole
    @RequestMapping(value = "/leave/{eventId}", method = RequestMethod.GET)
    public String leaveEvent(@PathVariable final Long eventId, @AuthenticationPrincipal final User user) {
        eventService.leaveEvent(eventId, user);
        return "redirect:/org";
    }

    @HasUserRole
    @RequestMapping(value = "/new/{orgId}", method = RequestMethod.GET)
    public String newEvent(@PathVariable final Long orgId, final Model model) {
        if (!model.containsAttribute(EVENT_FORM)) {
            final Event event = new Event();
            event.setStart(DateTime.now().plusDays(7));
            event.setEnd(DateTime.now().plusDays(10));
            model.addAttribute(EVENT_FORM, event);
        }
        model.addAttribute("orgId", orgId);
        return "event/create";
    }

    @HasUserRole
    @RequestMapping(value = "/create/{orgId}", method = RequestMethod.POST)
    public String createEvent(@PathVariable final Long orgId, @Valid final Event eventForm, final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final User user) {

    	if (!bindingResult.hasErrors()) {
    		final boolean success = eventService.createEvent(eventForm, orgId, user);
    		if (!success) {
    			bindingResult.reject("event.create.error", "There was an error creating the event. Please try again.");
    		}
    	}
    	
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + EVENT_FORM, bindingResult);
            redirectAttributes.addFlashAttribute(EVENT_FORM, eventForm);
            return "redirect:/event/new/" + orgId;
        }
        
        return "redirect:/org/" + orgId;
    }

}
