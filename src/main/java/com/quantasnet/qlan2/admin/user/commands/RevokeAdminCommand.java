package com.quantasnet.qlan2.admin.user.commands;

import org.springframework.stereotype.Component;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
@Component
class RevokeAdminCommand extends UserAdminCommand {

    @Override
    public void doAction(long userId) {
        userAdminService.revokeAdmin(userId);
    }

    @Override
    public String successMessage(long userId) {
        return "User " + userId + " had Admin revoked.";
    }

    @Override
    public String failureMessage(long userId) {
        return "User " + userId + " could not have Admin revoked.";
    }

    @Override
    public UserAdminCommands getCommandType() {
        return UserAdminCommands.REVOKEADMIN;
    }
}
