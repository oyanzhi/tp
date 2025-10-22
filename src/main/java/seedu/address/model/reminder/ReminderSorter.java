package seedu.address.model.reminder;

import java.util.Comparator;

public class ReminderSorter implements Comparator<Reminder> {
    @Override
    public int compare(Reminder reminder1, Reminder reminder2) {
        return reminder1.compareDeadline(reminder2) != 0
                ? reminder1.compareDeadline(reminder2)
                : reminder1.compareHeader(reminder2);
    }
}
