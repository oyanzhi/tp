package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_HEADER_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Reminder;

class JsonAdaptedReminderTest {
    private static final String INVALID_HEADER = "";
    private static final String VALID_DEADLINE_BEFORE_CURRENT = "2020-12-25 00:00";
    private static final String INVALID_DEADLINE_FORMAT = "2020-12-25 0000";

    @Test
    void toModelType_validReminder_convertsSuccessfully() throws IllegalValueException {

        // deadline after current
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(reminder);
        Reminder convertedReminder = adaptedReminder.toModelType();
        assertEquals(reminder, convertedReminder);

        // deadline before current
        Reminder deadlineBeforeCurrentReminder = new Reminder(VALID_REMINDER_HEADER_AMY,
                VALID_DEADLINE_BEFORE_CURRENT);
        JsonAdaptedReminder adaptedDeadlineBeforeCurrentReminder = new JsonAdaptedReminder(VALID_REMINDER_HEADER_AMY,
                VALID_DEADLINE_BEFORE_CURRENT);
        assertEquals(deadlineBeforeCurrentReminder, adaptedDeadlineBeforeCurrentReminder.toModelType());
    }

    @Test
    void toModelType_invalidHeader_throwsIllegalValueException() {
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(INVALID_HEADER, VALID_REMINDER_DEADLINE);
        assertThrows(IllegalValueException.class, adaptedReminder::toModelType);
    }

    @Test
    void toModelType_invalidDeadlineFormat_throwsIllegalValueException() {
        String invalidDeadlineFormat = "2020-12-25 0000";
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(VALID_REMINDER_HEADER_AMY,
                INVALID_DEADLINE_FORMAT);
        assertThrows(IllegalValueException.class, adaptedReminder::toModelType);
    }

    @Test
    void getReminderHeader_validParameter_returnsValidHeader() {
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(VALID_REMINDER_HEADER_AMY,
                VALID_REMINDER_DEADLINE);
        assertEquals(VALID_REMINDER_HEADER_AMY, adaptedReminder.getReminderHeader());
    }

    @Test
    void getReminderDeadline_validParameter_returnsValidDeadline() {
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(VALID_REMINDER_HEADER_AMY,
                VALID_REMINDER_DEADLINE);
        assertEquals(VALID_REMINDER_DEADLINE, adaptedReminder.getReminderDeadline());
    }

    @Test
    void deadlineFormatting_isCorrect() {
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(reminder);
        String formattedDeadline = adaptedReminder.getReminderDeadline();
        assertEquals(VALID_REMINDER_DEADLINE, formattedDeadline);
    }
}
