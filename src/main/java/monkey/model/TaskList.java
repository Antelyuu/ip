package monkey.model;

import java.util.ArrayList;
import java.util.List;

/** Owns Monkey's tasks and provides the operations used by the application. */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Returns whether this list contains no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Returns the number of tasks in this list. */
    public int size() {
        return tasks.size();
    }

    /** Returns the task at the given zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Adds a task to this list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at the given zero-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Marks the task at the given zero-based index as done. */
    public void mark(int index) {
        tasks.get(index).markAsDone();
    }

    /** Marks the task at the given zero-based index as not done. */
    public void unmark(int index) {
        tasks.get(index).markAsNotDone();
    }

    /** Removes and returns the task at the given zero-based index. */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /** Returns a snapshot suitable for persistence. */
    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }
}
