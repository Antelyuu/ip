package monkey.command;

import monkey.model.*;
import monkey.storage.*;
import monkey.ui.*;

/** Command object for reporting an unsupported user command. */
public class UnknownCommand extends CommandAction {
    /** Creates a command that reports an unsupported command. */
    public UnknownCommand() { }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("OOPS! Monkey says: This monkey does not recognize that command. "
                + "Try todo, deadline, event, list, mark, unmark, delete, or bye.");
    }
}
