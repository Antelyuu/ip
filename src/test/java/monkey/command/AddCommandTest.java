package monkey.command;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import monkey.model.TaskList;
import monkey.storage.Storage;
import monkey.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void execute_deadlineWithRepeatedByMarker_rejectsInput() {
        TaskList tasks = new TaskList();
        StringBuilder output = new StringBuilder();

        new AddCommand(Command.DEADLINE, "submit /by 2026-09-20 /by 2026-09-21")
                .execute(tasks, new Ui(output::append), createStorage());

        assertTrue(tasks.isEmpty());
        assertEquals("OOPS! Monkey says: Use exactly one /by marker for a deadline.", output.toString());
    }

    @Test
    void execute_eventWithReversedMarkers_rejectsInput() {
        TaskList tasks = new TaskList();
        StringBuilder output = new StringBuilder();

        new AddCommand(Command.EVENT, "meeting /to 2026-09-20 1600 /from 2026-09-20 1400")
                .execute(tasks, new Ui(output::append), createStorage());

        assertTrue(tasks.isEmpty());
        assertEquals("OOPS! Monkey says: Use one /from marker followed by one /to marker for an event.",
                output.toString());
    }

    @Test
    void execute_eventEndingAtStart_rejectsInput() {
        TaskList tasks = new TaskList();
        StringBuilder output = new StringBuilder();

        new AddCommand(Command.EVENT, "meeting /from 2026-09-20 1400 /to 2026-09-20 1400")
                .execute(tasks, new Ui(output::append), createStorage());

        assertTrue(tasks.isEmpty());
        assertEquals("An event must end after it starts.", output.toString());
    }

    @Test
    void execute_eventWithImpossibleDate_rejectsInput() {
        TaskList tasks = new TaskList();
        StringBuilder output = new StringBuilder();

        new AddCommand(Command.EVENT, "meeting /from 2026-02-30 1400 /to 2026-03-01 1400")
                .execute(tasks, new Ui(output::append), createStorage());

        assertTrue(tasks.isEmpty());
        assertEquals("Use event times like yyyy-mm-dd HHmm, d/M/yyyy HHmm, or h:mm am/pm.", output.toString());
    }

    @Test
    void execute_eventWithImpossibleDateOnly_rejectsInput() {
        TaskList tasks = new TaskList();
        StringBuilder output = new StringBuilder();

        new AddCommand(Command.EVENT, "conference /from 2026-02-30 /to 2026-03-01")
                .execute(tasks, new Ui(output::append), createStorage());

        assertTrue(tasks.isEmpty());
        assertEquals("Use event times like yyyy-mm-dd HHmm, d/M/yyyy HHmm, or h:mm am/pm.", output.toString());
    }

    @Test
    void execute_eventWithEndDateBeforeStartDate_rejectsInput() {
        TaskList tasks = new TaskList();
        StringBuilder output = new StringBuilder();

        new AddCommand(Command.EVENT, "conference /from 2026-09-21 /to 2026-09-20")
                .execute(tasks, new Ui(output::append), createStorage());

        assertTrue(tasks.isEmpty());
        assertEquals("An event must end after it starts.", output.toString());
    }

    private Storage createStorage() {
        return new Storage(temporaryDirectory.resolve("duke.txt").toString());
    }
}
