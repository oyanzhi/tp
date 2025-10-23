package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_HEADER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_HEADER_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;

public class EditReminderCommandParserTest {
    private static final Index INDEX_FIRST_REMINDER = Index.fromOneBased(1);

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE);

    private final EditReminderCommandParser parser = new EditReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = String.format("%s %s %s %s",
                INDEX_FIRST_PERSON.getOneBased(),
                INDEX_FIRST_REMINDER.getOneBased(),
                REMINDER_HEADER_DESC_AMY,
                REMINDER_DEADLINE_DESC);

        Reminder expectedReminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        EditReminderCommand expectedCommand = new EditReminderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_REMINDER, expectedReminder);
        System.out.println(expectedCommand);

        try {
            EditReminderCommand actualCommand = parser.parse(userInput);
            assertEquals(expectedCommand, actualCommand);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parse_missingHeader_failure() {
        String userInput = String.format("%s %s %s",
                INDEX_FIRST_PERSON.getOneBased(),
                INDEX_FIRST_REMINDER.getOneBased(),
                REMINDER_DEADLINE_DESC);

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingDeadline_failure() {
        String userInput = String.format("%s %s %s",
                INDEX_FIRST_PERSON.getOneBased(),
                INDEX_FIRST_REMINDER.getOneBased(),
                REMINDER_HEADER_DESC_AMY);

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String negativeIndex = "-5";
        String zeroIndex = "0";

        // negative client index
        assertParseFailure(parser, negativeIndex + INDEX_FIRST_REMINDER
                + REMINDER_HEADER_DESC_AMY + REMINDER_DEADLINE_DESC, MESSAGE_INVALID_FORMAT);

        // zero client index
        assertParseFailure(parser, zeroIndex + INDEX_FIRST_REMINDER
                + REMINDER_HEADER_DESC_AMY + REMINDER_DEADLINE_DESC, MESSAGE_INVALID_FORMAT);

        // negative reminder index
        assertParseFailure(parser, INDEX_FIRST_PERSON + negativeIndex
                + REMINDER_HEADER_DESC_AMY + REMINDER_DEADLINE_DESC, MESSAGE_INVALID_FORMAT);

        // zero reminder index
        assertParseFailure(parser, INDEX_FIRST_PERSON + zeroIndex
                + REMINDER_HEADER_DESC_AMY + REMINDER_DEADLINE_DESC, MESSAGE_INVALID_FORMAT);
    }

    // Missing prefixes
    @Test
    public void parse_missingPrefixes_throwsParseException() {
        String commandString = EditReminderCommand.COMMAND_WORD + " "
                + VALID_REMINDER_HEADER_AMY + " "
                + VALID_REMINDER_DEADLINE;

        assertParseFailure(parser, commandString, MESSAGE_INVALID_FORMAT);
    }

    // Empty preamble
    @Test
    public void parse_emptyPreamble_throwsParseException() {
        String commandString = EditReminderCommand.COMMAND_WORD + " ";

        assertParseFailure(parser, commandString, MESSAGE_INVALID_FORMAT);
    }
}
