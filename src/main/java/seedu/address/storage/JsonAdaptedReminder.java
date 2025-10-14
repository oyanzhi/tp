package seedu.address.storage;

import static seedu.address.model.reminder.Reminder.DATE_INPUT_PATTERN;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Reminder;



/**
 * Jackson-friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {

    private final String header;
    private final String deadline;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given {@code Reminder}.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("header") String header, @JsonProperty("deadline") String deadline) {
        this.header = header;
        this.deadline = deadline;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder reminder) {
        this.header = reminder.getHeader();
        this.deadline = reminder.getDeadline().format(DateTimeFormatter.ofPattern(DATE_INPUT_PATTERN));
    }

    public String getHeader() {
        return this.header;
    }

    public String getDeadline() {
        return this.deadline;
    }

    /**
     * Converts this Jackson-friendly adapted Reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Reminder toModelType() throws IllegalValueException {
        if (!Reminder.isValidHeader(this.header)) {
            throw new IllegalValueException(Reminder.HEADER_MESSAGE_CONSTRAINTS);
        }
        if (!Reminder.isValidDeadline(this.deadline)) {
            throw new IllegalValueException(Reminder.DEADLINE_MESSAGE_CONSTRAINTS);
        }
        return new Reminder(this.header, this.deadline);
    }

}
