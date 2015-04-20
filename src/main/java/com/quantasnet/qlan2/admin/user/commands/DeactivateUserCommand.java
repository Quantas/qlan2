package com.quantasnet.qlan2.admin.user.commands;

import org.springframework.stereotype.Component;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
@Component
class DeactivateUserCommand extends UserAdminCommand {

    @Override
    public void doAction(long userId) {
        userAdminService.deactivateUser(userId);
    }

    @Override
    public String successMessage(long userId) {
        return "User " + userId + " was successfully deactivated.";
    }

    @Override
    public String failureMessage(long userId) {
        return "User " + userId + " could not be deactivated.";
    }

    @Override
    public UserAdminCommands getCommandType() {
        return UserAdminCommands.DEACTIVATE;
    }
}
