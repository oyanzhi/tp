package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
            + "Parameters: CLIENT_INDEX (must be a positive integer), REMINDER_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Client %1$s's Reminder %2$d: %3$s";

    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";

    private static final Comparator<Reminder> UI_ORDER =
            java.util.Comparator.comparing(String::valueOf);
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
        Person target = lastShownList.get(clientIndex.getZeroBased());

        // Resolve reminder by the SAME order as UI
        List<Reminder> ordered = new ArrayList<>(target.getReminders());
        ordered.sort(UI_ORDER);
        if (reminderIndex.getZeroBased() >= ordered.size()) {
            throw new CommandException(MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }
        Reminder toRemove = ordered.get(reminderIndex.getZeroBased());

        // Build new reminders collection and replace Person
        List<Reminder> updated = new ArrayList<>(target.getReminders());
        boolean removed = updated.remove(toRemove);
        if (!removed) {
            throw new CommandException("Failed to delete reminder (not found).");
        }

        Person edited = rebuildPersonWithReminders(target, updated);
        model.setPerson(target, edited);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(
                MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(edited),
                reminderIndex.getOneBased(),
                toRemove));
    }

    /** Rebuilds a Person with updated reminders. */
    private Person rebuildPersonWithReminders(Person original, List<Reminder> newReminders) {
        return new Person(
                original.getName(),
                original.getPhone(),
                original.getEmail(),
                original.getAddress(),
                original.getTags(),
                new ArrayList<>(newReminders),
                Optional.empty()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteReminderCommand)) {
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

