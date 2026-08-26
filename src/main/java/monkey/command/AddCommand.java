package monkey.command;

import monkey.model.*;
import monkey.storage.*;
import monkey.ui.*;

/** Command object for adding deadline and event tasks. */
public class AddCommand extends CommandAction {
    private final Command type;
    private final String details;

    public AddCommand(Command type, String details) {
        this.type = type;
        this.details = details;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task;
        if (type == Command.EVENT) {
            int fromMarker = details.indexOf(" /from ");
            int toMarker = details.indexOf(" /to ", fromMarker + 1);
            String description = fromMarker >= 0 ? details.substring(0, fromMarker).trim() : details.trim();
            String from = fromMarker >= 0 && toMarker >= 0
                    ? details.substring(fromMarker + 7, toMarker).trim() : "";
            String to = toMarker >= 0 ? details.substring(toMarker + 5).trim() : "";
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                ui.showMessage("OOPS! Monkey says: An event needs a description, a /from time, and a /to time. Even monkeys need a schedule!");
                return;
            }
            task = new Event(description, from, to);
        } else {
            int byMarker = details.indexOf(" /by ");
            String description = byMarker >= 0 ? details.substring(0, byMarker).trim() : details.trim();
            String by = byMarker >= 0 ? details.substring(byMarker + 5).trim() : "";
            if (description.isEmpty() || by.isEmpty()) {
                ui.showMessage("OOPS! Monkey says: A deadline needs a description and a /by date or time. Don't let that banana go rotten!");
                return;
            }
            task = new Deadline(description, by);
        }
        tasks.add(task);
        storage.save(tasks.asList());
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + task);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
    }
}