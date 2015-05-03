package com.quantasnet.qlan2.event;

import com.quantasnet.qlan2.security.HasUserRole;
import com.quantasnet.qlan2.user.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEvent(final Model model) {
        if (!model.containsAttribute(EVENT_FORM)) {
            final Event event = new Event();
            event.setStart(DateTime.now().plusDays(7));
            event.setEnd(DateTime.now().plusDays(10));
            model.addAttribute(EVENT_FORM, event);
        }
        return "event/create";
    }

    @HasUserRole
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createEvent(@Valid final Event eventForm, final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final User user) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + EVENT_FORM, bindingResult);
            redirectAttributes.addFlashAttribute(EVENT_FORM, eventForm);
            return "redirect:/event/new";
        }

        eventForm.setOwner(user);
        eventService.createEvent(eventForm);
        return "redirect:/event/list";
    }

}
