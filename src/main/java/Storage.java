import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/** Saves Monkey's task list in a simple, human-readable text format. */
public class Storage {
    private static final Path FILE_PATH = Paths.get("data", "duke.txt");

    private Storage() {
        // Utility class.
    }

    /** Writes the current task list to disk, replacing the previous snapshot. */
    public static void save(List<Task> tasks) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
                for (Task task : tasks) {
                    writer.write(formatTask(task));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("OOPS! Monkey could not save your tasks: " + e.getMessage());
        }
    }

    private static String formatTask(Task task) {
        String type = task instanceof Deadline ? "D" : task instanceof Event ? "E" : "T";
        String details = task.getDescription();
        if (task instanceof Deadline deadline) {
            details += " | " + deadline.getBy();
        } else if (task instanceof Event event) {
            details += " | " + event.getFrom() + " | " + event.getTo();
        }
        return type + " | " + (task.isDone() ? "1" : "0") + " | " + details;
    }
}
