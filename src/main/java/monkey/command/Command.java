package monkey.command;


/** Represents a command understood by Monkey. */
public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    EVENT("event"),
    DEADLINE("deadline"),
    TODO("todo"),
    UNKNOWN("");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    /** Returns the command keyword used in user input. */
    public String getKeyword() {
        return keyword;
    }

    /** Converts the first word of user input into a known command. */
    public static Command fromInput(String input) {
        String trimmedInput = input.trim();
        for (Command command : values()) {
            if (command != UNKNOWN && (trimmedInput.equals(command.keyword)
                    || trimmedInput.startsWith(command.keyword + " "))) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
