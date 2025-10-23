package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetingnote.MeetingNote;
import seedu.address.model.person.Person;

/**
 * Deletes a meeting note attached to a client using both of their displayed indexes.
 */
public class DeleteMeetingNoteCommand extends Command {

    public static final String COMMAND_WORD = "nDelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting note identified by the index number used in the targeted person's "
            + "meeting notes list.\n"
            + "Parameters: CLIENT_INDEX (must be a positive integer), MEETING_NOTE_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_MEETING_NOTE_SUCCESS = "Deleted Client %1$s's Meeting note %2$d: %3$s";

    private final Index clientIndex;
    private final Index meetingNoteIndex;

    /**
     * Creates a DeleteMeetingNoteCommand to delete the specified {@code MeetingNote} from the specified {@code Person}
     */
    public DeleteMeetingNoteCommand(Index clientIndex, Index meetingNoteIndex) {
        requireAllNonNull(clientIndex, meetingNoteIndex);
        this.clientIndex = clientIndex;
        this.meetingNoteIndex = meetingNoteIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < clientIndex.getOneBased()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteFrom = lastShownList.get(clientIndex.getZeroBased());
        ArrayList<MeetingNote> meetingNotes = personToDeleteFrom.getMeetingNotes();

        if (meetingNotes.size() < meetingNoteIndex.getOneBased()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_NOTE_INDEX);
        }

        MeetingNote meetingNoteToDelete = meetingNotes.get(meetingNoteIndex.getZeroBased());
        Person editedPerson = personToDeleteFrom.removeMeetingNote(meetingNoteToDelete);

        model.setPerson(personToDeleteFrom, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_NOTE_SUCCESS,
                personToDeleteFrom.getName(),
                meetingNoteIndex.getOneBased(),
                meetingNoteToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMeetingNoteCommand)) {
            return false;
        }

        DeleteMeetingNoteCommand otherDeleteMeetingNoteCommand = (DeleteMeetingNoteCommand) other;
        return clientIndex.equals(otherDeleteMeetingNoteCommand.clientIndex)
                && meetingNoteIndex.equals(otherDeleteMeetingNoteCommand.meetingNoteIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("meetingNoteIndex", meetingNoteIndex)
                .toString();
    }
}

