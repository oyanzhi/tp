package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
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

public class StarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(StarCommand.MESSAGE_STARRED_PERSON_SUCCESS,
                Messages.format(personToStar));

        Person starredPerson = personToStar.withStarredStatus(true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.setPerson(personToStar, starredPerson);
        expectedModel.sortPersons(StarCommand.STARRED_STATUS_COMPARATOR);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(starCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAlreadyStarred_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);

        Person starredPerson = personToStar.withStarredStatus(true);
        model.setPerson(personToStar, starredPerson);

        assertCommandFailure(starCommand, model, StarCommand.MESSAGE_PERSON_IS_STARRED);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        StarCommand starCommand = new StarCommand(outOfBoundIndex);

        assertCommandFailure(starCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(StarCommand.MESSAGE_STARRED_PERSON_SUCCESS,
                Messages.format(personToStar));

        Person starredPerson = personToStar.withStarredStatus(true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToStar, starredPerson);
        expectedModel.sortPersons(StarCommand.STARRED_STATUS_COMPARATOR);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(starCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // double check validity of outOfBoundIndex
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        StarCommand starCommand = new StarCommand(outOfBoundIndex);

        assertCommandFailure(starCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        StarCommand starFirstCommand = new StarCommand(INDEX_FIRST_PERSON);
        StarCommand starSecondCommand = new StarCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(starFirstCommand.equals(starFirstCommand));

        // same values -> returns true
        StarCommand starFirstCommandCopy = new StarCommand(INDEX_FIRST_PERSON);
        assertTrue(starFirstCommand.equals(starFirstCommandCopy));

        // different types -> returns false
        assertFalse(starFirstCommand.equals(1));

        // null -> returns false
        assertFalse(starFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(starFirstCommand.equals(starSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        StarCommand starCommand = new StarCommand(targetIndex);
        String expected = StarCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, starCommand.toString());
    }
}
