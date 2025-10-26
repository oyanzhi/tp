package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ListActiveCommand}.
 */
public class ListActiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsActivePersonsOnly() {
        ListActiveCommand command = new ListActiveCommand();
        expectedModel.updateFilteredPersonList(person -> !person.isArchived());
        assertCommandSuccess(command, model, ListActiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listAfterArchive_showsOnlyUnarchived() {
        // archive first person
        model.setPerson(model.getFilteredPersonList().get(0),
                model.getFilteredPersonList().get(0).archive());

        ListActiveCommand command = new ListActiveCommand();
        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(0),
                expectedModel.getFilteredPersonList().get(0).archive());
        expectedModel.updateFilteredPersonList(person -> !person.isArchived());

        assertCommandSuccess(command, model, ListActiveCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
