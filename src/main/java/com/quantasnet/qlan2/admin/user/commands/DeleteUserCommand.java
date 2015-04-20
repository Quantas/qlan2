package com.quantasnet.qlan2.admin.user.commands;

import org.springframework.stereotype.Component;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
@Component
class DeleteUserCommand extends UserAdminCommand {

    @Override
    public void doAction(final long userId) {
        userAdminService.deleteUser(userId);
    }

    @Override
    public String successMessage(final long userId) {
        return "User " + userId + " was successfully deleted.";
    }

    @Override
    public String failureMessage(final long userId) {
        return "User " + userId + " could not be deleted.";
    }

    @Override
    public UserAdminCommands getCommandType() {
        return UserAdminCommands.DELETE;
    }
}
