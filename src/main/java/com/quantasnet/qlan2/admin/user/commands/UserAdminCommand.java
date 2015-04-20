package com.quantasnet.qlan2.admin.user.commands;

import com.quantasnet.qlan2.admin.user.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
public abstract class UserAdminCommand {

    @Autowired
    protected UserAdminService userAdminService;

    public abstract void doAction(long userId);
    public abstract String successMessage(long userId);
    public abstract String failureMessage(long userId);
    public abstract UserAdminCommands getCommandType();
}
