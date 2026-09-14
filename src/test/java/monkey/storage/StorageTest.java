package monkey.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import monkey.model.Task;
import monkey.model.ToDos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class StorageTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void load_missingFile_returnsEmptyListWithoutError() {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        List<Task> tasks = storage.load();

        assertEquals(List.of(), tasks);
        assertNull(storage.getLastError());
    }

    @Test
    void load_fileContainingMalformedLine_keepsValidTasksAndReportsLine() throws Exception {
        Path saveFile = temporaryDirectory.resolve("duke.txt");
        Files.writeString(saveFile, "T | 0 | buy milk\nD | nope | broken | 2026-09-20\n");
        Storage storage = new Storage(saveFile.toString());

        List<Task> tasks = storage.load();

        assertEquals(1, tasks.size());
        assertEquals("buy milk", tasks.get(0).getDescription());
        assertEquals("Ignored malformed saved task data on line 2.", storage.getLastError());
    }

    @Test
    void load_fileContainingDuplicateTask_keepsFirstTaskAndReportsLine() throws Exception {
        Path saveFile = temporaryDirectory.resolve("duke.txt");
        Files.writeString(saveFile, "T | 0 | buy milk\nT | 1 | BUY   MILK\n");
        Storage storage = new Storage(saveFile.toString());

        List<Task> tasks = storage.load();

        assertEquals(1, tasks.size());
        assertFalse(tasks.get(0).isDone());
        assertEquals("Ignored duplicate saved task data on line 2.", storage.getLastError());
    }

    @Test
    void save_parentPathIsFile_returnsFalseAndReportsError() throws Exception {
        Path blockingFile = temporaryDirectory.resolve("not-a-directory");
        Files.writeString(blockingFile, "block child creation");
        Storage storage = new Storage(blockingFile.resolve("duke.txt").toString());

        boolean isSaved = storage.save(List.of(new ToDos("buy milk")));

        assertFalse(isSaved);
        assertEquals("Could not save tasks to disk.", storage.getLastError());
    }
}
