package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "rDelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the targeted person's reminder list.\n"
            + "Parameters: CLIENT INDEX (must be a positive integer), REMINDER INDEX (must be a positive integer\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Client %1%s's Reminder %2%s: %1$s";

    private final Index clientIndex;
    private final Index reminderIndex;

    public DeleteReminderCommand(Index clientIndex, Index reminderIndex) {
    }

}
