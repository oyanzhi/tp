package seedu.address.model.reminder;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a single reminder for a person
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline} and {@link #isValidHeader(String)}
 */
public class Reminder {

    public static final String DEADLINE_MESSAGE_CONSTRAINTS = "Deadline should be minimally from today.";
    public static final String HEADER_MESSAGE_CONSTRAINTS = "Reminder can take any value but cannot be blank.";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final LocalDateTime deadline;
    private final String header;

    /**
     * Constructs a {@code Reminder}.
     *
     * @param header A valid header.
     * @param deadline A valid deadline.
     */
    public Reminder(String header, String deadline) {
        requireAllNonNull(header, deadline);
        checkArgument(isValidHeader(header), HEADER_MESSAGE_CONSTRAINTS);
        checkArgument(isValidDeadline(deadline), DEADLINE_MESSAGE_CONSTRAINTS);
        this.header = header;
        this.deadline = LocalDateTime.parse(deadline);
    }

    /**
     * Returns true if string is a non-empty header
     */
    public static boolean isValidHeader(String header) {
        return header.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if string is a valid deadline after today
     */
    public static boolean isValidDeadline(String test) {
        try {
            LocalDateTime testTime = LocalDateTime.parse(test);
            if (testTime.isBefore(LocalDateTime.now())) {
                return false;
            }
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, deadline);
    }

    @Override
    public String toString() {
        return String.format("%s, due by %s", this.header, this.deadline);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles null
        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder r = (Reminder) other;
        return r.deadline.equals(this.deadline) && r.header.equals(this.header);
    }
}
