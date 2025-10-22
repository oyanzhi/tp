package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_FAILURE;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_INDEX;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnstarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnstarCommand object
 */
public class UnstarCommandParser implements Parser<UnstarCommand> {
    private static Logger logger = Logger.getLogger(UnstarCommandParser.class.getSimpleName());

    /**
     * Parses the given {@code String} of arguments in the context of the StarCommand
     * and returns a StarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnstarCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDEX + index.getOneBased());
            return new UnstarCommand(index);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, LOGGING_MESSAGE_PARSE_FAILURE
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstarCommand.MESSAGE_USAGE));
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstarCommand.MESSAGE_USAGE), pe);
        }
    }

}
