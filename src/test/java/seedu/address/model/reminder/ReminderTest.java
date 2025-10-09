package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ReminderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, null));
        assertThrows(NullPointerException.class, () -> new Reminder(null, "valid header"));
        assertThrows(NullPointerException.class, () -> new Reminder("2023-10-27T10:30:00", null));
    }

    @Test
    public void constructor_invalidHeader_throwsIllegalArgumentException() {
        String invalidHeader = "";
        assertThrows(IllegalArgumentException.class, () -> new Reminder("2023-10-27T10:30:00", invalidHeader));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "100";
        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidDeadline,
                "valid header"));

        String invalidDate = "2023-10-27T10:30:00";
        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidDate,
                "valid header"));
    }

    @Test
    public void isValidHeader() {
        // null header
        assertThrows(NullPointerException.class, () -> Reminder.isValidHeader(null));

        // invalid header
        assertFalse(Reminder.isValidHeader(""));

        // valid header
        assertTrue(Reminder.isValidHeader("this is a valid header."));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Reminder.isValidDeadline(null));

        // invalid deadline (deadline before now)
        assertFalse(Reminder.isValidDeadline("2023-10-27T10:30:00"));

        // valid deadline
        assertTrue(Reminder.isValidDeadline("2026-10-27T10:30:00"));
    }

}
