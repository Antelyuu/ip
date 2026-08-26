/** Command object for displaying the current task list. */
public class ListCommand extends CommandAction {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (!tasks.isEmpty()) {
            ui.showMessage("Here are the tasks in your list:");
        }
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage((i + 1) + "." + tasks.get(i));
        }
    }
}
