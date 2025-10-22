package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;
import javafx.scene.control.ListView;
import seedu.address.model.reminder.Reminder;

public class GeneralReminderListPanel extends UiPart<Region> {
    private static final String FXML = "GeneralReminderListPanel.fxml";

    @FXML
    private ListView<Reminder> generalReminderListView;

    public GeneralReminderListPanel(ObservableList<Reminder> reminderList) {
        super(FXML);
        generalReminderListView.setItems(reminderList);
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
