package monkey.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {
    @Test
    void markAndUnmark_validIndex_updatesTaskStatus() {
        Task task = new ToDos("revise JUnit");
        TaskList tasks = new TaskList(List.of(task));

        tasks.mark(0);
        assertTrue(task.isDone());

        tasks.unmark(0);
        assertFalse(task.isDone());
    }

    @Test
    void delete_validIndex_removesAndReturnsSelectedTask() {
        Task first = new ToDos("first task");
        Task second = new ToDos("second task");
        TaskList tasks = new TaskList(List.of(first, second));

        Task deleted = tasks.delete(0);

        assertSame(first, deleted);
        assertEquals(1, tasks.size());
        assertSame(second, tasks.get(0));
    }

    @Test
    void asList_returnsSnapshotThatDoesNotChangeTaskList() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("original task"));

        List<Task> snapshot = tasks.asList();
        snapshot.clear();

        assertEquals(1, tasks.size());
        assertEquals("original task", tasks.get(0).getDescription());
    }
}
