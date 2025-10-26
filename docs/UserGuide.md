---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# FinHub User Guide

FinHub is a **desktop app for insurance agents to manage clients. It helps them to better organise all information related to each client, optimized for use via a Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI). If you can type fast, FinHub can get your client management
tasks done faster than traditional GUI apps.

<box type="tip" seamless>

**Tip:** In addition to managing standard contact fields, FinHub also allows you to add reminders and meeting notes to 
each client. You also have the option to star or archive clients for better client organisation.
</box>

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version
   prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for FinHub.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar FinHub.jar`
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe`
      to FinHub.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

    * `reminder 2 h/Meeting on Friday d/2026-04-24 16:00` : Adds a reminder with header `Meeting on Friday` to client
      index 2.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by you.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

<br>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

<br>

### Adding a client: `add`

Adds a client to FinHub.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ [ip/INSURANCE_POLICY]`

<box type="tip" seamless>

**Tip:** A client can have any number of tags (including 0)
</box>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal ip/AIB LifePlan`

<br>

### Listing all clients : `list`

Shows a list of all clients in FinHub.

Format: `list`

<br>

### Editing a client : `edit`

Edits an existing client in FinHub.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [ip/INSURANCE_POLICY]`

* Edits the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client’s tags by typing `t/` without
  specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567`
  and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd client to be `Betsy Crower` and clears all existing tags.

<br>

### Locating clients by name: `find`

Finds clients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

<br>

### Deleting a client : `delete`

Deletes a specified client from FinHub.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd client in FinHub.
* `find Betsy` followed by `delete 1` deletes the 1st client in the results of the `find` command.

<br>

### Adding a reminder : `reminder`

Adds a reminder to the specified client in FinHub.

Format: `reminder CLIENT_INDEX h/HEADER d/DEADLINE`

* Adds a reminder to the client at the specified `CLIENT_INDEX`.
* A reminder consists of a `HEADER` which describes the task or event, and a `DEADLINE` which indicates when it's due.
* `DEADLINE` should be in the format: `yyyy-MM-dd HH:mm`
* `CLIENT_INDEX` refers to the index number of the client shown in the displayed client list.
* `CLIENT_INDEX` **must be a positive integer** 1, 2, 3, …​
* Both the header and deadline must be provided.
* Only one reminder can be added at a time.
* More than one reminder can be added to each client.

Examples:

* `reminder 1 h/Meeting on Friday d/2026-04-24 16:00`
* `reminder 2 h/Meeting on Saturday d/2026-06-24 18:00`

<br>

### Deleting a reminder : `rDelete`

Deletes a reminder from a specified client in FinHub.

Format: `rDelete CLIENT_INDEX REMINDER_INDEX`

* Deletes the reminder at the specified `REMINDER_INDEX` of the specified client at `CLIENT_INDEX`.
* The indices refer to the index number shown in the displayed client list.
* The indices **must be positive integers** 1, 2, 3, …​
* Both the client index and reminder index must be provided.
* Only one reminder can be deleted at a time.

Examples:

* `list` followed by `rDelete 2 1` deletes the 1st reminder of the 2nd client in FinHub.
* `find Betsy` followed by `rDelete 1 1` deletes the 1st reminder of the 1st client in the results of the `find`
  command.

<br>

### Editing a reminder : `rEdit`

Edits a specified reminder from a specified client in FinHub.

* Edits the reminder at the specified `REMINDER_INDEX` of the specified client at `CLIENT_INDEX`.
* The indices refer to the index number shown in the displayed client list.
* The indices **must be positive integers** 1, 2, 3, …​
* Both the client index and reminder index must be provided.
* Only one reminder will be replaced by the new reminder at a time.

Examples:
* `list` followed by `rEdit 2 1 h/Meeting on Friday d/2026-04-24 16:00` edits the 1st reminder of the 2nd client in 
FinHub to the given reminder 
* `find Betsy` followed by `rEdit 1 1 h/Meeting on Friday d/2026-04-24 16:00` edits the 1st reminder of the 1st client
in the results of the `find` command to the given reminder

<br>

### Archiving a client : `archive`

Archives the specified client in FinHub.

Format: `archive INDEX`

* Archives the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The indices **must be positive integers** 1, 2, 3, …​
* Only one client can be archived at a time.

Examples:

* `list` followed by `archive 1` archives the 1st client in the list
* `list` followed by `archive 2` archives the 2nd client in the list

<br>

### Unarchiving a client : `unarchive`

Unarchives the specified client in FinHub.

Format: `unarchive INDEX`

* Unarchives the client at the specified `INDEX`
* The index refers to the index number shown in the archived client list.
* The indices **must be positive integers** 1, 2, 3, …​
* Only one client can be unarchived at a time.

Examples:

* `archivelist` followed by `unarchive 1` unarchives the 1st client in the list
* `archivelist` followed by `unarchive 2` unarchives the 2nd client in the list

<br>

### Adding a meeting note : `note`

Adds a meeting note to the specified client in FinHub.

