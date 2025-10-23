package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

public class EditReminderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editReminder_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Reminder reminder = new Reminder("Custom Reminder for Edit Test", VALID_REMINDER_DEADLINE);

        // will not require add reminder command
        personToEdit = personToEdit.addReminder(reminder);
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);

        int reminderPosition = personToEdit.getReminders().indexOf(reminder);

        if (reminderPosition == -1) {
            // should be able to find the index since reminder is just added before this line
            fail();
        }

        Index reminderIndex = Index.fromZeroBased(reminderPosition);

        Reminder editedReminder = new Reminder("Custom Edited Reminder for Edit Test", VALID_REMINDER_DEADLINE);

        EditReminderCommand editReminderCommand = new EditReminderCommand(INDEX_FIRST_PERSON,
                reminderIndex,
                editedReminder);

        Person editedPerson = personToEdit.removeReminder(reminder);
        editedPerson = editedPerson.addReminder(editedReminder);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS,
                editedPerson.getName(),
                reminderIndex.getOneBased(),
                reminder,
                editedReminder);
        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }
}
