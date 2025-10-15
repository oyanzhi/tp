package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;

public class ArchiveCommandParserTest {

    private ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveCommand() {
        // basic valid index
        assertParseSuccess(parser, "1", new ArchiveCommand(Index.fromOneBased(1)));

        // with extra whitespace
        assertParseSuccess(parser, "   2   ", new ArchiveCommand(Index.fromOneBased(2)));

        // with leading/trailing whitespace and preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "3", new ArchiveCommand(Index.fromOneBased(3)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non-numeric input
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // negative number
        assertParseFailure(parser, "-1", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // zero (index must be positive)
        assertParseFailure(parser, "0", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // multiple integers (should only take one index)
        assertParseFailure(parser, "1 2", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));

        // random preamble text
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
    }
}
