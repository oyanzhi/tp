package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.junit.jupiter.api.Test;

/** CI-safe test: ensure the FXML is packaged and loadable. */
public class ReminderCardTest {

    @Test
    public void fxml_isPresentOnClasspath() {
        URL url = ReminderCard.class.getResource("/view/ReminderCard.fxml");
        assertNotNull(url, "ReminderCard.fxml should be on the classpath under /view/");
    }
}
