package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteReminderCommandParser object
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns a DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        try {
            Index[] indices = ParserUtil.parseDualIndex(args);
            return new DeleteReminderCommand(indices[0], indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe);
        }
    }
}
