package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
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

        ObservableList<String> reminderList = reminders != null ? reminders : FXCollections.observableArrayList();
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(lv -> new ReminderListCell());
        reminderListView.setOnScroll(event -> event.consume());

        reminderListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reminderListView.getSelectionModel().clearSelection();
        reminderListView.setFocusTraversable(false);
        reminderListView.setFixedCellSize(-1);
        reminderListView.getStyleClass().add("reminder-list");

        Platform.runLater(() -> {
            reminderListView.scrollTo(0);
            reminderListView.getSelectionModel().clearSelection();
        });
    }

    /** Replace the items displayed by this list view. Safe no-op if {@code items} is null. */
    public void setItems(ObservableList<String> items) {
        reminderListView.setItems(items != null ? items : FXCollections.observableArrayList());
    }

    class ReminderListCell extends ListCell<String> {
        private final Label label = new Label();

        ReminderListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            label.getStyleClass().add("reminder-row");
            label.setWrapText(false);
            label.setTextOverrun(OverrunStyle.CLIP);
            setGraphic(label);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setTooltip(null);
                return;
            }
            int oneBased = getIndex() + 1;
            String text = oneBased + ". " + item;
            label.setText(text);
            setGraphic(label);
            setTooltip(new Tooltip(item));
        }
    }
}
