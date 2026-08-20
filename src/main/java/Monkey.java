import java.util.Scanner;

/** A simple command-line task manager. */
public class Monkey {
    private static final int MAX_TASKS = 100;

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
        String[] tasks = new String[MAX_TASKS];
        boolean[] completed = new boolean[MAX_TASKS];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye! Keep swinging, and I hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    String status = completed[i] ? "X" : " ";
                    System.out.println((i + 1) + ".[" + status + "] " + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                String taskNumber = command.substring("mark ".length()).trim();
                try {
                    int index = Integer.parseInt(taskNumber) - 1;
                    if (index >= 0 && index < taskCount) {
                        completed[index] = true;
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [X] " + tasks[index]);
                    } else {
                        System.out.println("That task number does not exist.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please specify a valid task number.");
                }
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
            } else {
                System.out.println("Sorry, I can only store up to " + MAX_TASKS + " tasks.");
            }

            System.out.println(separator);
        }
    }
}
