package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A small UI component that displays a single reminder in a two-column style:
 * an index label (1-based) and the reminder text.
 *
 * <p>Its FXML has no {@code fx:controller}; the controller instance is supplied
 * by {@link UiPart} via the constructor.</p>
 */
public class ReminderCard extends UiPart<Region> {

    /** Relative path under {@code src/main/resources/view}. */
    public static final String FXML = "ReminderCard.fxml";

    @FXML
    private Label indexLabel;

    @FXML
    private Label contentLabel;

    /**
     * Creates a {@code ReminderCard}.
     *
     * @param displayedIndex 1-based index to show on the card
     * @param content reminder text to show
     */
    public ReminderCard(int displayedIndex, String content) {
        super(FXML);
        // Defensive defaults to avoid NPEs if cell text is null.
        String safe = (content == null) ? "" : content;
        indexLabel.setText(displayedIndex + ".");
        contentLabel.setText(safe);
        // Prefer the label content for accessibility; graphics are set by the ListCell.
        getRoot().setAccessibleText(displayedIndex + ". " + safe);
    }
}
