package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;


public class AddReminderCommandParserTest {
    private final AddReminderParser parser = new AddReminderParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = "1 h/client's birthday d/2026-10-15T09:00";

        Reminder expectedReminder = new Reminder("client's birthday", "2026-10-15T09:00");
        AddReminderCommand expectedCommand = new AddReminderCommand(Index.fromZeroBased(1), expectedReminder);

        AddReminderCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }

}
