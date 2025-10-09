package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;


public class AddReminderCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);
    private final AddReminderParser parser = new AddReminderParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = "1 h/client's birthday d/2026-10-15T09:00";

        Reminder expectedReminder = new Reminder("client's birthday", "2026-10-15T09:00");
        AddReminderCommand expectedCommand = new AddReminderCommand(Index.fromZeroBased(1), expectedReminder);

        AddReminderCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingHeader_failure() {
        String userInput = "1 d/2025-12-01T09:00";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingDeadline_failure() {
        String userInput = "1 h/Meeting";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + "meeting" + "2026-10-15T09:00", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + "meeting" + "2026-10-15T09:00", MESSAGE_INVALID_FORMAT);
    }

}
