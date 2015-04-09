package com.quantasnet.qlan2.home;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@AuthenticationPrincipal final User currentUser) {
        return "home";
    }
}
