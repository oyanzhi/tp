package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOTE_CREATED_BY_AMY;
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
import seedu.address.model.meetingnote.MeetingNote;
import seedu.address.model.person.Person;

// Fixed timestamp is used here since AddMeetingNoteCommand does not generate the timestamp itself
public class AddMeetingNoteCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addMeetingNote_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MeetingNote meetingNote = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);
        AddMeetingNoteCommand command = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNote);

        Person editedPerson = personToEdit.addMeetingNote(meetingNote);
        String expectedMessage = String.format(AddMeetingNoteCommand.MESSAGE_ADD_MEETING_NOTE_SUCCESS,
                editedPerson.getName(), meetingNote);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MeetingNote meetingNote = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);
        AddMeetingNoteCommand command = new AddMeetingNoteCommand(outOfBoundIndex, meetingNote);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateMeetingNote_throwsCommandException() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MeetingNote newNote = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);

        // Add meeting note
        Person editedPerson = targetPerson.addMeetingNote(newNote);
        model.setPerson(targetPerson, editedPerson);

        // Try to add the same note again
        AddMeetingNoteCommand command = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, newNote);

        assertCommandFailure(command, model, AddMeetingNoteCommand.MESSAGE_DUPLICATE_MEETING_NOTE);
    }


    @Test
    public void execute_sameMeetingNoteDifferentPersons_success() {
        MeetingNote meetingNote = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        AddMeetingNoteCommand commandFirst = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNote);
        AddMeetingNoteCommand commandSecond = new AddMeetingNoteCommand(INDEX_SECOND_PERSON, meetingNote);

        // Apply to first person
        Person editedFirst = firstPerson.addMeetingNote(meetingNote);
        Model expectedModelFirst = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelFirst.setPerson(firstPerson, editedFirst);

        assertCommandSuccess(commandFirst, model,
                String.format(AddMeetingNoteCommand.MESSAGE_ADD_MEETING_NOTE_SUCCESS,
                        editedFirst.getName(), meetingNote),
                expectedModelFirst);

        // Apply to second person (should still succeed)
        Person editedSecond = secondPerson.addMeetingNote(meetingNote);
        Model expectedModelSecond = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelSecond.setPerson(secondPerson, editedSecond);

        assertCommandSuccess(commandSecond, model,
                String.format(AddMeetingNoteCommand.MESSAGE_ADD_MEETING_NOTE_SUCCESS,
                        editedSecond.getName(), meetingNote),
                expectedModelSecond);
    }

    @Test
    public void equals() {
        MeetingNote meetingNote1 = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);
        MeetingNote meetingNote2 = new MeetingNote(VALID_MEETING_NOTE_BOB, VALID_MEETING_NOTE_CREATED_BY_AMY);

        AddMeetingNoteCommand addMeetingNoteFirstCommand = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNote1);
        AddMeetingNoteCommand addMeetingNoteSecondCommand = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNote2);
        AddMeetingNoteCommand addMeetingNoteThirdCommand = new AddMeetingNoteCommand(INDEX_SECOND_PERSON, meetingNote1);

        // same object -> returns true
        assertTrue(addMeetingNoteFirstCommand.equals(addMeetingNoteFirstCommand));

        // same values -> returns true
        AddMeetingNoteCommand addMeetingNoteFirstCommandCopy =
                new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNote1);
        assertTrue(addMeetingNoteFirstCommand.equals(addMeetingNoteFirstCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingNoteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingNoteFirstCommand.equals(null));

        // different meeting note -> returns false
        assertFalse(addMeetingNoteFirstCommand.equals(addMeetingNoteSecondCommand));

        // different index -> returns false
        assertFalse(addMeetingNoteFirstCommand.equals(addMeetingNoteThirdCommand));
    }

    @Test
    public void toStringMethod() {
        MeetingNote meetingNote = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);
        AddMeetingNoteCommand command = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNote);
        String expected = AddMeetingNoteCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", meeting note=" + meetingNote + "}";
        assertEquals(expected, command.toString());
    }
}
