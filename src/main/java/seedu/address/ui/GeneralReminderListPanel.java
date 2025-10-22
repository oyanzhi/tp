package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.Reminder;

/**
 * Panel containing the list of all reminders
 */
public class GeneralReminderListPanel extends UiPart<Region> {
    private static final String FXML = "GeneralReminderListPanel.fxml";

    @FXML
    private ListView<Reminder> generalReminderListView;

    /**
     * Creates a {@code GeneralReminderListPanel} with the given {@code ObservableList}.
     */
    public GeneralReminderListPanel(ObservableList<Reminder> reminderList) {
        super(FXML);
        generalReminderListView.setItems(reminderList != null ? reminderList : FXCollections.observableArrayList());
        generalReminderListView.setCellFactory(listView -> new GeneralReminderListViewCell());
    }

    static class GeneralReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(getIndex() + 1, reminder.toString()).getRoot());
            }
        }
    }
}
