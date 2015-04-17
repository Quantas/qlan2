package com.quantasnet.qlan2.admin;

import com.quantasnet.qlan2.ModelConstants;
import com.quantasnet.qlan2.user.User;
import com.quantasnet.qlan2.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by andrewlandsverk on 4/15/15.
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/users")
@Controller
public class UserAdminController {

    private static final String USERS_REDIRECT = "redirect:/admin/users";
    private static final String USERS_VIEW = "admin/users";

    private final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String users(final Model model) {
        final List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return USERS_VIEW;
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable final long userId, final RedirectAttributes redirectAttributes, final Model model) {
        try {
            userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, "User " + userId + " was successfully deleted.");
        } catch (DataAccessException dae) {
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, "User " + userId + " could not be deleted.");
            logger.error("Error deleting user {}", userId, dae);
        }

        return USERS_REDIRECT;
    }

    @RequestMapping(value = "/deactivate/{userId}", method = RequestMethod.GET)
    public String deactivateUser(@PathVariable final long userId, final RedirectAttributes redirectAttributes, final Model model) {
        try {
            userService.deactivateUser(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, "User " + userId + " was successfully deactivated.");
        } catch (DataAccessException dae) {
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, "User " + userId + " could not be deactivated.");
            logger.error("Error deactivating user {}", userId, dae);
        }

        return USERS_REDIRECT;
    }

    @RequestMapping(value = "/activate/{userId}", method = RequestMethod.GET)
    public String activateUser(@PathVariable final long userId, final RedirectAttributes redirectAttributes, final Model model) {
        try {
            userService.activateUser(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, "User " + userId + " was successfully activated.");
        } catch (DataAccessException dae) {
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, "User " + userId + " could not be activated.");
            logger.error("Error activating user {}", userId, dae);
        }

        return USERS_REDIRECT;
    }

    @RequestMapping(value = "/makeadmin/{userId}", method = RequestMethod.GET)
    public String makeAdmin(@PathVariable final long userId, final RedirectAttributes redirectAttributes, final Model model) {
        try {
            userService.makeAdmin(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, "User " + userId + " was made an Admin.");
        } catch (DataAccessException dae) {
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, "User " + userId + " was not made an Admin.");
            logger.error("Error making user an admin {}", userId, dae);
        }

        return USERS_REDIRECT;
    }

    @RequestMapping(value = "/revokeadmin/{userId}", method = RequestMethod.GET)
    public String revokeAdmin(@PathVariable final long userId, final RedirectAttributes redirectAttributes, final Model model) {
        try {
            userService.revokeAdmin(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, "User " + userId + " had Admin revoked.");
        } catch (DataAccessException dae) {
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, "User " + userId + " could not have Admin revoked.");
            logger.error("Error revoking admin from {}", userId, dae);
        }

        return USERS_REDIRECT;
    }
}
