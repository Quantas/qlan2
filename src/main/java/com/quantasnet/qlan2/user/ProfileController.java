package com.quantasnet.qlan2.user;

import com.quantasnet.qlan2.user.model.ChangePasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

/**
 * Created by andrewlandsverk on 4/12/15.
 */
@SessionAttributes({ ProfileController.PROFILE_USER })
@RequestMapping("/profile")
@Controller
public class ProfileController {

    public static final String PROFILE_USER = "profileUser";
    public static final String CHANGE_PASSWORD = "changePasswordForm";

    @Autowired
    private UserService userService;

    @RequestMapping
    public String profile(@AuthenticationPrincipal final User user, final Model model) {
        final User profileUser = userService.getUserById(user.getId());
        model.addAttribute(PROFILE_USER, profileUser);
        model.addAttribute(CHANGE_PASSWORD, new ChangePasswordForm());
        return "user/profile";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProfile(@AuthenticationPrincipal final User user, @Valid @ModelAttribute(PROFILE_USER) final User profileUser, final BindingResult bindingResult) {
        if (user.getId().equals(profileUser.getId())) {
            final User emailCheck = userService.getUserByEmail(profileUser.getEmail());

            if (null != emailCheck && !emailCheck.getId().equals(user.getId())) {
                bindingResult.addError(new FieldError(PROFILE_USER, "email", "Email already taken"));
            }

            if (bindingResult.hasErrors()) {
                return "user/profile";
            } else {
                userService.profileUpdate(profileUser);
            }

        }
        return "redirect:/profile";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@AuthenticationPrincipal final User user, @Valid @ModelAttribute final ChangePasswordForm changePasswordForm, final BindingResult bindingResult) {
        final boolean success = userService.changePassword(user, changePasswordForm);

        if (!success && !bindingResult.hasErrors()) {
            bindingResult.addError(new FieldError(CHANGE_PASSWORD, "newPassword", "The password change failed"));
        }

        if (bindingResult.hasErrors()) {
            return "user/profile";
        }

        return "redirect:/profile";
    }
}
