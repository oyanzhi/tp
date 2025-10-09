package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FxTestUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Smoke tests: constructor + cell factory for ReminderListPanel. */
public class ReminderListPanelTest {

    @Test
    public void constructor_withItems_noException() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Send birthday voucher", "Follow up claim status");
        FxTestUtils.runOnFxAndWait(() ->
                assertDoesNotThrow(() -> new ReminderListPanel(items))
        );
    }

    @Test
    public void constructorWithNullUsesEmptyListNoException() {
        FxTestUtils.runOnFxAndWait(() ->
                assertDoesNotThrow(() -> new ReminderListPanel(null))
        );
    }
}
