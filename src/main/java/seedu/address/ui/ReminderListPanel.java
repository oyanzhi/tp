package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of reminders for a selected client.
 *
 * <p>This class follows the same pattern as other list panels in AB-3:
 * it uses an FXML with no {@code fx:controller}, and sets a cell factory that
 * tries to load a {@link ReminderCard}. If loading fails (e.g. in headless CI),
 * it falls back to plain text so tests can still run without JavaFX.</p>
 */
public class ReminderListPanel extends UiPart<Region> {

    /** Relative path under {@code src/main/resources/view}. */
    public static final String FXML = "ReminderListPanel.fxml";

    @FXML
    private ListView<String> reminderListView;

    /**
     * Constructs a panel bound to {@code reminders}. If {@code reminders} is {@code null},
     * an empty observable list is used.
     *
     * @param reminders observable list of reminder strings (may be {@code null})
     */
    public ReminderListPanel(ObservableList<String> reminders) {
        super(FXML);

        reminderListView.setItems(reminders != null ? reminders : FXCollections.observableArrayList());
        reminderListView.setCellFactory(list -> new ReminderListViewCell() {
            @Override
            protected void updateItem(String text, boolean empty) {
                super.updateItem(text, empty);
                setText(null);
                setGraphic(null);
                if (!empty && text != null) {
                    // correct order: (index, text)
                    setGraphic(new ReminderCard(getIndex() + 1, text).getRoot());
                }
            }
        });


        // Make the list visually read-only & avoid dim selected state
        reminderListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reminderListView.getSelectionModel().clearSelection();
        reminderListView.setFocusTraversable(false);
        reminderListView.setFixedCellSize(-1);

    }

    /** Replace the items displayed by this list view. Safe no-op if {@code items} is null. */
    public void setItems(ObservableList<String> items) {
        reminderListView.setItems(items != null ? items : FXCollections.observableArrayList());
    }

    /**
     * ListCell that renders each reminder.
     *
     * <p>Primary path: render an FXML {@link ReminderCard} and set it as the graphic.</p>
     * <p>Fallback path (for CI): render a plain string so tests do not need a JavaFX runtime.</p>
     */
    private static class ReminderListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            // Try to render the proper FXML card.
            try {
                int idx = getIndex() + 1; // ListCell is 0-based; UI is 1-based.
                setText(null); // prefer graphic when available
                setGraphic(new ReminderCard(idx, item).getRoot());
            } catch (Exception e) {
                // Fallback for headless/unit-test environments: show plain text.
                int idx = getIndex() + 1;
                setGraphic(null);
                setText(ReminderTextUtil.formatItem(idx, item));
            }
        }
    }
}
