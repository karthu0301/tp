package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;



/**
 * This class is called to set the priority of the patient.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";
    public static final String SHORT_COMMAND_WORD = "pr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Indicates priority of patient "
            + "by the index number used in the last person listing. "
            + "Existing priority will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer), PRIORITY (must be low, medium or high) "
            + "pr [INDEX] [PRIORITY]\n"
            + "Example: pr 2 HIGH.";

    private final Index index;
    private final Priority priority;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param priority of the person to be updated to
     */
    public PriorityCommand(Index index, Priority priority) {
        requireAllNonNull(index, priority);

        this.index = index;
        this.priority = priority;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid person index.");
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getGender(),
                personToEdit.getHeight(),
                personToEdit.getWeight(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDiet(),
                priority,
                personToEdit.getMeetingDate(),
                personToEdit.getRemark(),
                personToEdit.getAllergies()
        );

        model.setPerson(personToEdit, updatedPerson);
        return new CommandResult(String.format("Updated priority for %s to %s",
                personToEdit.getName(), priority));
    }
}
