/** Represents a task in Monkey's task list. */
public class Task {
    protected String description;
    protected boolean isDone;

    /** Creates a task that is initially not done. */
    public Task(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("This monkey needs a task description before it can add one.");
        }
        this.description = description;
        this.isDone = false;
    }

    /** Returns the task's status icon for display. */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /** Returns the task description for persistence. */
    public String getDescription() {
        return description;
    }

    /** Returns whether this task has been completed. */
    public boolean isDone() {
        return isDone;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** Returns this task in the format used by the task list. */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
