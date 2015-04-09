package com.quantasnet.qlan2.event;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Controller
@RequestMapping("/event")
public class EventController {

    @RequestMapping("/list")
    public String eventList() {
        return "event/list";
    }
}
