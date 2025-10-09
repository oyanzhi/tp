package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FxTestUtils;

/** Smoke test: constructor + FXML load for ReminderCard. */
public class ReminderCardTest {

    @Test
    public void constructor_loadsFxml_noException() {
        FxTestUtils.runOnFxAndWait(() ->
                assertDoesNotThrow(() -> new ReminderCard(1, "Renewal call on 21/11/2025 14:30"))
        );
    }
}
