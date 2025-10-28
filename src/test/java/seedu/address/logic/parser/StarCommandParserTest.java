package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StarCommand;

public class StarCommandParserTest {

    private StarCommandParser parser = new StarCommandParser();

    @Test
    public void parse_validArgs_returnsStarCommand() {
        assertParseSuccess(parser, "1", new StarCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, "   1   ", new StarCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1", new StarCommand(INDEX_FIRST_PERSON));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: String user input
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StarCommand.MESSAGE_USAGE));

        // EP: single character user input
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StarCommand.MESSAGE_USAGE));

        // EP: negative index user input
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StarCommand.MESSAGE_USAGE));
    }
}
