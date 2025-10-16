package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.reminder.Reminder.DATE_INPUT_PATTERN;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class ReminderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, null));
        assertThrows(NullPointerException.class, () -> new Reminder("valid header", null));
        assertThrows(NullPointerException.class, () -> new Reminder(null, "2023-10-27 10:30"));
    }

    @Test
    public void constructor_invalidHeader_throwsIllegalArgumentException() {
        String invalidHeader = "";
        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidHeader, "2023-10-27 10:30"));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "100";
        assertThrows(IllegalArgumentException.class, () -> new Reminder("valid header", invalidDeadline));

        String invalidDate = "2023-10-27T10:30:00";
        assertThrows(IllegalArgumentException.class, () -> new Reminder("valid header", invalidDate));
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
        assertFalse(Reminder.isValidDeadline("2023-10-27 10:30"));

        // valid deadline
        assertTrue(Reminder.isValidDeadline("2026-10-27 10:30"));
    }

    @Test
    public void constructor_successfulReminder() {
        String deadline = "2026-10-27 10:30";
        String header = "This is a valid header.";
        Reminder r = new Reminder(header, deadline);
        assertEquals(r.toString(), String.format("%s, due by %s", header, deadline));
    }

    @Test
    public void getHeader_successful() {
        String header = "This is a valid header.";
        String deadline = "2026-10-27 10:30";
        assertEquals(header, new Reminder(header, deadline).getHeader());
    }

    @Test
    public void getDeadline_successful() {
        String header = "This is a valid header.";
        String deadline = "2026-10-27 10:30";
        LocalDateTime expectedDeadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern(DATE_INPUT_PATTERN));
        assertEquals(expectedDeadline, new Reminder(header, deadline).getDeadline());
    }


    @Test
    public void equals() {
        String deadline = "2026-10-27 10:30";
        String header = "This is a valid header.";

        String differentDeadline = "2027-10-27 10:30";
        String differentHeader = "Different header";
        Reminder r = new Reminder(header, deadline);

        //same values -> true
        assertTrue(r.equals(new Reminder(header, deadline)));

        //same object -> true
        assertTrue(r.equals(r));

        // null -> returns false
        assertFalse(r.equals(null));

        // different types -> returns false
        assertFalse(r.equals(""));

        // different header -> returns false
        assertFalse(r.equals(new Reminder(differentHeader, deadline)));

        // different deadline -> returns false
        assertFalse(r.equals(new Reminder(header, differentDeadline)));
    }

}
