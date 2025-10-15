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
    private static final String INVALID_DEADLINE = "2020-12-25 00:00";

    @Test
    void toModelType_validReminder_convertsSuccessfully() throws IllegalValueException {
        Reminder reminder = new Reminder(VALID_REMINDER_HEADER_AMY, VALID_REMINDER_DEADLINE);
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(reminder);
        Reminder convertedReminder = adaptedReminder.toModelType();
        assertEquals(reminder, convertedReminder);
    }

    @Test
    void toModelType_invalidHeader_throwsIllegalValueException() {
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(INVALID_HEADER, VALID_REMINDER_DEADLINE);
        assertThrows(IllegalValueException.class, adaptedReminder::toModelType);
    }

    @Test
    void toModelType_invalidDeadline_throwsIllegalValueException() {
        JsonAdaptedReminder adaptedReminder = new JsonAdaptedReminder(VALID_REMINDER_HEADER_AMY, INVALID_DEADLINE);
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
