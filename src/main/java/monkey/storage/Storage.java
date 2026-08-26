package monkey.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import monkey.model.Deadline;
import monkey.model.Event;
import monkey.model.Task;
import monkey.model.ToDos;

/** Saves Monkey's task list in a simple, human-readable text format. */
public class Storage {
    private final Path filePath;

    /** Creates storage backed by the given file path. */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /** Reads the saved task list, returning an empty list when no save exists yet. */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(filePath)) {
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
    public void save(List<Task> tasks) {
        if (tasks == null) {
            return;
        }
        Path temporaryPath = filePath.resolveSibling(filePath.getFileName() + ".tmp");
        try {
            Files.createDirectories(filePath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(temporaryPath)) {
                for (Task task : tasks) {
                    if (task != null) {
                        writer.write(formatTask(task));
                        writer.newLine();
                    }
                }
            }
            try {
                Files.move(temporaryPath, filePath, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.move(temporaryPath, filePath, StandardCopyOption.REPLACE_EXISTING);
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

    /** Reconstructs a task from one storage line, or returns null when invalid. */
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
