package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnstarCommand;

public class UnstarCommandParserTest {

    private UnstarCommandParser parser = new UnstarCommandParser();

    @Test
    public void parse_validArgs_returnsUnstarCommand() {
        assertParseSuccess(parser, "1", new UnstarCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, "   1   ", new UnstarCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1", new UnstarCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: String user input
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: single character user input
        assertParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: negative index user input
        assertParseFailure(parser, "-1", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: zero index user input
        assertParseFailure(parser, "0", ParserUtil.MESSAGE_INVALID_INDEX);
        // EP: multiple index user input
        assertParseFailure(parser, "1 2", ParserUtil.MESSAGE_MORE_THAN_ONE_INDEX);
    }
}
