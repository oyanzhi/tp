package seedu.address.ui;

/**
 * Pure-Java helpers for meeting note text formatting used by the UI layer.
 */
public final class MeetingNoteTextUtil {

    private MeetingNoteTextUtil() { }

    /**
     * Formats a meeting note entry for display, e.g. {@code "3. Discuss budget"}.
     *
     * @param index 1-based index to display; must be {@code >= 1}
     * @param text  note text; {@code null} is treated as an empty string
     * @return formatted display string
     * @throws IllegalArgumentException if {@code index < 1}
     */
    public static String formatItem(int index, String text) {
        if (index < 1) {
            throw new IllegalArgumentException("index must be >= 1");
        }
        String t = (text == null) ? "" : text.trim();
        return index + ". " + t;
    }
}

