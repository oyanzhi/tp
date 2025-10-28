package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.person.Person.STARRED_STATUS_COMPARATOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnstarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnstar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // Star the person before unstar
        Person starredPerson = personToUnstar.rebuildWithStarredStatus(true);
        model.setPerson(personToUnstar, starredPerson);

        UnstarCommand unstarCommand = new UnstarCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnstarCommand.MESSAGE_UNSTARRED_PERSON_SUCCESS,
                Messages.format(personToUnstar));

        Person unstarredPerson = personToUnstar.rebuildWithStarredStatus(false);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUnstar, unstarredPerson);
        expectedModel.sortPersons(STARRED_STATUS_COMPARATOR);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(unstarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAlreadyUnstarred_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnstar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnstarCommand unstarCommand = new UnstarCommand(INDEX_FIRST_PERSON);

        Person unstarredPerson = personToUnstar.rebuildWithStarredStatus(false);
        model.setPerson(personToUnstar, unstarredPerson);

        assertCommandFailure(unstarCommand, model, UnstarCommand.MESSAGE_PERSON_IS_UNSTARRED);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnstarCommand unstarCommand = new UnstarCommand(outOfBoundIndex);

        assertCommandFailure(unstarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnstar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // Star the person before unstar
        Person starredPerson = personToUnstar.rebuildWithStarredStatus(true);
        model.setPerson(personToUnstar, starredPerson);

        UnstarCommand unstarCommand = new UnstarCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnstarCommand.MESSAGE_UNSTARRED_PERSON_SUCCESS,
                Messages.format(personToUnstar));

        Person unstarredPerson = personToUnstar.rebuildWithStarredStatus(false);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUnstar, unstarredPerson);
        expectedModel.sortPersons(STARRED_STATUS_COMPARATOR);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(unstarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // double-check the outOfBoundIndex
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnstarCommand unstarCommand = new UnstarCommand(outOfBoundIndex);

        assertCommandFailure(unstarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnstarCommand unstarFirstCommand = new UnstarCommand(INDEX_FIRST_PERSON);
        UnstarCommand unstarSecondCommand = new UnstarCommand(INDEX_SECOND_PERSON);

        // EP: same unstar command object -> returns true
        assertTrue(unstarFirstCommand.equals(unstarFirstCommand));

        // EP: same unstar command index -> returns true
        UnstarCommand unstarFirstCommandCopy = new UnstarCommand(INDEX_FIRST_PERSON);
        assertTrue(unstarFirstCommand.equals(unstarFirstCommandCopy));

        // EP: different types -> returns false
        assertFalse(unstarFirstCommand.equals(1));

        // EP: null -> returns false
        assertFalse(unstarFirstCommand.equals(null));

        // EP: different unstar command object -> returns false
        assertFalse(unstarFirstCommand.equals(unstarSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnstarCommand unstarCommand = new UnstarCommand(targetIndex);
        String expected = UnstarCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unstarCommand.toString());
    }
}
