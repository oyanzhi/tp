package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

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
     * @param deadline A valid deadline.
     * @param header A valid header.
     */
    public Reminder(String deadline, String header) {
        requireAllNonNull(deadline, header);
        checkArgument(isValidDeadline(deadline), DEADLINE_MESSAGE_CONSTRAINTS);
        checkArgument(isValidHeader(header), HEADER_MESSAGE_CONSTRAINTS);
        this.deadline = LocalDateTime.parse("decide from parser");
        this.header = header;
    }

    /**
     * Returns true if string is a valid deadline after today
     */
    public static boolean isValidDeadline(String test) {
        LocalDateTime testTime = LocalDateTime.parse(test);

        if (testTime.isBefore(LocalDateTime.now())) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if string is a non-empty header
     */
    public static boolean isValidHeader(String header) {
        return header.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%s, due by %s", this.header, this.deadline);
    }
}
