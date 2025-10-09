package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddReminderCommandParserTest {
    private final AddReminderParser parser = new AddReminderParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = "1 h/client's birthday d/2026-10-15";

        Reminder expectedReminder = new Reminder(Index.fromOneBased(1), "client's birthday", "2026-10-15");
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);

        AddReminderCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }

}
