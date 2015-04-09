package com.quantasnet.qlan2.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Controller
public class SecurityController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
