package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Proxy class. To be updated
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "rDelete";

    public static final String MESSAGE_USAGE = "to be updated";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    private final Index targetIndex;
    private final Index reminderIndex;
    /**
     * Proxy constructor. To be updated
     */
    public DeleteReminderCommand(Index targetIndex, Index reminderIndex) {
        this.targetIndex = targetIndex;
        this.reminderIndex = reminderIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