Format: `note CLIENT_INDEX NOTE`

* Adds a meeting note to the client at the specified `CLIENT_INDEX`
* When adding a meeting note, FinHub automatically records the date and time the note was added. 
* `CLIENT_INDEX` refers to the index number of the client shown in the displayed client list.
* `CLIENT_INDEX` **must be a positive integer** 1, 2, 3, …​
* The note cannot be empty. 
* Only one meeting note can be added at a time.
* More than one meeting note can be added to each client.

Examples:

* `note 1 Client is interested in policy abc`
* `list` followed by `note 2 Client wants to renew policy` adds the meeting note "Client wants to renew policy" to the 
2nd client in FinHub. 
* `find Betsy` followed by `note 1 Client wants to know about policy 2` adds the meeting note "Client wants to 
know more about policy 2" to the 1st client in the result of the `find` command
<br>

### Deleting a meeting note : `nDelete`

Deletes a meeting note from a specified client in FinHub.

Format: `nDelete CLIENT_INDEX MEETING_NOTE_INDEX`

* Deletes the meeting note at the given `MEETING_NOTE_INDEX` for the client listed at `CLIENT_INDEX`.
* `CLIENT_INDEX` refers to the index number of the client shown in the displayed client list.
* `MEETING_NOTE_INDEX` refers to the index number of the meeting note shown in the specified client's 
displayed meeting note list.
* Both `CLIENT_INDEX` and `MEETING_NOTE_INDEX` **must be positive integers** 1, 2, 3, …​
* Both `CLIENT_INDEX` and `MEETING_NOTE_INDEX` must be provided.
* Only one meeting note can be deleted at a time.

Examples:

* `list` followed by `nDelete 2 1` deletes the 1st reminder of the 2nd client in FinHub.
* `find Betsy` followed by `nDelete 1 1` deletes the 1st meeting note of the 1st client in the results of the `find`
  command.

<br>

### Starring a client : `star`

Stars the specified client in FinHub.

Format: `star CLIENT_INDEX`

* Stars the client at the specified `CLIENT_INDEX`.
* The index refers to the index number shown in the displayed client list.
* The indices **must be positive integers** 1, 2, 3, …​
* Only one client can be archived at a time.

Examples:

* `list` followed by `star 1` archives the 1st client in the list
* `list` followed by `star 2` archives the 2nd client in the list

<br>

### Removing Star of a client : `unstar`

Remove the starred status of a specified client in FinHub.

Format: `unstar CLIENT_INDEX`

* Removes the starred status of the client at the specified `CLIENT_INDEX`.
* The index refers to the index number shown in the displayed client list.
* The indices **must be positive integers** 1, 2, 3, …​
* Only one client can be unarchived at a time.

Examples:

* `list` followed by `unstar 1` unarchives the 1st client in the list
* `list` followed by `unstar 2` unarchives the 2nd client in the list

<br>

### Clearing all entries : `clear`

Clears all entries in FinHub.

Format: `clear`

<br>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

<br>

### Saving the data

FinHub data are saved in the hard disk automatically after any command that changes the data. There is no need to
save manually.

<br>

### Editing the data file

FinHub data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are
welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, FinHub will discard all data and start with an empty
data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause FinHub to behave in unexpected ways (e.g., if a value entered is outside
the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

<br>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous FinHub home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut
   `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to
   manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action        | Format, Examples                                                                                                                                                        |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**       | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`|
| **Delete**    | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                     |
| **Edit**      | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                          |  
| **Clear**     | `clear`                                                                                                                                                                 |
| **Find**      | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                              |
| **List**      | `list`                                                                                                                                                                  |
| **Reminder**  | `reminder INDEX h/HEADER d/DEADLINE`<br> e.g., `reminder 1 h/Meeting on Friday d/2026-04-24 16:00`                                                                      |
| **rDelete**   | `rDelete CLIENT_INDEX REMINDER_INDEX`<br> e.g., `rDelete 2 1`                                                                                                           |
| **rEdit**     | `rEdit CLIENT_INDEX REMINDER_INDEX h/HEADER d/DEADLINE`<br> e.g., `rEdit 1 1 h/Meeting on Friday d/2026-04-24 16:00`                                                    |
| **Archive**   | `archive INDEX`<br> e.g., `archive 1`                                                                                                                                   |
| **Unarchive** | `unarchive INDEX`<br> e.g., `unarchive 1`                                                                                                                               |  
| **Note**      | `note INDEX NOTE`<br> e.g., `note 1 Client wants to know about policy abc`                                                                                              |
| **nDelete**   | `nDelete CLIENT_INDEX MEETING_NOTE_INDEX`<br> e.g., `nDelete 1 1`                                                                                                       |
| **Star**      | `star INDEX`<br> e.g., `star 1`                                                                                                                                         |
| **Unstar**    | `unstar INDEX`<br> e.g., `unstar 1`                                                                                                                                     |  
| **Help**      | `help`                                                                                                                                                                  |
