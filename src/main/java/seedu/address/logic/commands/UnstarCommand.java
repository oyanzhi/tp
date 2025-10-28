package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.StarCommand.STARRED_STATUS_COMPARATOR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes starred status of a person identified using their displayed index from the address book.
 */
public class UnstarCommand extends Command {
    public static final String COMMAND_WORD = "unstar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes starred status of the person identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNSTARRED_PERSON_SUCCESS = "Starred status removed from Person: %1$s";
    public static final String MESSAGE_PERSON_IS_UNSTARRED = "Chosen person is not starred";

    private final Index targetIndex;

    public UnstarCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToUnstar = lastShownList.get(targetIndex.getZeroBased());

        // Assert person is not null
        assert personToUnstar != null : "Person to unstar is null. Index: " + targetIndex.getZeroBased();

        // Check if person starred status has been removed
        if (!personToUnstar.isStarred()) {
            throw new CommandException(MESSAGE_PERSON_IS_UNSTARRED);
        }

        Person unstarredPerson = personToUnstar.rebuildWithStarredStatus(false);

        model.setPerson(personToUnstar, unstarredPerson);
        model.sortPersons(STARRED_STATUS_COMPARATOR);
        model.refreshFilteredPersonList();
        return new CommandResult(String.format(MESSAGE_UNSTARRED_PERSON_SUCCESS, Messages.format(personToUnstar)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnstarCommand)) {
            return false;
        }

        UnstarCommand otherUnstarCommand = (UnstarCommand) other;
        return targetIndex.equals(otherUnstarCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
