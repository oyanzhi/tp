package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of meeting notes for a selected client.
 */
public class MeetingNoteListPanel extends UiPart<Region> {

    public static final String FXML = "MeetingNoteListPanel.fxml";

    @FXML
    private ListView<String> meetingNoteListView;

    /**
     * Constructs a panel bound to {@code meetingNotes}. If {@code meetingNotes} is {@code null},
     * an empty observable list is used.
     *
     * @param notes observable list of meetingNote strings (may be {@code null})
     */
    public MeetingNoteListPanel(ObservableList<String> notes) {
        super(FXML);

        ObservableList<String> noteList = notes != null ? notes : FXCollections.observableArrayList();
        meetingNoteListView.setItems(noteList);
        meetingNoteListView.setCellFactory(list -> new MeetingNoteListViewCell());

        meetingNoteListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        meetingNoteListView.getSelectionModel().clearSelection();
        meetingNoteListView.setFocusTraversable(false);
        meetingNoteListView.setFixedCellSize(-1);

        // Disable parent scrolling when mouse is hovering over meeting notes list
        meetingNoteListView.setOnScroll(event -> event.consume());


    }

    public void setItems(ObservableList<String> items) {
        meetingNoteListView.setItems(items != null ? items : FXCollections.observableArrayList());
    }

    private class MeetingNoteListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            try {
                int idx = getIndex() + 1;
                setText(null);
                Region cardRoot = new MeetingNoteCard(idx, item).getRoot();
                cardRoot.prefWidthProperty().bind(meetingNoteListView.widthProperty().subtract(20));
                cardRoot.maxWidthProperty().bind(meetingNoteListView.widthProperty().subtract(20));
                setGraphic(cardRoot);
            } catch (Exception e) {
                int idx = getIndex() + 1;
                setGraphic(null);
                setText(MeetingNoteTextUtil.formatItem(idx, item));
            }
        }
    }
}

