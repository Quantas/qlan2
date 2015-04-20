package com.quantasnet.qlan2.admin.user;

import com.quantasnet.qlan2.ModelConstants;
import com.quantasnet.qlan2.admin.user.commands.IllegalUserAdminCommandException;
import com.quantasnet.qlan2.admin.user.commands.UserAdminCommand;
import com.quantasnet.qlan2.admin.user.commands.UserAdminCommandsHolder;
import com.quantasnet.qlan2.security.HasAdminRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;

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

    @Autowired
    private UserAdminCommandsHolder userAdminCommandsHolder;

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(UserAdminCommand.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) {
                setValue(userAdminCommandsHolder.getCommandForType(text));
            }
        });
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalUserAdminCommandException.class)
    public String illegalEnumValue() {
        return "error/404";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String users(final Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @RequestMapping(value = "/{userAdminCommand}/{userId}", method = RequestMethod.GET)
    public String doUserAdminCommand(@PathVariable final UserAdminCommand userAdminCommand, @PathVariable final long userId,
                             final RedirectAttributes redirectAttributes) {
        try {
            userAdminCommand.doAction(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.SUCCESS_STATUS, userAdminCommand.successMessage(userId));
        } catch (DataAccessException dae) {
            final String failMessage = userAdminCommand.failureMessage(userId);
            redirectAttributes.addFlashAttribute(ModelConstants.FAILURE_STATUS, failMessage);
            logger.error(failMessage, dae);
        }

        return "redirect:/admin/users";
    }
}
