package monkey;

import monkey.command.*;
import monkey.exception.*;
import monkey.parser.*;
import monkey.storage.*;
import monkey.ui.*;
import monkey.model.*;


/** A simple command-line task manager. */
public class Monkey {
    /** Runs the task manager and processes commands until the user says bye. */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        Storage storage = new Storage("data/duke.txt");
        ui.showWelcome();

        TaskList tasks = new TaskList(storage.load());

        String command;
        while ((command = ui.readCommand()) != null) {
            ui.showSeparator();

            try {
                if (command.trim().isEmpty()) {
                    throw new MonkeyException("This monkey heard nothing! Please swing over a command.");
                }

                ParsedCommand parsedCommand = parser.parse(command);
                Command commandType = parsedCommand.command();
                String arguments = parsedCommand.arguments();
                if (commandType == Command.BYE) {
                    CommandAction exitCommand = parser.parseAction(command);
                    exitCommand.execute(tasks, ui, storage);
                    ui.showSeparator();
                    break;
                }

                if (commandType == Command.LIST) {
                parser.parseAction(command).execute(tasks, ui, storage);
                } else if (commandType == Command.MARK) {
                parser.parseAction(command).execute(tasks, ui, storage);
            } else if (commandType == Command.UNMARK) {
                parser.parseAction(command).execute(tasks, ui, storage);
            } else if (commandType == Command.DELETE) {
                parser.parseAction(command).execute(tasks, ui, storage);
            } else if (commandType == Command.EVENT) {
                parser.parseAction(command).execute(tasks, ui, storage);
            } else if (commandType == Command.DEADLINE) {
                parser.parseAction(command).execute(tasks, ui, storage);
            } else if (commandType == Command.TODO) {
                parser.parseAction(command).execute(tasks, ui, storage);
                } else {
                    parser.parseAction(command).execute(tasks, ui, storage);
                }
            } catch (MonkeyException | IllegalArgumentException e) {
                System.out.println("OOPS! Monkey says: " + e.getMessage());
            }

            ui.showSeparator();
        }
    }
}