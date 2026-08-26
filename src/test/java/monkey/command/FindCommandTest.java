package monkey.command;

import monkey.model.TaskList;
import monkey.model.ToDos;
import monkey.storage.Storage;
import monkey.ui.Ui;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindCommandTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void execute_matchesKeywordCaseInsensitivelyAndKeepsOriginalOrder() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        tasks.add(new ToDos("buy milk"));
        tasks.add(new ToDos("return BOOK"));

        new FindCommand("book").execute(tasks, new Ui(), new Storage(temporaryDirectory.resolve("duke.txt").toString()));

        assertEquals(3, tasks.size());
        assertEquals("read book", tasks.get(0).getDescription());
        assertEquals("buy milk", tasks.get(1).getDescription());
    }
}
