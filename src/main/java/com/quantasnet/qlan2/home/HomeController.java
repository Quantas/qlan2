package com.quantasnet.qlan2.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Controller
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        LOG.info("Secure=", request.isSecure());
        return "home";
    }
}
