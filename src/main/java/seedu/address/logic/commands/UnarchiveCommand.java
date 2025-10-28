package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unarchives a person identified using it's displayed index from the archive address book.
 */
public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the person identified by the index number in the archived list.\n"
            + "Parameters: INDEX (must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_NOT_ARCHIVED = "This person is not archived";

    private final Index targetIndex;

    /**
     * @param targetIndex of person from archive address book to unarchive.
     */
    public UnarchiveCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isViewingArchivedList()) {
            throw new CommandException("You must be viewing the archive list to unarchive a person.");
        }

        List<Person> archivedList = model.getArchivedPersonList();

        if (targetIndex.getZeroBased() >= archivedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnarchive = archivedList.get(targetIndex.getZeroBased());
        if (!personToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_NOT_ARCHIVED);
        }

        Person unarchivedPerson = personToUnarchive.unarchive();
        model.setPerson(personToUnarchive, unarchivedPerson);
        model.refreshFilteredPersonList();
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, Messages.format(unarchivedPerson)));
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        return targetIndex.equals(otherUnarchiveCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
