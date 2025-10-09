package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Ensures the ReminderListPanel FXML is packaged on the classpath. */
public class ReminderListPanelTest {

    @Test
    public void formatFallbackText_formatsCorrectly() {
        String s = ReminderListPanel.formatFallbackText(3, "Call client");
        assertEquals("3. Call client", s);
    }
}
