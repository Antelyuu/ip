package monkey.command;

import monkey.model.*;
import monkey.storage.*;
import monkey.ui.*;

/** Command object for adding a todo task. */
public class TodoCommand extends CommandAction {
    private final String description;

    public TodoCommand(String description) { this.description = description; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (description.isEmpty()) {
            ui.showMessage("OOPS! Monkey says: A todo needs a description. This monkey cannot fetch an invisible banana!");
            return;
        }
        tasks.add(new ToDos(description));
        storage.save(tasks.asList());
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + tasks.get(tasks.size() - 1));
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
    }
}