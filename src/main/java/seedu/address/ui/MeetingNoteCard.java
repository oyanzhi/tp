package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * A small UI component that displays a single meeting note in two columns:
 * an index label (1-based) and the meeting note text.
 */
public class MeetingNoteCard extends UiPart<Region> {

    public static final String FXML = "MeetingNoteCard.fxml";

    @FXML
    private Label indexLabel;

    @FXML
    private Label contentLabel;

    /**
     * Creates a {@code MeetingNoteCard}.
     *
     * @param displayedIndex 1-based index to show on the card
     * @param content meeting note text to show
     */
    public MeetingNoteCard(int displayedIndex, String content) {
        super(FXML);
        String safe = (content == null) ? "" : content;
        indexLabel.setText(displayedIndex + ".");
        contentLabel.setText(safe);
        getRoot().setAccessibleText(displayedIndex + ". " + safe);

        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(contentLabel, Priority.ALWAYS);

    }
}
