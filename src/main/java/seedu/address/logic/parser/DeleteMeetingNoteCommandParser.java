package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_INDEX;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMeetingNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMeetingNoteCommand object
 */
public class DeleteMeetingNoteCommandParser implements Parser<DeleteMeetingNoteCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteMeetingNoteCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetingNoteCommand
     * and returns a DeleteMeetingNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetingNoteCommand parse(String args) throws ParseException {
        try {
            Index[] indices = ParserUtil.parseDualIndex(args);
            Index clientIndex = indices[0];
            Index meetingNoteIndex = indices[1];
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDEX + clientIndex.getOneBased());
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDEX + meetingNoteIndex.getOneBased());
            return new DeleteMeetingNoteCommand(indices[0], indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    pe.getMessage(), pe);
        }
    }
}
