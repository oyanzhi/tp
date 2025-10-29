package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_FAILURE;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_INDICES;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteReminderCommandParser object
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteReminderCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns a DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        try {
            Index[] indices = ParserUtil.parseDualIndex(args);
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDICES + indices[0].getOneBased() + ", "
                    + indices[1].getOneBased());
            return new DeleteReminderCommand(indices[0], indices[1]);
        } catch (ParseException pe) {
            logger.log(Level.FINER, LOGGING_MESSAGE_PARSE_FAILURE + pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE), pe);
        }
    }
}
