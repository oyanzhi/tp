package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI component that displays a single reminder line for a client.
 */

public class ReminderCard extends UiPart<Region> {

    /** Path to the FXML resource. */
    public static final String FXML = "ReminderCard.fxml";

    @FXML private Label index;
    @FXML private Label content;

    /**
     * Creates a {@code ReminderCard} with the given display index and text.
     *
     * @param displayedIndex 1-based index to show before the reminder.
     * @param text The reminder text to display. Must not be {@code null}.
     */
    public ReminderCard(int displayedIndex, String text) {
        super(FXML);
        // Populate simple labels.
        index.setText(displayedIndex + ".");
        content.setText(text);
    }
}
