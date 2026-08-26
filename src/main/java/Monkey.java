
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
                if (!tasks.isEmpty()) {
                    System.out.println("Here are the tasks in your list:");
                }
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                } else if (commandType == Command.MARK) {
                String taskNumber = arguments;
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.mark(index);
                        storage.save(tasks.asList());
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("That banana-shaped task number does not look right. Use a number after 'mark'.");
                }
            } else if (commandType == Command.UNMARK) {
                String taskNumber = arguments;
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.unmark(index);
                        storage.save(tasks.asList());
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("This monkey needs a valid task number after 'unmark'.");
                }
            } else if (commandType == Command.DELETE) {
                parser.parseAction(command).execute(tasks, ui, storage);
            } else if (commandType == Command.EVENT) {
                String eventDetails = arguments;
                int fromMarker = eventDetails.indexOf(" /from ");
                int toMarker = eventDetails.indexOf(" /to ", fromMarker + 1);
                String description = fromMarker >= 0
                        ? eventDetails.substring(0, fromMarker).trim()
                        : eventDetails.trim();
                String from = fromMarker >= 0 && toMarker >= 0
                        ? eventDetails.substring(fromMarker + " /from ".length(), toMarker).trim()
                        : "";
                String to = toMarker >= 0
                        ? eventDetails.substring(toMarker + " /to ".length()).trim()
                        : "";
                if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    throw new MonkeyException("An event needs a description, a /from time, and a /to time. Even monkeys need a schedule!");
                }
                tasks.add(new Event(description, from, to));
                storage.save(tasks.asList());
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (commandType == Command.DEADLINE) {
                String deadlineDetails = arguments;
                int byMarker = deadlineDetails.indexOf(" /by ");
                String description = byMarker >= 0
                        ? deadlineDetails.substring(0, byMarker).trim()
                        : deadlineDetails.trim();
                String by = byMarker >= 0
                        ? deadlineDetails.substring(byMarker + " /by ".length()).trim()
                        : "";
                if (description.isEmpty() || by.isEmpty()) {
                    throw new MonkeyException("A deadline needs a description and a /by date or time. Don't let that banana go rotten!");
                }
                tasks.add(new Deadline(description, by));
                storage.save(tasks.asList());
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (commandType == Command.TODO) {
                String description = arguments;
                if (description.isEmpty()) {
                    throw new MonkeyException("A todo needs a description. This monkey cannot fetch an invisible banana!");
                }
                tasks.add(new ToDos(description));
                storage.save(tasks.asList());
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new MonkeyException("This monkey does not recognize that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
                }
            } catch (MonkeyException | IllegalArgumentException e) {
                System.out.println("OOPS! Monkey says: " + e.getMessage());
            }

            ui.showSeparator();
        }
    }
}
