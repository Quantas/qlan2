package com.quantasnet.qlan2.admin.user.commands;

/**
 * Created by andrewlandsverk on 4/19/15.
 */
enum UserAdminCommands {
    DELETE,
    DEACTIVATE,
    ACTIVATE,
    MAKEADMIN,
    REVOKEADMIN;

    private static final UserAdminCommands[] values = values();

    public static UserAdminCommands convertFromText(final String text) {
        for (final UserAdminCommands command : values) {
            if (command.name().toLowerCase().equals(text)) {
                return command;
            }
        }

        return null;
    }
}
