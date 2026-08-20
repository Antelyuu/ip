/** Represents a task that must be completed by a stated date or time. */
public class Deadline extends Task {
    private final String by;

    /** Creates an incomplete deadline with its description and due date/time. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
