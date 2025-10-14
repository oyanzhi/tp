package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_HEADER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_HEADER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
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
    public void execute_addReminder_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        AddReminderCommand command = new AddReminderCommand(INDEX_FIRST_PERSON, reminder);

        Person editedPerson = personToEdit.addReminder(reminder);
        String expectedMessage = String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS,
                editedPerson.getName(), reminder);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        AddReminderCommand command = new AddReminderCommand(outOfBoundIndex, reminder);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameReminderDifferentPersons_success() {
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        AddReminderCommand commandFirst = new AddReminderCommand(INDEX_FIRST_PERSON, reminder);
        AddReminderCommand commandSecond = new AddReminderCommand(INDEX_SECOND_PERSON, reminder);

        // Apply to first person
        Person editedFirst = firstPerson.addReminder(reminder);
        Model expectedModelFirst = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelFirst.setPerson(firstPerson, editedFirst);

        assertCommandSuccess(commandFirst, model,
                String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS, editedFirst.getName(), reminder),
                expectedModelFirst);

        // Apply to second person (should still succeed)
        Person editedSecond = secondPerson.addReminder(reminder);
        Model expectedModelSecond = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelSecond.setPerson(secondPerson, editedSecond);

        assertCommandSuccess(commandSecond, model,
                String.format(AddReminderCommand.MESSAGE_ADD_REMINDER_SUCCESS, editedSecond.getName(), reminder),
                expectedModelSecond);
    }

    @Test
    public void equals() {
        Reminder reminder1 = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        Reminder reminder2 = new Reminder(VALID_REMINDER_HEADER_BOB, VALID_REMINDER_DEADLINE);

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
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        AddReminderCommand command = new AddReminderCommand(INDEX_FIRST_PERSON, reminder);
        String expected = AddReminderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", reminder=" + reminder + "}";
        assertEquals(expected, command.toString());
    }
}
