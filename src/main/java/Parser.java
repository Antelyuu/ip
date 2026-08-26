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

    /** Returns the text following the command keyword, trimmed. */
    public String parseArguments(String input, Command command) {
        int keywordLength = command.getKeyword().length();
        return input.length() > keywordLength ? input.substring(keywordLength).trim() : "";
    }
}
