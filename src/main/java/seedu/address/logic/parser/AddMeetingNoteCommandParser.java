package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMeetingNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meetingnote.MeetingNote;

/**
 * Parses input arguments and creates a new AddMeetingNoteCommand object
 */
public class AddMeetingNoteCommandParser implements Parser<AddMeetingNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingNoteCommand
     * and returns an AddMeetingNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        String[] splitArgs = trimmedArgs.split("\\s+", 2);

        // Defensive check
        if (splitArgs.length < 2) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingNoteCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            String note = ParserUtil.parseNote(splitArgs[1]);
            MeetingNote meetingNote = new MeetingNote(note, LocalDateTime.now());
            return new AddMeetingNoteCommand(index, meetingNote);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingNoteCommand.MESSAGE_USAGE), pe);
        }

    }

}
