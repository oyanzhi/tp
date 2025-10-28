package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Panel containing the list of all reminders
 */
public class GeneralReminderListPanel extends UiPart<Region> {
    private static final String FXML = "GeneralReminderListPanel.fxml";

    @FXML
    private ListView<Pair<Person, Reminder>> generalReminderListView;

    /**
     * Creates a {@code GeneralReminderListPanel} with the given {@code ObservableList}.
     */
    public GeneralReminderListPanel(ObservableList<Pair<Person, Reminder>> generalReminderList) {
        super(FXML);
        generalReminderListView.setItems(
                generalReminderList != null ? generalReminderList : FXCollections.observableArrayList());
        generalReminderListView.setCellFactory(listView -> new GeneralReminderListViewCell());
    }

    static class GeneralReminderListViewCell extends ListCell<Pair<Person, Reminder>> {
        @Override
        protected void updateItem(Pair<Person, Reminder> pair, boolean empty) {
            super.updateItem(pair, empty);

            if (empty || pair == null) {
                setGraphic(null);
                setText(null);
            } else {
                String toDisplay = String.format("%s for %s", pair.getValue(), pair.getKey().getName());
                setGraphic(new ReminderCard(getIndex() + 1, toDisplay).getRoot());
            }
        }
    }
}
