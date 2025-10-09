package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel; // initializes JavaFX toolkit

/**
 * Minimal utilities to run code on the JavaFX Application Thread in tests.
 */
public final class FxTestUtils {

    private static volatile boolean jfxReady = false;

    private FxTestUtils() {}

    /** Ensures JavaFX toolkit is initialized (idempotent). */
    public static synchronized void initFx() {
        if (jfxReady) {
            return;
        }
        // Creating a JFXPanel initializes the JavaFX runtime safely and only once.
        new JFXPanel();
        jfxReady = true;
    }

    /** Runs the given runnable on the FX thread and waits for completion. */
    public static void runOnFxAndWait(Runnable r) {
        initFx();
        if (Platform.isFxApplicationThread()) {
            r.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                r.run();
            } finally {
                latch.countDown();
            }
        });
        try {
            if (!latch.await(5, TimeUnit.SECONDS)) {
                fail("Timed out waiting for JavaFX task.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted while waiting for JavaFX task: " + e);
        }
    }
}