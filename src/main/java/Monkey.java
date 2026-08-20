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

                if (command.equals("bye")) {
                    System.out.println("Bye! Keep swinging, and I hope to see you again soon!");
                    System.out.println(separator);
                    break;
                }

                if (command.equals("list")) {
                if (!tasks.isEmpty()) {
                    System.out.println("Here are the tasks in your list:");
                }
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                String taskNumber = command.length() > "mark".length()
                        ? command.substring("mark".length()).trim() : "";
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("That banana-shaped task number does not look right. Use a number after 'mark'.");
                }
            } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                String taskNumber = command.length() > "unmark".length()
                        ? command.substring("unmark".length()).trim() : "";
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(index));
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("This monkey needs a valid task number after 'unmark'.");
                }
            } else if (command.equals("delete") || command.startsWith("delete ")) {
                String taskNumber = command.length() > "delete".length()
                        ? command.substring("delete".length()).trim() : "";
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new MonkeyException("This monkey needs a valid task number after 'delete'.");
                }
            } else if (command.equals("event") || command.startsWith("event ")) {
                String eventDetails = command.length() > "event".length()
                        ? command.substring("event".length()).trim() : "";
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
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                String deadlineDetails = command.length() > "deadline".length()
                        ? command.substring("deadline".length()).trim() : "";
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
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (command.equals("todo") || command.startsWith("todo ")) {
                String description = command.length() > "todo".length()
                        ? command.substring("todo".length()).trim() : "";
                if (description.isEmpty()) {
                    throw new MonkeyException("A todo needs a description. This monkey cannot fetch an invisible banana!");
                }
                tasks.add(new ToDos(description));
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
