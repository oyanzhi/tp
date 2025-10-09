package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.testutil.FxTestUtils;

/** Exercises the ReminderListPanel: items binding, cell factory and rendered cell. */
public class ReminderListPanelTest {

    @Test
    public void constructor_withItems_rendersFirstCell() {
        FxTestUtils.runOnFxAndWait(() -> {
            ObservableList<String> items = FXCollections.observableArrayList(
                    "Send birthday voucher",
                    "Follow up claim status");

            ReminderListPanel panel = new ReminderListPanel(items);
            Parent root = (Parent) panel.getRoot();
            assertNotNull(root);

            // Host in a JFXPanel to ensure a Scene and CSS/skin are applied in headless CI.
            JFXPanel swingHost = new JFXPanel();
            Scene scene = new Scene(root, 480, 320);
            swingHost.setScene(scene);

            // Find the internal ListView and force it to create visible cells.
            ListView<String> lv = findFirstListView(root);
            assertNotNull(lv, "ListView should exist in ReminderListPanel");
            assertEquals(2, lv.getItems().size(), "Items should be bound to the ListView");

            root.applyCss();
            root.layout();
            lv.getSelectionModel().select(0); // helps force cell creation
            lv.scrollTo(0);

            // Lookup the first visible ListCell
            Node cellNode = lv.lookup(".list-cell");
            assertNotNull(cellNode, "At least one ListCell should be present after layout");
            assertTrue(cellNode instanceof ListCell, "lookup('.list-cell') should return a ListCell");

            ListCell<?> cell = (ListCell<?>) cellNode;
            boolean hasGraphic = cell.getGraphic() != null;
            boolean hasText = cell.getText() != null && !cell.getText().isEmpty();
            assertTrue(hasGraphic || hasText,
                    "Cell should render either a graphic (ReminderCard) or fallback text");
        });
    }

    @Test
    public void constructor_withNull_usesEmptyList() {
        FxTestUtils.runOnFxAndWait(() -> {
            ReminderListPanel panel = new ReminderListPanel(null);
            Parent root = (Parent) panel.getRoot();
            assertNotNull(root);

            JFXPanel swingHost = new JFXPanel();
            Scene scene = new Scene(root, 400, 300);
            swingHost.setScene(scene);

            ListView<String> lv = findFirstListView(root);
            assertNotNull(lv);
            assertEquals(0, lv.getItems().size(), "Null input should result in an empty backing list");
        });
    }

    // ---- helper ----
    @SuppressWarnings("unchecked")
    private static ListView<String> findFirstListView(Node node) {
        if (node instanceof ListView) {
            return (ListView<String>) node;
        }
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                ListView<String> found = findFirstListView(child);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
