package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

public class AddReminderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Reminder reminder = new Reminder("Meet client on Monday", "2026-10-12T09:00");
        AddReminderCommand command = new AddReminderCommand(outOfBoundIndex, reminder);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Reminder reminder1 = new Reminder("Meet client on Monday", "2026-10-12T09:00");
        Reminder reminder2 = new Reminder("Meet client on Tuesday", "2026-10-12T09:00");

        AddReminderCommand addReminderFirstCommand = new AddReminderCommand(INDEX_FIRST_PERSON, reminder1);
        AddReminderCommand addReminderSecondCommand = new AddReminderCommand(INDEX_FIRST_PERSON, reminder2);

        // same object -> returns true
        assertTrue(addReminderFirstCommand.equals(addReminderFirstCommand));

        // same values -> returns true
        AddReminderCommand addReminderFirstCommandCopy = new AddReminderCommand(INDEX_FIRST_PERSON, reminder1);
        assertTrue(addReminderFirstCommand.equals(addReminderFirstCommandCopy));

        // different types -> returns false
        assertFalse(addReminderFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addReminderFirstCommand.equals(null));

        // different reminder -> returns false
        assertFalse(addReminderFirstCommand.equals(addReminderSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Reminder reminder = new Reminder("Meet client on Monday", "2026-10-12T09:00");
        AddReminderCommand command = new AddReminderCommand(INDEX_FIRST_PERSON, reminder);
        String expected = AddReminderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", reminder=" + reminder + "}";
        assertEquals(expected, command.toString());
    }
}
