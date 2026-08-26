import java.util.ArrayList;
import java.util.List;

/** Owns Monkey's tasks and provides the operations used by the application. */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() { this.tasks = new ArrayList<>(); }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) { this.tasks = new ArrayList<>(tasks); }

    public boolean isEmpty() { return tasks.isEmpty(); }
    public int size() { return tasks.size(); }
    public Task get(int index) { return tasks.get(index); }
    public void add(Task task) { tasks.add(task); }
    public Task remove(int index) { return tasks.remove(index); }

    /** Marks the task at the given zero-based index as done. */
    public void mark(int index) { tasks.get(index).markAsDone(); }

    /** Marks the task at the given zero-based index as not done. */
    public void unmark(int index) { tasks.get(index).markAsNotDone(); }

    /** Returns a snapshot suitable for persistence. */
    public List<Task> asList() { return new ArrayList<>(tasks); }
}
