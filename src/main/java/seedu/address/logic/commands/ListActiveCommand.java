package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * List all persons that are active in the address book to the user.
 */
public class ListActiveCommand extends Command {

    public static final String COMMAND_WORD = "activelist";
    public static final String MESSAGE_SUCCESS = "Listed all active clients";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCurrentFilter(person -> !person.isArchived());
        model.setViewingArchivedList(false);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
