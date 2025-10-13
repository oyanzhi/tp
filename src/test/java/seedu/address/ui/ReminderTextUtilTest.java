package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Pure-Java tests to satisfy patch coverage. */
public class ReminderTextUtilTest {
    @Test
    public void formatItem_ok() {
        assertEquals("3. Call", ReminderTextUtil.formatItem(3, "Call "));
    }
    @Test
    public void formatItem_nullText_ok() {
        assertEquals("1. ", ReminderTextUtil.formatItem(1, null));
    }
    @Test
    public void formatItem_badIndex_throws() {
        assertThrows(IllegalArgumentException.class, () -> ReminderTextUtil.formatItem(0, "x"));
    }
}
