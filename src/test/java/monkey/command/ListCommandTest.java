package monkey.command;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import monkey.model.TaskList;
import monkey.storage.Storage;
import monkey.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void execute_emptyTaskList_reportsThatNoTasksExist() {
        StringBuilder output = new StringBuilder();
        Ui ui = new Ui(output::append);

        new ListCommand().execute(
                new TaskList(), ui, new Storage(temporaryDirectory.resolve("duke.txt").toString()));

        assertEquals("There are no tasks in your list.", output.toString());
    }
}
