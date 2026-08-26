package monkey.command;

import monkey.model.*;
import monkey.storage.*;
import monkey.ui.*;

/** Command object for deleting a task by its one-based user index. */
public class DeleteCommand extends CommandAction {
    private final String taskNumber;

    /** Creates a command for deleting the task at the given user index. */
    public DeleteCommand(String taskNumber) { this.taskNumber = taskNumber; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                ui.showMessage("That task number does not exist.");
                return;
            }
            Task removedTask = tasks.delete(index);
            storage.save(tasks.asList());
            ui.showMessage("Noted. I've removed this task:");
            ui.showMessage("  " + removedTask);
            ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            ui.showMessage("OOPS! Monkey says: This monkey needs a valid task number after 'delete'.");
        }
    }
}
