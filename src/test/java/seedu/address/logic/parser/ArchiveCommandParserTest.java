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
        assertParseSuccess(parser, "1", new ArchiveCommand(Index.fromOneBased(1)));

        assertParseSuccess(parser, "   2   ", new ArchiveCommand(Index.fromOneBased(2)));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "3", new ArchiveCommand(Index.fromOneBased(3)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "-1", ParserUtil.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "0", ParserUtil.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "1 2", ParserUtil.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
