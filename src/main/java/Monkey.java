import java.util.ArrayList;
import java.util.Scanner;

/** A simple command-line task manager. */
public class Monkey {
    /** Runs the task manager and processes commands until the user says bye. */
    public static void main(String[] args) {
        String separator = "____________________________________________________________";
        String banner = " __  __              _\n"
                + "|  \\/  | ___  _ __  | | _____ _   _\n"
                + "| |\\/| |/ _ \\| '_ \\ | |/ / _ \\ | | |\n"
                + "| |  | | (_) | | | ||   <  __/ |_| |\n"
                + "|_|  |_|\\___/|_| |_||_|\\_\\___|\\__, |\n"
                + "                              |___/\n";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Monkey, your cheeky little assistant.");
        System.out.println("What can I do for you today?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(separator);

            try {
                if (command.trim().isEmpty()) {
                    throw new MonkeyException("This monkey heard nothing! Please swing over a command.");
                }

                Command commandType = Command.fromInput(command);
                if (commandType == Command.BYE) {
                    System.out.println("Bye! Keep swinging, and I hope to see you again soon!");
                    System.out.println(separator);
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
                String taskNumber = command.length() > commandType.getKeyword().length()
                        ? command.substring(commandType.getKeyword().length()).trim() : "";
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        Storage.save(tasks);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("That banana-shaped task number does not look right. Use a number after 'mark'.");
                }
            } else if (commandType == Command.UNMARK) {
                String taskNumber = command.length() > commandType.getKeyword().length()
                        ? command.substring(commandType.getKeyword().length()).trim() : "";
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        Storage.save(tasks);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("This monkey needs a valid task number after 'unmark'.");
                }
            } else if (commandType == Command.DELETE) {
                String taskNumber = command.length() > commandType.getKeyword().length()
                        ? command.substring(commandType.getKeyword().length()).trim() : "";
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        Storage.save(tasks);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("This monkey needs a valid task number after 'delete'.");
                }
            } else if (commandType == Command.EVENT) {
                String eventDetails = command.length() > commandType.getKeyword().length()
                        ? command.substring(commandType.getKeyword().length()).trim() : "";
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
                Storage.save(tasks);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (commandType == Command.DEADLINE) {
                String deadlineDetails = command.length() > commandType.getKeyword().length()
                        ? command.substring(commandType.getKeyword().length()).trim() : "";
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
                Storage.save(tasks);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (commandType == Command.TODO) {
                String description = command.length() > commandType.getKeyword().length()
                        ? command.substring(commandType.getKeyword().length()).trim() : "";
                if (description.isEmpty()) {
                    throw new MonkeyException("A todo needs a description. This monkey cannot fetch an invisible banana!");
                }
                tasks.add(new ToDos(description));
                Storage.save(tasks);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new MonkeyException("This monkey does not recognize that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
                }
            } catch (MonkeyException | IllegalArgumentException e) {
                System.out.println("OOPS! Monkey says: " + e.getMessage());
            }

            System.out.println(separator);
        }
    }
}
