package com.quantasnet.qlan2.admin.user.commands;

import org.springframework.stereotype.Component;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
@Component
class MakeAdminCommand extends UserAdminCommand {

    @Override
    public void doAction(long userId) {
        userAdminService.makeAdmin(userId);
    }

    @Override
    public String successMessage(long userId) {
        return "User " + userId + " was made an Admin.";
    }

    @Override
    public String failureMessage(long userId) {
        return "User " + userId + " was not made an Admin.";
    }

    @Override
    public UserAdminCommands getCommandType() {
        return UserAdminCommands.MAKEADMIN;
    }
}
