---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Insurance Agent who manage multiple clients
* Who may need to find contacts of particular clients 
* Who has to keep track of many meetings with clients

**Value proposition**: Centralised platform for keeping track of all work-related information, organising large amounts of information and helping with progress management of clients.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​             |  I want to …​                                       | So that I can…​                                                                      |
|----------|------------------------|--------------------------------------------------------|-----------------------------------------------------------------------------------------|
| `* * *`  | user                   | save the data I input                                  | not have to input them again on start-up                                                |
| `* * *`  | user                   | search clients by name                                 | easily find a specific client’s information                                             |
| `* * *`  | user                   | delete reminders                                       | remove any outdated/non-necessary/wrongly set-up reminders                              |
| `* * *`  | user                   | add a new client's contact                             | keep track of his information                                                           |
| `* * *`  | user                   | delete a client's contact                              | keep my contacts clean                                                                  |
| `* * `   | user                   | search clients by phone number                         | find specific clients through their phone number                                        |
| `* * `   | user                   | search clients by email                                | find specific clients through their email                                               |
| `* *`    | user                   | mark my client as completed                            | easily keep track of which clients are already onboarded and who is yet to be onboarded |
| `* *`    | user                   | receive alerts                                         | maintain regular engagement                                                             |
| `* *`    | user                   | see upcoming policy renewal dates                      | proactively reach out to clients before policy expires                                  |
| `* *`    | user                   | keep track of my client's deadline that is coming soon | better prioritise and manage my time                                                    |
| `* * `   | forgetful user         | be alerted when I try to add a duplicate client        | keep my contacts organised                                                              |
| `* *`    | user                   | edit a client's information                            | update changing information                                                             |
| `* `     | user                   | be able to tag a client with a custom label            | customize the grouping of clients                                                       |
| `* `     | user                   | bookmark “star clients” for quick access               | jump to top clients immediately                                                         |
| `*`      | user                   | assign priority levels to tasks                        | manage time more efficiently                                                            |
| `*`      | user                   | view a client history timeline                         | see a chronological record of interactions                                              |
| `* `     | user                   | see in-app tutorials                                   | easily familiarise with FinHub's features                                               |
| `*`      | user with many clients | save the data I enter                                  | save time re-entering all data each time I open the app                                 |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is `FinHub` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Search client by name**

**MSS**

1.  The user searches for the client by their name.
2.  FinHub shows details of clients with matching names.

    Use case ends.

**Extensions**

* 1a. FinHub detects an error in the command entered.
    * 1a1. FinHub displays an error message and prompts the user to input again.
    * 1a2. The user re-enters the command to search the client by name. 
  
      Steps 1a1-1a2 are repeated until the command and data entered are correct.
  
      Use case resumes at step 2.

* 2a. The list is empty.

  Use case ends.

**Use case: UC02 - Search client by email**

**MSS**

1.  The user searches for the client by their email.
2.  FinHub shows details of clients with matching emails.

    Use case ends.

**Extensions**

* 1a. FinHub detects an error in the command entered.
    * 1a1. FinHub displays an error message and prompts the user to input again.
    * 1a2. The user re-enters the command to search the client by email. 
  
      Steps 1a1-1a2 are repeated until the command and data entered are correct.

      Use case resumes at step 2.

* 2a. The list is empty.

  Use case ends.

**Use case: UC03 - Search client by phone number**

**MSS**

1.  The user searches for the client by their phone number.
2.  FinHub shows details of clients with matching phone number.

    Use case ends.

**Extensions**

* 1a. FinHub detects an error in the command entered.
    * 1a1. FinHub displays an error message and prompts the user to input again.
    * 1a2. The user re-enters the command to search the client by phone number. 
  
      Steps 1a1-1a2 are repeated until the command and data entered are correct.

      Use case resumes at step 2.

* 2a. The list is empty.

  Use case ends.

**Use case: UC04 - Mark client as complete**

**MSS**

1. User <u>search client by their name (UC01)</u>.
2. FinHub displays a list of clients. 
3. User selects the client to be marked as complete. 
4. FinHub successfully marks the client as complete and displays a success message. 

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. FinHub detects an error in the command entered.
    * 3a1. FinHub displays an error message and prompts the user to input again.
    * 3a2. The user re-enters the command to <u>search the client by name (UC01) </u>. 
  
      Steps 3a1-3a2 are repeated until the command and data entered are correct. 

      Use case resumes at step 4.

