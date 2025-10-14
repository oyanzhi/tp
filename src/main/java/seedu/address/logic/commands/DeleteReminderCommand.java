package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Deletes a reminder attached to a client using both of their displayed indexes.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "rDelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the targeted person's reminder list.\n"
            + "Parameters: CLIENT INDEX (must be a positive integer), REMINDER INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Client %1%s's Reminder %2%d: %3$s";

    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";

    private final Index clientIndex;
    private final Index reminderIndex;

    /**
     * Creates an DeleteReminderCommand to delete the specified {@code Reminder} from the specified {@code Person}
     */
    public DeleteReminderCommand(Index clientIndex, Index reminderIndex) {
        requireAllNonNull(clientIndex, reminderIndex);
        this.clientIndex = clientIndex;
        this.reminderIndex = reminderIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteFrom = lastShownList.get(clientIndex.getZeroBased());
        ArrayList<Reminder> reminderList = personToDeleteFrom.getReminders();

        if (reminderIndex.getZeroBased() >= reminderList.size()) {
            throw new CommandException(MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }
        Reminder reminderToDelete = reminderList.get(reminderIndex.getZeroBased());
        reminderList.remove(reminderToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(personToDeleteFrom),
                reminderIndex.getOneBased(),
                reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteReminderCommand otherDeleteReminderCommand = (DeleteReminderCommand) other;
        return clientIndex.equals(otherDeleteReminderCommand.clientIndex)
                && reminderIndex.equals(otherDeleteReminderCommand.reminderIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("reminderIndex", reminderIndex)
                .toString();
    }
}
