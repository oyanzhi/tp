package seedu.address.model.reminder;

import javafx.util.Pair;
import java.util.Comparator;

import seedu.address.model.person.Person;

public class GeneralReminderSorter implements Comparator<Pair<Person, Reminder>> {
    @Override
    public int compare(Pair<Person, Reminder> o1, Pair<Person, Reminder> o2) {
        ReminderSorter reminderSorter = new ReminderSorter();
        return reminderSorter.compare(o1.getValue(), o2.getValue()) != 0
                ? reminderSorter.compare(o1.getValue(), o2.getValue())
                : o1.getKey().compareTo(o2.getKey());
    }
}
