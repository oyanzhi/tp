package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ListArchiveCommand}.
 */
public class ListArchiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noArchivedPersons_showsEmptyList() {
        ListArchiveCommand command = new ListArchiveCommand();
        expectedModel.updateFilteredPersonList(person -> person.isArchived());
        assertCommandSuccess(command, model, ListArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withArchivedPersons_showsArchivedOnly() {
        model.setPerson(model.getFilteredPersonList().get(0),
                model.getFilteredPersonList().get(0).archive());

        ListArchiveCommand command = new ListArchiveCommand();
        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(0),
                expectedModel.getFilteredPersonList().get(0).archive());
        expectedModel.updateFilteredPersonList(person -> person.isArchived());

        assertCommandSuccess(command, model, ListArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
