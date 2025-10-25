package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOTE_CREATED_BY_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetingnote.MeetingNote;

class JsonAdaptedMeetingNoteTest {
    private static final String INVALID_MEETING_NOTE = "";

    @Test
    void toModelType_validMeetingNote_convertsSuccessfully() throws IllegalValueException {
        MeetingNote meetingNote = new MeetingNote(VALID_MEETING_NOTE_AMY, VALID_MEETING_NOTE_CREATED_BY_AMY);
        JsonAdaptedMeetingNote adaptedMeetingNote = new JsonAdaptedMeetingNote(meetingNote);
        MeetingNote convertedMeetingNote = adaptedMeetingNote.toModelType();
        assertEquals(meetingNote, convertedMeetingNote);
    }

    @Test
    void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedMeetingNote adaptedMeetingNote = new JsonAdaptedMeetingNote(INVALID_MEETING_NOTE,
                VALID_MEETING_NOTE_CREATED_BY_AMY);
        assertThrows(IllegalValueException.class, adaptedMeetingNote::toModelType);
    }


    @Test
    void getNote_validParameter_returnsValidMeetingNote() {
        JsonAdaptedMeetingNote adaptedMeetingNote = new JsonAdaptedMeetingNote(VALID_MEETING_NOTE_AMY,
                VALID_MEETING_NOTE_CREATED_BY_AMY);
        assertEquals(VALID_MEETING_NOTE_AMY, adaptedMeetingNote.getNote());
    }

    @Test
    void getCreatedAt_validParameter_returnsValidDeadline() {
        JsonAdaptedMeetingNote adaptedMeetingNote = new JsonAdaptedMeetingNote(VALID_MEETING_NOTE_AMY,
                VALID_MEETING_NOTE_CREATED_BY_AMY);
        assertEquals(VALID_MEETING_NOTE_CREATED_BY_AMY, adaptedMeetingNote.getCreatedAt());
    }

}

