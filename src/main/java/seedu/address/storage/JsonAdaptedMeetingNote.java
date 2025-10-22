package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetingnote.MeetingNote;

/**
 * Jackson-friendly version of {@link MeetingNote}.
 */
public class JsonAdaptedMeetingNote {
    private final String note;
    private final String createdAt;

    /**
     * Constructs a {@code JsonAdaptedMeetingNote} with the given {@code note createdAt}
     */
    @JsonCreator
    public JsonAdaptedMeetingNote(@JsonProperty("note") String note,
                                  @JsonProperty("createdAt") String createdAt) {
        this.note = note;
        this.createdAt = createdAt;
    }

    /**
     * Converts a given {@code MeetingNote} into this class for Jackson use.
     */
    public JsonAdaptedMeetingNote(MeetingNote source) {
        note = source.getNote();
        createdAt = source.getCreatedAt().format(DateTimeFormatter.ofPattern(MeetingNote.DATE_PATTERN));
    }

    public String getNote() {
        return note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Converts this Jackson-friendly adapted MeetingNote object into the model's {@code MeetingNote} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted MeetingNote.
     */
    public MeetingNote toModelType() throws IllegalValueException {
        if (!MeetingNote.isValidNote(note)) {
            throw new IllegalValueException(MeetingNote.NOTE_MESSAGE_CONSTRAINTS);
        }
        return new MeetingNote(note, LocalDateTime.parse(createdAt));
    }
}
