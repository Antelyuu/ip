package monkey.ui;

import java.util.Scanner;

/** Handles console input and common user-facing messages for Monkey. */
public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final String BANNER = " __  __              _\n"
            + "|  \\/  | ___  _ __  | | _____ _   _\n"
            + "| |\\/| |/ _ \\| '_ \\ | |/ / _ \\ | | |\n"
            + "| |  | | (_) | | | ||   <  __/ |_| |\n"
            + "|_|  |_|\\___/|_| |_||_|\\_\\___|\\__, |\n"
            + "                              |___/\n";
    private final Scanner scanner = new Scanner(System.in);

    /** Shows Monkey's greeting. */
    public void showWelcome() {
        showSeparator();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Monkey, your cheeky little assistant.");
        System.out.println("What can I do for you today?");
        showSeparator();
    }

    /** Returns the next command, or null when standard input is exhausted. */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    /** Shows the boundary between command interactions. */
    public void showSeparator() { System.out.println(SEPARATOR); }

    /** Shows a message to the user. */
    public void showMessage(String message) { System.out.println(message); }
}