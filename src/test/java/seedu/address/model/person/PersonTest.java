package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        String name = editPersonDescriptor.getName().isPresent()
                ? editPersonDescriptor.getName().get().toString()
                : "Default Name";
        String phone = editPersonDescriptor.getPhone().isPresent()
                ? editPersonDescriptor.getPhone().get().toString()
                : "Default Phone";
        String email = editPersonDescriptor.getEmail().isPresent()
                ? editPersonDescriptor.getEmail().get().toString()
                : "Default Email";
        String address = editPersonDescriptor.getAddress().isPresent()
                ? editPersonDescriptor.getAddress().get().toString()
                : "Default Address";
        String tags = editPersonDescriptor.getTags().isPresent()
                ? editPersonDescriptor.getTags().get().toString()
                : "Default Tags";
        String policy = editPersonDescriptor.getPolicy().isPresent()
                ? editPersonDescriptor.getPolicy().get().toString()
                : "Default Policy";

        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + name + ", phone="
                + phone + ", email="
                + email + ", address="
                + address + ", tags="
                + tags + ", policy="
                + policy + "}";

        assertEquals(expected, editPersonDescriptor.toString());
    }


    @Test
    public void testPersonWithPolicy() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("98765432");
        Email email = new Email("johndoe@example.com");
        Address address = new Address("311, Clementi Ave 2, #02-25");
        Set<Tag> tags = Set.of(new Tag("friends"));
        InsurancePolicy policy = new InsurancePolicy("AIB Secure Plan");
        Person person = new Person(name, phone, email, address, tags, new ArrayList<>(), policy);
        assertEquals("AIB Secure Plan", person.getPolicy().toString());
    }

    @Test
    public void testPersonEqualityWithPolicy() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("98765432");
        Email email = new Email("johndoe@example.com");
        Address address = new Address("311, Clementi Ave 2, #02-25");
        Set<Tag> tags = Set.of(new Tag("friends"));
        InsurancePolicy policy = new InsurancePolicy("AIB Secure Plan");

        Person person1 = new Person(name, phone, email, address, tags, new ArrayList<>(), policy);
        Person person2 = new Person(name, phone, email, address, tags, new ArrayList<>(), policy);

        assertTrue(person1.equals(person2));
    }
}
