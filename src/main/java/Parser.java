/** Interprets raw user input as a command and its argument text. */
public class Parser {
    /** Converts the first word of input into a known command. */
    public Command parseCommand(String input) {
        return Command.fromInput(input);
    }

    /** Returns the text following the command keyword, trimmed. */
    public String parseArguments(String input, Command command) {
        int keywordLength = command.getKeyword().length();
        return input.length() > keywordLength ? input.substring(keywordLength).trim() : "";
    }
}
