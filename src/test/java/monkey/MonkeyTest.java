package monkey;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
class MonkeyTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void getResponse_todoCommand_returnsCommandOutput() {
        Monkey monkey = new Monkey(temporaryDirectory.resolve("duke.txt").toString());

        String response = monkey.getResponse("todo learn JavaFX");

        assertEquals("Got it. I've added this task:\n"
                + "  [T][ ] learn JavaFX\n"
                + "Now you have 1 tasks in the list.", response);
    }
}
