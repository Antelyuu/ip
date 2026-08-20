/** Represents a task that starts and ends at stated date/time values. */
public class Event extends Task {
    private final String from;
    private final String to;

    /** Creates an incomplete event with its start and end date/time values. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
