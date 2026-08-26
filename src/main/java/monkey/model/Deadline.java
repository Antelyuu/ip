package monkey.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/** Represents a task that must be completed by a typed date or date/time. */
public class Deadline extends Task {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a", Locale.ENGLISH);
    private final LocalDate date;
    private final LocalDateTime dateTime;

    /** Creates an incomplete deadline with its description and due date/time. */
    public Deadline(String description, String by) {
        super(description);
        String value = by == null ? "" : by.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("A deadline needs a valid date or time.");
        }
        LocalDate parsedDate = null;
        LocalDateTime parsedDateTime = null;
        try {
            parsedDate = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException dateError) {
            try {
                parsedDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("d/M/uuuu"));
            } catch (DateTimeParseException slashDateError) {
                parsedDateTime = parseDateTime(value);
            }
        }
        date = parsedDate;
        dateTime = parsedDateTime;
    }

    private static LocalDateTime parseDateTime(String value) {
        DateTimeFormatter[] formats = {
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm"),
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
        };
        for (DateTimeFormatter format : formats) {
            try {
                return LocalDateTime.parse(value, format);
            } catch (DateTimeParseException ignored) {
                // Try the next supported input format.
            }
        }
        throw new IllegalArgumentException("Use a date like yyyy-mm-dd or d/M/yyyy, or a date/time like d/M/yyyy HHmm.");
    }

    /** Returns the human-readable deadline value for display. */
    public String getBy() {
        return date != null ? date.format(DATE_FORMAT) : dateTime.format(DATE_TIME_FORMAT);
    }

    /** Returns the canonical value used by storage. */
    public String getStorageValue() {
        return date != null ? date.toString() : dateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + ")";
    }
}