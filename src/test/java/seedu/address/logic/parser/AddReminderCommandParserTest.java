package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_HEADER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_HEADER_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;


public class AddReminderCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);
    private final AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + REMINDER_HEADER_DESC_AMY
                + " " + REMINDER_DEADLINE_DESC;

        Reminder expectedReminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        AddReminderCommand expectedCommand = new AddReminderCommand(INDEX_FIRST_PERSON, expectedReminder);

        AddReminderCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingHeader_failure() {
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + REMINDER_DEADLINE_DESC , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingDeadline_failure() {
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + REMINDER_HEADER_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + REMINDER_HEADER_DESC_AMY + REMINDER_DEADLINE_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMINDER_HEADER_DESC_AMY + REMINDER_DEADLINE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("-5"));
    }

    // Missing prefixes
    @Test
    public void parse_missingPrefixes_throwsParseException() {
        String commandString = AddReminderCommand.COMMAND_WORD + " " + VALID_REMINDER_HEADER_AMY + " "
                + VALID_REMINDER_DEADLINE;

        assertParseFailure(parser, commandString, MESSAGE_INVALID_FORMAT);
    }

    // Empty preamble
    @Test
    public void parse_emptyPreamble_throwsParseException() {
        String commandString = AddReminderCommand.COMMAND_WORD + " ";

        assertParseFailure(parser, commandString, MESSAGE_INVALID_FORMAT);
    }

}
