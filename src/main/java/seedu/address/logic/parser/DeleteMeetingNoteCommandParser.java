package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.LOGGING_MESSAGE_PARSE_INDEX;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMeetingNoteCommand;
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
            Index CLIENT_INDEX = indices[0];
            Index MEETING_NOTE_INDEX = indices[1];
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDEX + CLIENT_INDEX.getOneBased());
            logger.log(Level.INFO, LOGGING_MESSAGE_PARSE_INDEX + MEETING_NOTE_INDEX.getOneBased());
            return new DeleteMeetingNoteCommand(indices[0], indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingNoteCommand.MESSAGE_USAGE), pe);
        }
    }
}
