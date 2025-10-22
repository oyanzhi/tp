package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Stars a person identified using their displayed index from the address book.
 */
public class StarCommand extends Command {

    public static final String COMMAND_WORD = "star";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": stars the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_STARRED_PERSON_SUCCESS = "Starred Person: %1$s";
    public static final String MESSAGE_PERSON_IS_STARRED = "Chosen person is already starred";

    // Static comparator for sorting
    private static final Comparator<Person> STARRED_STATUS_COMPARATOR = Comparator
            .comparing(Person::isStarred, Comparator.reverseOrder())
            .thenComparing(Person::getName);

    private final Index targetIndex;

    public StarCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToStar = lastShownList.get(targetIndex.getZeroBased());
        // Check if person has already been starred
        if (personToStar.isStarred()) {
            throw new CommandException(MESSAGE_PERSON_IS_STARRED);
        }

        Person starredPerson = personToStar.withStarredStatus(true);

        model.setPerson(personToStar, starredPerson);
        model.sortPersons(STARRED_STATUS_COMPARATOR);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_STARRED_PERSON_SUCCESS, Messages.format(personToStar)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StarCommand)) {
            return false;
        }

        StarCommand otherStarCommand = (StarCommand) other;
        return targetIndex.equals(otherStarCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
