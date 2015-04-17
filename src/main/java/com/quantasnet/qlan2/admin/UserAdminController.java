package com.quantasnet.qlan2.admin;

import com.quantasnet.qlan2.ModelConstants;
import com.quantasnet.qlan2.security.HasAdminRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by andrewlandsverk on 4/15/15.
 */
@HasAdminRole
@RequestMapping("/admin/users")
@Controller
public class UserAdminController {

    private final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

    @Autowired
    private UserAdminService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String users(final Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable final long userId, final RedirectAttributes redirectAttributes) {
        return doAction(() -> userService.deleteUser(userId),
                "User " + userId + " was successfully deleted.",
                "User " + userId + " could not be deleted.",
                redirectAttributes);
    }

    @RequestMapping(value = "/deactivate/{userId}", method = RequestMethod.GET)
    public String deactivateUser(@PathVariable final long userId, final RedirectAttributes redirectAttributes) {
        return doAction(() -> userService.deactivateUser(userId),
                "User " + userId + " was successfully deactivated.",
                "User " + userId + " could not be deactivated.",
                redirectAttributes);
    }

    @RequestMapping(value = "/activate/{userId}", method = RequestMethod.GET)
    public String activateUser(@PathVariable final long userId, final RedirectAttributes redirectAttributes) {
        return doAction(() -> userService.activateUser(userId),
                "User " + userId + " was successfully activated.",
                "User " + userId + " could not be activated.",
                redirectAttributes);
    }

    @RequestMapping(value = "/makeadmin/{userId}", method = RequestMethod.GET)
    public String makeAdmin(@PathVariable final long userId, final RedirectAttributes redirectAttributes) {
        return doAction(() -> userService.makeAdmin(userId),
                "User " + userId + " was made an Admin.",
                "User " + userId + " was not made an Admin.",
                redirectAttributes);
    }

    @RequestMapping(value = "/revokeadmin/{userId}", method = RequestMethod.GET)
    public String revokeAdmin(@PathVariable final long userId, final RedirectAttributes redirectAttributes) {
        return doAction(() -> userService.revokeAdmin(userId),
            "User " + userId + " had Admin revoked.",
            "User " + userId + " could not have Admin revoked.",
            redirectAttributes);
    }

    private String doAction(final Runnable function, final String successMessage, final String failMessage, final RedirectAttributes redirectAttributes) {
        try {
            function.run();
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, successMessage);
        } catch (DataAccessException dae) {
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, failMessage);
            logger.error(failMessage, dae);
        }

        return "redirect:/admin/users";
    }
}
