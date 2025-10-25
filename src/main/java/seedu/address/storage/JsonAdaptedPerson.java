package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetingnote.MeetingNote;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InsurancePolicy;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String INVALID_STARRED_MESSAGE = "Starred status should be either 'true' or 'false'.";
    public static final String STARRED_SIMPLE_NAME = "Starred status";
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private final String policy;
    private final List<JsonAdaptedMeetingNote> meetingNotes = new ArrayList<>();
    private final String starred;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("reminders") List<JsonAdaptedReminder> reminders,
            @JsonProperty("insurancePolicy") String policy,
            @JsonProperty("meeting notes")List<JsonAdaptedMeetingNote> meetingNotes,
            @JsonProperty("starred") String starred) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (reminders != null) {
            this.reminders.addAll(reminders);
        }
        this.policy = policy;
        if (meetingNotes != null) {
            this.meetingNotes.addAll(meetingNotes);
        }
        this.starred = starred;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        reminders.addAll(source.getReminders().stream()
                .map(JsonAdaptedReminder::new)
                .collect(Collectors.toList()));
        policy = source.getPolicy().toString();
        meetingNotes.addAll(source.getMeetingNotes().stream()
                .map(JsonAdaptedMeetingNote::new)
                .collect(Collectors.toList()));
        starred = String.valueOf(source.isStarred());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Reminder> personReminders = new ArrayList<>();
        final List<MeetingNote> personMeetingNotes = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        for (JsonAdaptedReminder reminder : reminders) {
            personReminders.add(reminder.toModelType());
        }

        for (JsonAdaptedMeetingNote meetingNote : meetingNotes) {
            personMeetingNotes.add(meetingNote.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final ArrayList<Reminder> modelReminder = new ArrayList<>(personReminders);

        if (policy == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "InsurancePolicy"));
        }
        if (!InsurancePolicy.isValidPolicy(policy)) {
            throw new IllegalValueException(InsurancePolicy.MESSAGE_CONSTRAINTS);
        }
        final InsurancePolicy modelPolicy = new InsurancePolicy(policy);
        final ArrayList<MeetingNote> modelMeetingNotes = new ArrayList<>(personMeetingNotes);
        if (starred == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, STARRED_SIMPLE_NAME));
        }

        if (!starred.equalsIgnoreCase("true") && !starred.equalsIgnoreCase("false")) {
            throw new IllegalValueException(INVALID_STARRED_MESSAGE);
        }
        final boolean modelStarred = Boolean.parseBoolean(starred);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelReminder,
                modelPolicy, modelMeetingNotes, modelStarred);
    }

}
