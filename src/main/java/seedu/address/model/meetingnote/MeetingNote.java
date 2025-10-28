package seedu.address.model.meetingnote;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a single meeting note for a person
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class MeetingNote {

    public static final String NOTE_MESSAGE_CONSTRAINTS = "Note cannot be empty, must be less than 200 characters, "
            + "and must contain only printable ASCII characters.";

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    private final String note;
    private final LocalDateTime createdAt;

    /**
     * Constructs a {@code MeetingNote}.
     *
     * @param note A valid note.
     * @param createdAt The time when the note is created.
     */
    public MeetingNote(String note, String createdAt) {
        requireAllNonNull(note, createdAt);
        assert !createdAt.isBlank() : "CreatedAt string cannot be blank";
        checkArgument(isValidNote(note), NOTE_MESSAGE_CONSTRAINTS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        this.note = note;
        this.createdAt = LocalDateTime.parse(createdAt, formatter);
    }

    /**
     * Returns true if string is non-empty, less than 200 characters, and contains only printable ASCII characters
     */
    public static boolean isValidNote(String note) {
        return !note.isBlank()
                && note.length() < 200 // less than 200 characters
                && note.chars().allMatch(c -> c >= 32 && c <= 126); // basic printable ASCII
    }

    public String getNote() {
        return this.note;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }


    @Override
    public int hashCode() {
        return Objects.hash(note, createdAt);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedCreatedAt = this.createdAt.format(formatter);
        return String.format("[%s] %s", formattedCreatedAt, this.note);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingNote)) {
            return false;
        }

        MeetingNote otherMeetingNote = (MeetingNote) other;
        return note.equals(otherMeetingNote.note);
    }
}
