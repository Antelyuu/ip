package monkey.parser;

import monkey.command.AddCommand;
import monkey.command.Command;
import monkey.command.CommandAction;
import monkey.command.DeleteCommand;
import monkey.command.ExitCommand;
import monkey.command.FindCommand;
import monkey.command.ListCommand;
import monkey.command.MarkCommand;
import monkey.command.SnoozeCommand;
import monkey.command.TodoCommand;
import monkey.command.UnknownCommand;

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
        return switch (command) {
        case BYE -> new ExitCommand();
        case DELETE -> new DeleteCommand(parseArguments(input, command));
        case TODO -> new TodoCommand(parseArguments(input, command));
        case LIST -> new ListCommand();
        case FIND -> new FindCommand(parseArguments(input, command));
        case MARK, UNMARK -> new MarkCommand(parseArguments(input, command), command == Command.MARK);
        case EVENT, DEADLINE -> new AddCommand(command, parseArguments(input, command));
        case SNOOZE -> new SnoozeCommand(parseArguments(input, command));
        default -> new UnknownCommand();
        };
    }

    /** Returns the text following the command keyword, trimmed. */
    public String parseArguments(String input, Command command) {
        int keywordLength = command.getKeyword().length();
        return input.length() > keywordLength ? input.substring(keywordLength).trim() : "";
    }
}
