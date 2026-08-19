import java.util.Scanner;

public class Monkey {
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
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye! Keep swinging, and I hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            System.out.println(command);
            System.out.println(separator);
        }
    }
}
