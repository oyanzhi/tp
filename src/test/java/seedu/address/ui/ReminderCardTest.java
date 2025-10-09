package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import seedu.address.testutil.FxTestUtils;

/** Exercises the ReminderCard constructor + FXML load and inspects rendered labels. */
public class ReminderCardTest {

    @Test
    public void constructor_loadsAndRendersLabels() {
        FxTestUtils.runOnFxAndWait(() -> {
            ReminderCard card = new ReminderCard(1, "Renewal call on 21/11/2025 14:30");
            Parent root = (Parent) card.getRoot();
            assertNotNull(root, "Root should not be null");

            // Expected structure from ReminderCard.fxml:
            // AnchorPane -> HBox -> [Label index, Label content]
            AnchorPane ap = (AnchorPane) root;
            HBox row = null;
            for (Node n : ap.getChildrenUnmodifiable()) {
                if (n instanceof HBox) {
                    row = (HBox) n;
                    break;
                }
            }
            if (row == null && !ap.getChildrenUnmodifiable().isEmpty()) {
                Node maybe = ap.getChildrenUnmodifiable().get(0);
                if (maybe instanceof HBox) {
                    row = (HBox) maybe;
                } else if (maybe instanceof AnchorPane) {
                    AnchorPane inner = (AnchorPane) maybe;
                    for (Node n : inner.getChildrenUnmodifiable()) {
                        if (n instanceof HBox) {
                            row = (HBox) n;
                            break;
                        }
                    }
                }
            }
            assertNotNull(row, "HBox row must exist");

            Label indexLbl = (Label) row.getChildren().get(0);
            Label contentLbl = (Label) row.getChildren().get(1);

            assertTrue(indexLbl.getText().startsWith("1"),
                    "Index label should start with '1': " + indexLbl.getText());
            assertTrue(contentLbl.getText().contains("Renewal call"),
                    "Content label should contain reminder text: " + contentLbl.getText());
        });
    }
}
