package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.junit.jupiter.api.Test;

/** CI-safe: ensure the ReminderListPanel FXML is packaged and loadable. */
public class ReminderListPanelTest {

    @Test
    public void fxml_isPresentOnClasspath() {
        URL url = ReminderListPanel.class.getResource("/view/ReminderListPanel.fxml");
        assertNotNull(url, "ReminderListPanel.fxml should be on the classpath under /view/");
    }
}
