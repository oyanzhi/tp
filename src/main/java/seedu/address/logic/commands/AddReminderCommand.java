package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Adds a reminder.
 */
public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";
    public static final String MESSAGE_USAGE = "to be updated";
    private final Reminder toAdd;

    /**
     * @param index of the person to be tagged to the reminder
     * @param reminder reminder to be added
     */
    public AddReminderCommand(Index index, Reminder reminder) {
        this.toAdd = reminder;
    }

    /**
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(" ");

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddReminderCommand)) {
            return false;
        }

        AddReminderCommand otherAddReminderCommand = (AddReminderCommand) other;
        return toAdd.equals(otherAddReminderCommand.toAdd);
    }
}
