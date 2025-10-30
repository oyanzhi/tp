package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOTE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddMeetingNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meetingnote.MeetingNote;

public class AddMeetingNoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingNoteCommand.MESSAGE_USAGE);
    private final AddMeetingNoteCommandParser parser = new AddMeetingNoteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + MEETING_NOTE_DESC_AMY;
        AddMeetingNoteCommand actualCommand = parser.parse(userInput);

        // Verify note
        MeetingNote actualNote = actualCommand.getMeetingNote();
        assertEquals(VALID_MEETING_NOTE_AMY, actualNote.getNote());

        // Verify the date/time
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = actualNote.getCreatedAt();
        assertTrue(!createdAt.isAfter(now)); // not in the future
        Duration diff = Duration.between(createdAt, now);
        assertTrue(diff.abs().getSeconds() < 60,
                "createdAt should be within 60s of now, diff = " + diff.getSeconds()); // Seconds are truncated
    }

    @Test
    public void parse_missingNote_failure() {
        assertParseFailure(parser, INDEX_FIRST_PERSON + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + MEETING_NOTE_DESC_AMY, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + MEETING_NOTE_DESC_AMY, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("-5"));
    }


    // Empty preamble
    @Test
    public void parse_emptyPreamble_throwsParseException() {
        String commandString = AddMeetingNoteCommand.COMMAND_WORD + " ";

        assertParseFailure(parser, commandString, MESSAGE_INVALID_FORMAT);
    }

}
