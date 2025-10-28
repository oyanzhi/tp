package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * List all persons that are archived in the address book to the user.
 */
public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archivelist";
    public static final String MESSAGE_SUCCESS = "Listed all archived persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCurrentFilter(Person::isArchived);
        model.setViewingArchivedList(true);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
