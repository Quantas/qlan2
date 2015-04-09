package com.quantasnet.qlan2.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

}
