/** Interprets raw user input as a command and its argument text. */
public class Parser {
    /** Converts the first word of input into a known command. */
    public Command parseCommand(String input) {
        return Command.fromInput(input);
    }

    /** Parses both the command keyword and its argument text. */
    public ParsedCommand parse(String input) {
        Command command = parseCommand(input);
        return new ParsedCommand(command, parseArguments(input, command));
    }

    /** Creates an executable command object for commands already supported by the stretch design. */
    public CommandAction parseAction(String input) {
        Command command = parseCommand(input);
        if (command == Command.BYE) {
            return new ExitCommand();
        }
        if (command == Command.DELETE) {
            return new DeleteCommand(parseArguments(input, command));
        }
        if (command == Command.TODO) {
            return new TodoCommand(parseArguments(input, command));
        }
        if (command == Command.LIST) {
            return new ListCommand();
        }
        if (command == Command.MARK || command == Command.UNMARK) {
            return new MarkCommand(parseArguments(input, command), command == Command.MARK);
        }
        if (command == Command.EVENT || command == Command.DEADLINE) {
            return new AddCommand(command, parseArguments(input, command));
        }
        return null;
    }

    /** Returns the text following the command keyword, trimmed. */
    public String parseArguments(String input, Command command) {
        int keywordLength = command.getKeyword().length();
        return input.length() > keywordLength ? input.substring(keywordLength).trim() : "";
    }
}
