package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteMeetingNoteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteMeetingNoteCommand code. For example, inputs "1 1" and "1 1 abc" take the
 * same path through the DeleteReminderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteMeetingNoteCommandParserTest {

    private DeleteMeetingNoteCommandParser parser = new DeleteMeetingNoteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 1", new DeleteMeetingNoteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", ParserUtil.MESSAGE_NOT_TWO_INDICES);

        assertParseFailure(parser, "-1", ParserUtil.MESSAGE_NOT_TWO_INDICES);

        assertParseFailure(parser, "0 0", ParserUtil.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1", ParserUtil.MESSAGE_NOT_TWO_INDICES);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " 1", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
