package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Smoke test to exercise the ReminderListPanel constructor and cell factory setup. */
public class ReminderListPanelTest {

    private static final AtomicBoolean jfxStarted = new AtomicBoolean(false);

    @BeforeAll
    static void initJfx() throws InterruptedException {
        if (jfxStarted.getAndSet(true)) {
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void constructor_withItems_noException() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Send birthday voucher", "Follow up claim status");
        assertDoesNotThrow(() -> new ReminderListPanel(items));
    }

    @Test
    public void constructor_withNull_usesEmptyList_noException() {
        assertDoesNotThrow(() -> new ReminderListPanel(null));
    }
}
