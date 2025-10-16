package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Contains integration tests (interaction with the Model and {@code AddReminderCommand}) and unit tests for
 * {@code DeleteReminderCommand}.
 */
public class DeleteReminderCommandTest {
    private static final Index INDEX_FIRST_REMINDER = Index.fromOneBased(1);
    private static final Index INDEX_SECOND_REMINDER = Index.fromOneBased(2);
    private static final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeAll
    public static void addReminders() {
        Reminder reminderOneToAdd = new Reminder("This is a valid reminder.", "2026-10-27 10:30");
        Reminder reminderTwoToAdd = new Reminder("This is also valid.", "2026-10-27 10:30");

        AddReminderCommand addReminderCommand;

        try {
            addReminderCommand = new AddReminderCommand(INDEX_FIRST_PERSON, reminderOneToAdd);
            addReminderCommand.execute(model);

            addReminderCommand = new AddReminderCommand(INDEX_FIRST_PERSON, reminderTwoToAdd);
            addReminderCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

    }

    @Test
    public void execute_validIndexes_success() {
        List<Person> personList = model.getFilteredPersonList();
        Person personToDeleteFrom = personList.get(INDEX_FIRST_PERSON.getZeroBased());

        ArrayList<Reminder> reminderList = personToDeleteFrom.getReminders();
        Reminder reminderToDelete = reminderList.get(INDEX_FIRST_REMINDER.getZeroBased());

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(personToDeleteFrom),
                INDEX_FIRST_REMINDER.getOneBased(),
                reminderToDelete);

        try {
            assertEquals(deleteReminderCommand.execute(model).getFeedbackToUser(), expectedMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void toStringMethod() {
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_REMINDER);
        String expected = DeleteReminderCommand.class.getCanonicalName() + "{clientIndex=" + INDEX_FIRST_PERSON
                + ", " + "reminderIndex=" + INDEX_FIRST_REMINDER + "}";
        assertEquals(deleteReminderCommand.toString(), expected);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_REMINDER);

        //returns true with itself
        assertTrue(deleteReminderCommand.equals(deleteReminderCommand));

        DeleteReminderCommand otherDeleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_REMINDER);

        //returns true with same input
        assertTrue(deleteReminderCommand.equals(otherDeleteReminderCommand));

        //returns false with null
        assertFalse(deleteReminderCommand.equals(null));

        //returns false with different client index
        DeleteReminderCommand deleteReminderCommandDifferentClient = new DeleteReminderCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_REMINDER);
        assertFalse(deleteReminderCommand.equals(deleteReminderCommandDifferentClient));

        //returns false with different reminder index
        DeleteReminderCommand deleteReminderCommandDifferentReminder = new DeleteReminderCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_REMINDER);
        assertFalse(deleteReminderCommand.equals(deleteReminderCommandDifferentReminder));
    }

}
