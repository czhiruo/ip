import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    /**
     * Prints out a list of the tasks the user has
     * @param list The list containing all the tasks that user has input
     */
    public static void checkList(ArrayList<Task> list) {
        if(list.size() == 0) {
            int starEyesEmoji = 0x1F929;
            System.out.println("yay you don't have anything to do" + new String(Character.toChars(starEyesEmoji)));
        } else {
            System.out.println("sian you still have to complete these:");
            for (int i = 0; i < list.size(); i++) {
                Task curr = list.get(i);
                System.out.println((i + 1) + ". " + curr.toString());
            }
        }
    }

    /**
     * Marks a task as done
     * @param list The list containing all the tasks that user has input
     * @param input The task the user wants to mark as done
     */
    public static void markTask(ArrayList<Task> list, String input) {
        int starEyesEmoji = 0x1F929;
        System.out.println("good job, you've completed a task! You're so productive!" + new String(Character.toChars(starEyesEmoji)));
        String parts[] = input.split(" ");
        int taskNo = Integer.parseInt(parts[1]);
        Task checkedTask = list.get(taskNo - 1);
        checkedTask.markAsDone();
        System.out.println(checkedTask.toString());
    }

    /**
     * Unmarks a task
     * @param list The list containing all the tasks that user has input
     * @param input The task the user wants to unmark
     */
    public static void unMarkTask(ArrayList<Task> list, String input) {
        String parts[] = input.split(" ");
        int taskNo = Integer.parseInt(parts[1]);
        Task checkedTask = list.get(taskNo - 1);
        checkedTask.markAsUndone();
        System.out.println("why are you not going to " + checkedTask.description + "? remember to do it later!");
        System.out.println(checkedTask.toString());
    }

    /**
     * Deletes a Task from the list
     * @param list The list containing all the tasks that user has input
     * @param input The task the user wants to delete
     */
    public static void deleteTask(ArrayList<Task> list, String input) {
        String parts[] = input.split(" ");
        int taskNo = Integer.parseInt(parts[1]);
        Task checkedTask = list.get(taskNo - 1);
        list.remove(checkedTask);
        if(checkedTask.getStatusIcon().equals("X")) {
            System.out.println("good job! you're officially done with this:");
            System.out.println(checkedTask.toString());
        } else {
            System.out.println("not you running away from your responsibilities, i guess you don't have to do this now:");
            System.out.println(checkedTask.toString());
        }
        if(list.size() == 0) {
            System.out.println("THERES NOTHING LEFT TO DO!!!!");
        } else {
            System.out.println("but still sucks to be you, you still have " + list.size() + " tasks");
        }
    }

    /**
     * Adds a ToDo to the list
      * @param list The list containing all the tasks that user has input
     * @param input The ToDo the user wants to add
     */
    public static void addsToDo(ArrayList<Task> list, String input) {
        try {
            ToDo item = Task.createToDo(input);
            list.add(item);
            System.out.println("todo added: " + item.toString());
            System.out.println("You have this many stuff to complete: " + list.size());
        } catch (NoDescException e) {
        }
    }

    /**
     * Adds a Deadline to the list
     * @param list The list containing all the tasks that user has input
     * @param input The Deadline the user wants to add
     */
    public static void addDeadline(ArrayList<Task> list, String input) {
        try {
            Deadline item = Task.createDeadline(input);
            list.add(item);
            System.out.println("deadline added: " + item.toString());
            System.out.println("You have this many stuff to complete: " + list.size());
        } catch (NoDescException e) {
        } catch (DeadlineNoEndException e) {
        }
    }

    /**
     * Adds an Event to the list
     * @param list The list containing all the tasks that user has input
     * @param input The Event the user wants to add
     */
    public static void addEvent(ArrayList<Task> list, String input) {
        try {
            Event item = Task.createEvent(input);
            list.add(item);
            System.out.println("event added: " + item.toString());
            System.out.println("You have this many stuff to complete: " + list.size());
        } catch (NoDescException e) {
        }
    }

    public static void main(String[] args) throws IOException, IOException {

        String botName = "\n" +
                "██████╗  █████╗ ██╗   ██╗\n" +
                "██╔══██╗██╔══██╗██║   ██║\n" +
                "██████╔╝███████║██║   ██║\n" +
                "██╔═══╝ ██╔══██║██║   ██║\n" +
                "██║     ██║  ██║╚██████╔╝\n" +
                "╚═╝     ╚═╝  ╚═╝ ╚═════╝ \n" +
                "                         \n";

        String introduction = " HI! I'm " + botName + "\n" + "ENTERTAIN MEEE";
        String exit = "byebye come play with me next time";
        System.out.println(introduction);

        String input;
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            input = scan.nextLine();
            if (input.equals("bye")) {
                System.out.println(exit);
                break;
            } else if (input.equals("list")) {
                Duke.checkList(list);
            } else if (input.startsWith("mark")) {
                Duke.markTask(list, input);
            } else if (input.startsWith("unmark")) {
                Duke.unMarkTask(list, input);
            } else if (input.startsWith("delete")) {
                Duke.deleteTask(list, input);
            } else if (input.startsWith("todo")) {
                Duke.addsToDo(list, input);
            } else if (input.startsWith("deadline")) {
                Duke.addDeadline(list, input);
            } else if (input.startsWith("event")) {
                Duke.addEvent(list, input);
            }
        }
    }
}
