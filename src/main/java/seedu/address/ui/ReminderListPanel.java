package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing a scrollable list of reminders for a single client.
 */
public class ReminderListPanel extends UiPart<Region> {

    /** Path to the FXML resource. */
    public static final String FXML = "ReminderListPanel.fxml";

    @FXML private ListView<String> reminderListView;

    /**
     * Constructs a {@code ReminderListPanel}.
     *
     * @param reminders Observable list backing the view. If {@code null}, an empty list is used.
     */
    public ReminderListPanel(ObservableList<String> reminders) {
        super(FXML);

        ObservableList<String> safeList = (reminders == null)
                ? FXCollections.observableArrayList()
                : reminders;

        // Bind the list and install a cell factory that renders each row using ReminderCard.
        reminderListView.setItems(safeList);
        reminderListView.setCellFactory(list -> new ListCell<String>() {
            @Override
            protected void updateItem(String text, boolean empty) {
                super.updateItem(text, empty);
                if (empty || text == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                int idx = getIndex() + 1;
                try {
                    setGraphic(new ReminderCard(idx, text).getRoot());
                    setText(null); // we render via graphic only
                } catch (RuntimeException ex) {
                    // Fallback rendering so UI/tests don't crash if ReminderCard FXML fails in some envs.
                    setGraphic(null);
                    setText(idx + ". " + text);
                }
            }
        });

        // Cosmetic: avoid stealing focus when navigating the parent card.
        reminderListView.setFocusTraversable(false);
    }

    static String formatFallbackText(int index, String text) {
        return index + ". " + text;
    }
}
