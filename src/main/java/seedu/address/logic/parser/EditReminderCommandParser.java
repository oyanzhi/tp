package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;

/**
 * Parses input arguments and creates a new EditReminderParser object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HEADER, PREFIX_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_HEADER, PREFIX_DEADLINE)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
        }

        Index clientIndex;
        Index reminderIndex;

        try {
            Index[] indices = ParserUtil.parseDualIndex(argMultimap.getPreamble());
            clientIndex = indices[0];
            reminderIndex = indices[1];
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE), e);
        }

        String header = ParserUtil.parseHeader(argMultimap.getValue(PREFIX_HEADER).get());
        String deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());

        Reminder reminder = new Reminder(header, deadline);

        return new EditReminderCommand(clientIndex, reminderIndex, reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
