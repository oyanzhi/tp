package seedu.address.ui;

/**
 * Pure-Java helpers for reminder text formatting used by the UI layer.
 */
public final class ReminderTextUtil {

    private ReminderTextUtil() { }

    /**
     * Formats a reminder entry for display, e.g. {@code "3. Call client"}.
     *
     * @param index 1-based index to display; must be {@code >= 1}
     * @param text  reminder text; {@code null} is treated as an empty string
     * @return formatted display string
     * @throws IllegalArgumentException if {@code index < 1}
     */
    public static String formatItem(int index, String text) {
        if (index < 1) {
            throw new IllegalArgumentException("index must be >= 1");
        }
        String t;
        if (text == null) {
            t = "";
        } else {
            t = text.trim();
        }
        return index + ". " + t;
    }
}
