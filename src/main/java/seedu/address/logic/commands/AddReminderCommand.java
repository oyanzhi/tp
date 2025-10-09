package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";
    public static final String MESSAGE_USAGE = "to be updated";
    private final Reminder toAdd;

    public AddReminderCommand(Reminder reminder) {
        this.toAdd = reminder;
    }

    public CommandResult execute(Model model) throws CommandException {

    };
}
