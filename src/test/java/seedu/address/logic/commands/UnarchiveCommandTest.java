package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnarchiveCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // ensure tests see all persons, including archived ones
        model.updateFilteredPersonList(person -> true);
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person archivedPerson = personToArchive.archive();
        model.setPerson(personToArchive, archivedPerson);

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        Person unarchivedPerson = archivedPerson.unarchive();

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(unarchivedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(archivedPerson, unarchivedPerson);
        expectedModel.updateFilteredPersonList(person -> true);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotArchived_throwsCommandException() {
        Person activePerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        assertThrows(CommandException.class, () -> unarchiveCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundsIndex);

        assertThrows(CommandException.class, () -> unarchiveCommand.execute(model));
    }

    @Test
    public void equals() {
        UnarchiveCommand firstCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        UnarchiveCommand secondCommand = new UnarchiveCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        UnarchiveCommand firstCommandCopy = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertEquals(firstCommand, firstCommandCopy);

        // different values -> returns false
        assertEquals(false, firstCommand.equals(secondCommand));

        // different type -> returns false
        assertEquals(false, firstCommand.equals(1));

        // null -> returns false
        assertEquals(false, firstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand command = new UnarchiveCommand(targetIndex);

        String expected = UnarchiveCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";

        String commandString = command.toString();

        assertTrue(commandString.contains("targetIndex=" + targetIndex),
                "toString() should contain the targetIndex");
        assertTrue(commandString.contains("UnarchiveCommand"),
                "toString() should contain class name");
    }
}
