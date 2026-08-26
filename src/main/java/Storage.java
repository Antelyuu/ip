import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/** Saves Monkey's task list in a simple, human-readable text format. */
public class Storage {
    private static final Path FILE_PATH = Paths.get("data", "duke.txt");

    private Storage() {
        // Utility class.
    }

    /** Reads the saved task list, returning an empty list when no save exists yet. */
    public static ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(FILE_PATH)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(FILE_PATH)) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            // A missing or unreadable save should not prevent Monkey from starting.
        }
        return tasks;
    }

    /** Writes the current task list to disk, replacing the previous snapshot. */
    public static void save(List<Task> tasks) {
        if (tasks == null) {
            return;
        }
        Path temporaryPath = FILE_PATH.resolveSibling(FILE_PATH.getFileName() + ".tmp");
        try {
            Files.createDirectories(FILE_PATH.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(temporaryPath)) {
                for (Task task : tasks) {
                    if (task != null) {
                        writer.write(formatTask(task));
                        writer.newLine();
                    }
                }
            }
            try {
                Files.move(temporaryPath, FILE_PATH, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.move(temporaryPath, FILE_PATH, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("OOPS! Monkey could not save your tasks: " + e.getMessage());
            try {
                Files.deleteIfExists(temporaryPath);
            } catch (IOException ignored) {
                // Preserve the original save error.
            }
        }
    }

    private static String formatTask(Task task) {
        String type = task instanceof Deadline ? "D" : task instanceof Event ? "E" : "T";
        String details = task.getDescription();
        if (task instanceof Deadline deadline) {
            details += " | " + deadline.getStorageValue();
        } else if (task instanceof Event event) {
            details += " | " + event.getFrom() + " | " + event.getTo();
        }
        return type + " | " + (task.isDone() ? "1" : "0") + " | " + details;
    }

    private static Task parseTask(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] fields = line.split("\\s*\\|\\s*", -1);
        try {
            if (fields.length < 3 || fields[2].trim().isEmpty()
                    || !("0".equals(fields[1].trim()) || "1".equals(fields[1].trim()))) {
                return null;
            }
            Task task;
            switch (fields[0].trim()) {
            case "T":
                if (fields.length != 3) {
                    return null;
                }
                task = new ToDos(fields[2].trim());
                break;
            case "D":
                if (fields.length != 4 || fields[3].trim().isEmpty()) {
                    return null;
                }
                task = new Deadline(fields[2].trim(), fields[3].trim());
                break;
            case "E":
                if (fields.length != 5 || fields[3].trim().isEmpty() || fields[4].trim().isEmpty()) {
                    return null;
                }
                task = new Event(fields[2].trim(), fields[3].trim(), fields[4].trim());
                break;
            default:
                return null;
            }
            if ("1".equals(fields[1].trim())) {
                task.markAsDone();
            }
            return task;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
