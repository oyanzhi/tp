package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;


/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final int displayedIndex;

    @FXML
    private SplitPane cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label starred;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox leftBox;
    @FXML
    private AnchorPane remindersPlaceholder;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        this.displayedIndex = displayedIndex;

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        if (person.isStarred()) {
            starred.setText("★");
            starred.setStyle("-fx-text-fill: gold; -fx-font-size: 16px;");
        } else {
            starred.setText("");
        }
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        ObservableList<String> reminderTexts = deriveReminderTexts(person);
        ReminderListPanel reminderListPanel = new ReminderListPanel(reminderTexts);
        remindersPlaceholder.getChildren().add(reminderListPanel.getRoot());
        Region remindersRoot = reminderListPanel.getRoot();
        remindersPlaceholder.prefHeightProperty().bind(leftBox.heightProperty());
        remindersPlaceholder.maxHeightProperty().bind(leftBox.heightProperty());
        remindersRoot.prefHeightProperty().bind(leftBox.heightProperty());
        remindersRoot.maxHeightProperty().bind(leftBox.heightProperty());
        AnchorPane.setTopAnchor(reminderListPanel.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(reminderListPanel.getRoot(), 0.0);
        AnchorPane.setRightAnchor(reminderListPanel.getRoot(), 0.0);
        AnchorPane.setBottomAnchor(reminderListPanel.getRoot(), 0.0);
    }

    /**
     * Returns observable display strings for this person's reminders with minimal coupling.
     * Works with any of the following model shapes:
     *   {@code person.getReminderStrings() -> Collection<String>} (or {@code String[]} )
     *   {@code person.getReminders() -> Collection<?>} (or {@code Object[]} ), using {@code toString()}
     *   Falls back to empty if neither exists.
     */
    private ObservableList<String> deriveReminderTexts(Person p) {
        Collection<Reminder> src = p.getReminders();
        List<Reminder> list = new ArrayList<>(src);

        List<String> out = new ArrayList<>(list.size());
        for (Reminder r : list) {
            out.add(String.valueOf(r));
        }
        return javafx.collections.FXCollections.observableArrayList(out);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return displayedIndex == card.displayedIndex
                && Objects.equals(person, card.person);
    }
}