**Use case: UC05 - Add a client's contact**

**MSS**

1.  The user selects the option to add clients.
2.  The user enters the client's details (name, telephone number, email address).
3.  FinHub validates the input.
4.  FinHub adds the new client into the address book.
5.  FinHub displays a confirmation message.

    Use case ends.

**Extensions**

* 3a. The user enters invalid/missing inputs.
    * 3a1. FinHub prompts the user to enter the correct details.

      Use case resumes at step 2.

* 3b. The user enters an email/telephone number that has been added before.
    * 3b1. FinHub warns that a duplicate entry is not allowed.

      Use case ends.


**Use case: UC06 - Delete a client's contact**

**Precondition**: Client list must not be empty.

**MSS**

1.  The user <u>searches for the client to delete by their name (UC01)</u>.
2.  The user selects the client to be deleted.
3.  FinHub asks for confirmation of the deletion.
4.  FinHub removes the client from the address book.
5.  FinHub displays a success message. 

    Use case ends.

**Extensions**

* 2a. The user selects an invalid client.
    * 2a1. FinHub warns that an invalid client has been Selected, and prompts the user to select again.

      Step 2a1 is repeated until a valid client has been selected.

      Use case resumes at step 3.


* 3a. The user cancels the deletion.  
  Use case ends.


**Use case: UC07 - Edit a client's information** 

**Precondition**: Client list must not be empty.

**MSS**

1.  The user <u>searches for the client to edit by their name (UC01)</u>.
2.  The user selects the client to be edited, and enters one or more updated fields.
3.  FinHub validates the updated data.
4.  FinHub displays a success message. 

    Use case ends.

**Extensions**

* 2a. The user selects an invalid client.
    * 2a1. FinHub warns that an invalid client has been selected, and prompts the user to select again.

      Step 2a1 is repeated until a correct client has been selected.

      Use case resumes at step 3


* 2b. The user enters invalid fields.
    * 2b1. FinHub prompts the user to enter the correct details. 
  
      Use case resumes at step 2.

**Use case: UC08 - Delete reminder**

**MSS**

1.  The user <u>searches for the client to edit by their name (UC01)</u>. 
2.  The user selects which user and which reminder to delete.
3.  FinHub displays a success message. 
4.  FinHub shows the client the list without the deleted reminder.

    Use case ends.

**Extensions**

* 2a. The user selects an invalid client or reminder.
  * 2a1. FinHub warns and specifies which is the invalid selection, and prompts the user to select again. 
  
    Step 2a1 is repeated until a correct selection.
    
    Use case resumes at step 3.

**Use case: UC09 - Receive alerts if client has not been contacted in a specified period**

**MSS**

1.  The user starts up the application.
2.  FinHub displays a message containing all the clients that has not been contacted in a specific period of time.
3.  FinHub returns to landing display.

    Use case ends.

**Extensions**

* 2a. There is no clients that are under this criteria.
    * 2a1. FinHub displays an empty message.

      Use case resumes at step 3.

**Use case: UC10 - List of all upcoming policy renewal dates**

**MSS**

1.  The user starts up the application.
2.  FinHub displays a message showing the few upcoming policy renewal dates in chronological order within a set interval.
3.  FinHub returns to landing display.

    Use case ends.

**Extensions**

* 2a. There are no policy renewal dates in the data.
    * 2a1. FinHub displays an empty message.

      Use case resumes at step 3.

**Use case: UC11 - Assign priority levels to tasks**

**MSS**

1.  FinHub will display a list of tasks saved.
2.  The user will select the task that they want to assign a priority level to and the priority level.
3.  FinHub will update the task with the corresponding level.
4.  FinHub returns to landing display.

    Use case ends.

**Extensions**

* 2a. The user selects either invalid task or invalid priority level.
    * 2a1. FinHub warns and specifies which is the invalid selection, and prompts the user to select correct task/ priority level. 
  
      Step 2a1 is repeated until a correct task and priority level has been selected.

      Use case resumes at step 3.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
