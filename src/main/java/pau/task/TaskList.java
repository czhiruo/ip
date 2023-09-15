package pau.task;

import java.util.ArrayList;
import java.util.Scanner;

import pau.exception.DeadlineNoEndException;
import pau.exception.NoDescException;
import pau.exception.NoSuchTaskException;

/**
 * Stores the tasks in an ArrayList;
 */
public class TaskList {

    /**
     * The list where the tasks are stored.
     */
    private ArrayList<Task> taskList;

    /**
     * Constructs a new ArrayList for the tasks.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Retrieves size of the task list.
     *
     * @return Size of the task list.
     */
    public int listSize() {
        return this.taskList.size();
    }

    /**
     * Retrieves the task at a certain index in the task list.
     *
     * @param index The index of the task to be retrieved.
     * @return The task at the index.
     */
    public Task getTask(int index) {
        return taskList.get(index);
    }

    /**
     * Prints out a list of the tasks the user has.
     */
    public String checkList() {
        if (this.listSize() == 0) {
            int starEyesEmoji = 0x1F929;
            return "yay you don't have anything to do" + new String(Character.toChars(starEyesEmoji));
        } else {
            String output = "sian you still have to complete these:\n";
            for (int i = 0; i < this.listSize(); i++) {
                Task curr = this.taskList.get(i);
                output += (i + 1) + ". " + curr.toString() + "\n";
            }
            return output;
        }
    }

    /**
     * Marks a task as done.
     *
     * @param input The task the user wants to mark as done.
     */
    public String markTask(String input) {
        int starEyesEmoji = 0x1F929;
        String output = "good job, you've completed a task! You're so productive!"
                + new String(Character.toChars(starEyesEmoji)) + "\n";
        String parts[] = input.split(" ");

        int taskNo = Integer.parseInt(parts[1]);
        Task checkedTask = this.taskList.get(taskNo - 1);

        checkedTask.markAsDone();
        output += checkedTask.toString();
        return output;
    }

    /**
     * Unmarks a task.
     *
     * @param input The task the user wants to unmark.
     */
    public String unMarkTask(String input) {
        String parts[] = input.split(" ");
        int taskNo = Integer.parseInt(parts[1]);
        Task checkedTask = this.taskList.get(taskNo - 1);

        checkedTask.markAsUndone();
        String output = "why are you not going to " + checkedTask.description + "? remember to do it later!\n";
        output += checkedTask.toString();
        return output;
    }

    /**
     * Deletes a Task from the list.
     *
     * @param input The task the user wants to delete.
     */
    public String deleteTask(String input) {
        String output;
        try {
            String parts[] = input.split(" ");
            int taskNo = Integer.parseInt(parts[1]);
            if (taskNo > this.listSize()) {
                throw new NoSuchTaskException();
            }
            Task checkedTask = this.taskList.get(taskNo - 1);
            this.taskList.remove(checkedTask);
            if (checkedTask.getStatusIcon().equals("X")) {
                output = "good job! you're officially done with this:\n";
            } else {
                output = "not you running away from your responsibilities, "
                        + "i guess you don't have to do this now:\n";
            }
            output += checkedTask.toString() + "\n";
        } catch (NoSuchTaskException e) {
            return "there is no such task!!";
        }

        if (this.listSize() == 0) {
            output += "THERES NOTHING LEFT TO DO!!!!";
        } else {
            output += "but still sucks to be you, you still have " + this.listSize() + " tasks";
        }
        return output;
    }

    /**
     * Adds a ToDo to the task list.
     *
     * @param input The ToDo the user wants to add.
     */
    public String addToDo(String input) {
        String output = "";
        try {
            ToDo item = new ToDo(input.replace("todo ", ""));

            if (item.description.isEmpty()) {
                throw new NoDescException();
            }

            this.taskList.add(item);
            if (input.contains("todo")) {
                output = "todo added: " + item.toString() + "\n";
                output += "You have this many stuff to complete: " + this.listSize();
            }
            return output;
        } catch (NoDescException e) {
            return "oi write something please";
        }
    }

    /**
     * Adds a Deadline to the task list.
     *
     * @param input The Deadline the user wants to add.
     */
    @SuppressWarnings("checkstyle:ArrayTypeStyle")
    public String addDeadline(String input) {
        String output = "";
        try {
            String parts[] = input.split("/by ");
            if (input.replace("deadline", "").isEmpty()) {
                throw new NoDescException();
            }
            if (!input.contains("/by")) {
                throw new DeadlineNoEndException();
            }

            Deadline item = new Deadline(parts[0].replace("deadline ", ""), parts[1]);
            this.taskList.add(item);
            if (input.contains("deadline")) {
                output = "deadline added: " + item.toString() + "\n";
                output += "You have this many stuff to complete: " + this.listSize();
            }
            return output;
        } catch (NoDescException e) {
            return "oi write something please";
        } catch (DeadlineNoEndException e) {
            int cryingEmojiUnicode = 0x1F62D;

            return "when is this due" + new String(Character.toChars(cryingEmojiUnicode));
        }
    }

    /**
     * Adds an Event to the task list.
     *
     * @param input The Event the user wants to add.
     */
    public String addEvent(String input) {
        String output = "";
        try {
            String parts[] = input.split("/from");
            if (parts.length == 1) {
                throw new NoDescException();
            }

            String time[] = parts[1].split("/to");

            Event item = new Event(parts[0].replace("event ", ""), time[0], time[1]);
            this.taskList.add(item);
            if (input.contains("event")) {
                output = "event added: " + item.toString() + "\n";
                output += "You have this many stuff to complete: " + this.listSize() + "\n";
            }
            return output;
        } catch (NoDescException e) {
            return e.getMessage();
        }
    }

    /**
     * Finds a task based on the keyword.
     *
     * @param input The keyword the user wants to find.
     */
    public String findTask(String input) {
        String parts[] = input.split("find ");
        String keyword = parts[1];
        String output = "Pau found these: \n";

        int count = 1;
        for (int i = 0; i < listSize(); i++) {
            Task curr = this.getTask(i);
            if (curr.description.contains(keyword)) {
                output += count + ". " + curr.toString() + "\n";
                count++;
            }
        }
        return output;
    }

    /**
     * Creates different tasks based on the input when the tasks are loaded.
     *
     * @param taskDetails Input of each task when tasks are loaded.
     */
    public void createTask(String taskDetails) {
        Scanner s = new Scanner(taskDetails).useDelimiter(" \\| ");
        String taskType = s.next();
        String status = s.next();
        String description = s.next();

        switch (taskType) {
        case "T":
            this.addToDo(description);
            this.taskList.get(listSize() - 1).setStatus(status);
            break;
        case "D":
            String wholeDeadline = description + "/by " + s.next();
            this.addDeadline(wholeDeadline);
            this.taskList.get(listSize() - 1).setStatus(status);
            break;
        case "E":
            String wholeEvent = description + "/from" + s.next() + "/to" + s.next();
            this.addEvent(wholeEvent);
            this.taskList.get(listSize() - 1).setStatus(status);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + taskType);
        }
    }

}
