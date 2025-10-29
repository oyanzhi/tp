package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_FAILURE;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_INDEX;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StarCommand object
 */
public class StarCommandParser implements Parser<StarCommand> {
    private static final Logger logger = LogsCenter.getLogger(StarCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the StarCommand
     * and returns a StarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StarCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDEX + index.getOneBased());
            return new StarCommand(index);
        } catch (ParseException pe) {
            logger.log(Level.FINER, LOGGING_MESSAGE_PARSE_FAILURE
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, StarCommand.MESSAGE_USAGE));
            throw new ParseException(pe.getMessage(), pe);
        }
    }

}
