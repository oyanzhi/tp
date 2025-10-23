package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeleteMeetingNoteCommand.MESSAGE_DELETE_MEETING_NOTE_SUCCESS;
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
import seedu.address.model.meetingnote.MeetingNote;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model and {@code AddMeetingNoteCommand}) and unit tests for
 * {@code DeleteMeetingNoteCommand}.
 */
public class DeleteMeetingNoteCommandTest {
    private static final Index INDEX_FIRST_NOTE = Index.fromOneBased(1);
    private static final Index INDEX_SECOND_NOTE = Index.fromOneBased(2);
    private static final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeAll
    public static void addMeetingNotes() {
        MeetingNote meetingNoteOneToAdd = new MeetingNote("This is a valid note.", "2026-10-27 10:30");
        MeetingNote meetingNoteTwoToAdd = new MeetingNote("This is also valid.", "2026-10-27 10:30");

        AddMeetingNoteCommand addMeetingNoteCommand;

        try {
            addMeetingNoteCommand = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNoteOneToAdd);
            addMeetingNoteCommand.execute(model);

            addMeetingNoteCommand = new AddMeetingNoteCommand(INDEX_FIRST_PERSON, meetingNoteTwoToAdd);
            addMeetingNoteCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

    }

    @Test
    public void execute_validIndexes_success() {
        List<Person> personList = model.getFilteredPersonList();
        Person personToDeleteFrom = personList.get(INDEX_FIRST_PERSON.getZeroBased());

        ArrayList<MeetingNote> meetingNotes = personToDeleteFrom.getMeetingNotes();
        MeetingNote meetingNoteToDelete = meetingNotes.get(INDEX_FIRST_NOTE.getZeroBased());

        DeleteMeetingNoteCommand deleteMeetingNoteCommand = new DeleteMeetingNoteCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_NOTE);

        String expectedMessage = String.format(MESSAGE_DELETE_MEETING_NOTE_SUCCESS,
                Messages.format(personToDeleteFrom),
                INDEX_FIRST_NOTE.getOneBased(),
                meetingNoteToDelete);

        try {
            assertEquals(deleteMeetingNoteCommand.execute(model).getFeedbackToUser(), expectedMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void invalidClientIndex_fail() {
        int invalidClientIndex = model.getFilteredPersonList().size() + 1;
        Index INVALID_CLIENT_INDEX = Index.fromZeroBased(invalidClientIndex);
        DeleteMeetingNoteCommand deleteMeetingNoteCommand = new DeleteMeetingNoteCommand(INVALID_CLIENT_INDEX,
                INDEX_FIRST_NOTE);
        assertCommandFailure(deleteMeetingNoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void invalidMeetingNoteIndex_fail() {
        List<Person> personList = model.getFilteredPersonList();
        Person personToDeleteFrom = personList.get(INDEX_FIRST_PERSON.getZeroBased());
        int invalidMeetingNoteIndex = personToDeleteFrom.getMeetingNotes().size() + 1;
        Index INVALID_MEETING_NOTE_INDEX = Index.fromZeroBased(invalidMeetingNoteIndex);
        DeleteMeetingNoteCommand deleteMeetingNoteCommand = new DeleteMeetingNoteCommand(INDEX_FIRST_PERSON,
                INVALID_MEETING_NOTE_INDEX);
        assertCommandFailure(deleteMeetingNoteCommand, model, Messages.MESSAGE_INVALID_MEETING_NOTE_INDEX);
    }

    @Test
    public void toStringMethod() {
        DeleteMeetingNoteCommand deleteMeetingNoteCommand = new DeleteMeetingNoteCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_NOTE);
        String expected = DeleteMeetingNoteCommand.class.getCanonicalName() + "{clientIndex=" + INDEX_FIRST_PERSON
                + ", " + "meetingNoteIndex=" + INDEX_FIRST_NOTE + "}";
        assertEquals(deleteMeetingNoteCommand.toString(), expected);
    }

    @Test
    public void equals() {
        DeleteMeetingNoteCommand deleteMeetingNoteCommand = new DeleteMeetingNoteCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_NOTE);

        //returns true with itself
        assertTrue(deleteMeetingNoteCommand.equals(deleteMeetingNoteCommand));

        DeleteMeetingNoteCommand otherDeleteMeetingNoteCommand = new DeleteMeetingNoteCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_NOTE);

        //returns true with same input
        assertTrue(deleteMeetingNoteCommand.equals(otherDeleteMeetingNoteCommand));

        //returns false with null
        assertFalse(deleteMeetingNoteCommand.equals(null));

        //returns false with different client index
        DeleteMeetingNoteCommand deleteMeetingNoteCommandDifferentClient = new DeleteMeetingNoteCommand(
                INDEX_SECOND_PERSON, INDEX_FIRST_NOTE);
        assertFalse(deleteMeetingNoteCommand.equals(deleteMeetingNoteCommandDifferentClient));

        //returns false with different meeting note index
        DeleteMeetingNoteCommand deleteMeetingNoteCommandDifferentReminder = new DeleteMeetingNoteCommand(
                INDEX_FIRST_PERSON, INDEX_SECOND_NOTE);
        assertFalse(deleteMeetingNoteCommand.equals(deleteMeetingNoteCommandDifferentReminder));
    }

}
