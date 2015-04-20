package com.quantasnet.qlan2.admin.user.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
@Component
public class UserAdminCommandsHolder {

    @Autowired
    private List<UserAdminCommand> userAdminCommands;

    private Map<UserAdminCommands, UserAdminCommand> userAdminCommandMap;

    @PostConstruct
    public void postConstruct() {
        userAdminCommandMap = new EnumMap<>(UserAdminCommands.class);

        for (final UserAdminCommand command : userAdminCommands) {
            userAdminCommandMap.put(command.getCommandType(), command);
        }
    }

    public UserAdminCommand getCommandForType(final String type) {
        final UserAdminCommands command = UserAdminCommands.convertFromText(type);
        if (null == command) {
            throw new IllegalUserAdminCommandException();
        }
        return userAdminCommandMap.get(command);
    }
}
