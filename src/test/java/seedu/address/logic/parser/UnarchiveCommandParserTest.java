package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnarchiveCommandParserTest {

    private final UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() throws Exception {
        UnarchiveCommand command = parser.parse("1");
        assertEquals(new UnarchiveCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parse_invalidArgsNonInteger_throwsParseException() {
        assertThrows(ParseException.class,
                ParserUtil.MESSAGE_INVALID_INDEX, () -> parser.parse("abc"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        UnarchiveCommand.MESSAGE_USAGE), () -> parser.parse("   "));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertThrows(ParseException.class,
                ParserUtil.MESSAGE_INVALID_INDEX, () -> parser.parse("0"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class,
                ParserUtil.MESSAGE_INVALID_INDEX, () -> parser.parse("-5"));
    }
}
