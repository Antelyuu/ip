/** A command object that can perform one application action. */
public abstract class CommandAction {
    /** Executes this command using the application's collaborators. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /** Returns whether running this command should end the application. */
    public boolean isExit() { return false; }
}
