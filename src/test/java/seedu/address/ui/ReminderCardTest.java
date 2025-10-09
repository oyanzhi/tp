package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Smoke test to exercise the ReminderCard constructor and its FXML load. */
public class ReminderCardTest {

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
    public void constructor_loadsFxml_noException() {
        assertDoesNotThrow(() -> new ReminderCard(1, "Renewal call on 21/11/2025 14:30"));
    }
}
