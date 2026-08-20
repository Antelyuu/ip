/** Represents a task in Monkey's task list. */
public class Task {
    protected String description;
    protected boolean isDone;

    /** Creates a task that is initially not done. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns the task's status icon for display. */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** Returns the task description. */
    public String getDescription() {
        return description;
    }
}
