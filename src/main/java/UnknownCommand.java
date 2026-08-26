/** Command object for reporting an unsupported user command. */
public class UnknownCommand extends CommandAction {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("OOPS! Monkey says: This monkey does not recognize that command. "
                + "Try todo, deadline, event, list, mark, unmark, delete, or bye.");
    }
}
