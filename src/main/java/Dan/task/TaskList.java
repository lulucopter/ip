package dan.task;

import dan.task.Task;
import java.util.List;

import dan.DanException;
import dan.ui.Ui;

/**
 * Represents a list of tasks
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list of tasks
     *
     * @param input task details sent in after parsing
     * @throws DanException if the input format is not expected
     */
    public void addTask(String input) throws DanException {
        String description;
        String dateString;
        if (input.startsWith("todo")) {
            description = input.replace("todo", "").strip();
            if (description.isEmpty()) {
                throw new DanException("Please provide me a description for your todo item");
            }
            tasks.add(new ToDo(description));

        } else if (input.startsWith("deadline")) {
            String[] temp = input.replace("deadline","").strip().split("/by");
            if (temp.length != 2) {
                throw new DanException("Please follow the following format:\n deadline <description> /by <due date>");
            }
            description = temp[0].strip();
            dateString = temp[1].strip();
            if (description.isEmpty()) {
                throw new DanException("Please provide me a description for your deadline");
            }
            tasks.add(new Deadline(description, dateString));

        } else if (input.startsWith("event")) {
            String[] temp = input.replace("event", "").strip().split("/at");
            if (temp.length != 2) {
                throw new DanException("Please follow the following format:\n event <description> /at <time/date>");
            }
            description = temp[0].strip();
            dateString = temp[1].strip();
            if (description.isEmpty()) {
                throw new DanException("Please provide me a description for your event");
            }
            tasks.add(new Event(description, dateString));
        }
        Ui.printLine();
        Ui.printIndent("Okay okay, I'll add this task then:");
        Ui.printIndent(tasks.get(tasks.size() - 1).toString());
        Ui.printIndent(String.format("You now have %d many tasks in your list", tasks.size()));
        Ui.printLine();
    }

    /**
     * Displays the the current tasks in the list
     *
     * @throws DanException if there are no available tasks
     */
    public void showTasks() throws DanException {
        if (tasks.isEmpty()) {
            throw new DanException("Your list is empty!");
        }
        Ui.printLine();
        Ui.printIndent("Here are the tasks in your list:");
        Ui.printIndent(this);
        Ui.printLine();
        }

    /**
     * Marks the indicated task as completed
     *
     * @param index Task number (as displayed in `showTasks()`) of the task that is completed
     * @throws DanException if the given task number does not exist in the list
     */
    public void markTask(int index) throws DanException {
        if (index > tasks.size()) {
            Ui.printIndent("tasks.size(): " + tasks.size());
            throw new DanException("This task number doesn't exist!");
        }
        Task task = tasks.get(index - 1);
        task.setDone(true);
        Ui.printBlock(String.format("Hehe okay guess this is now done\n"
                + "  %s", task));
    }

    /**
     * Marks the indicated task as uncompleted
     *
     * @param index Task number (as displayed in `showTasks()`) of the task that is completed
     * @throws DanException if the given task number does not exist in the list
     */
    public void unMarkTask(int index) throws DanException {
        if (index > tasks.size()) {
            throw new DanException("This task number doesn't exist!");
        }
        Task task = tasks.get(index - 1);
        task.setDone(false);
        Ui.printBlock(String.format("Ooops, you haven't done this yet? Here ya go:\n"
                + "  %s", task));
    }

    /**
     * Removes the indicated task from the list
     *
     * @param index Task number (as displayed in `showTasks()`) of the task that is completed
     * @throws DanException if the given task number does not exist in the list
     */
    public void deleteTask(int index) throws DanException {
        if (index > tasks.size()) {
            throw new DanException("This task number doesn't exist!");
        }
        Ui.printLine();
        Ui.printIndent("Alright then, I'll remove this task from your list:");
        Ui.printIndent(tasks.get(index - 1).toString());
        tasks.remove(index - 1);
        Ui.printIndent(String.format("You now have %d many tasks in your list", tasks.size()));
        Ui.printLine();
    }

    /**
     * Searches the task list for all tasks' descriptions that contains the keyword
     *
     * @param keyword String to be searched for among the tasks in the list
     */
    public void findTask(String keyword) {
        StringBuilder result = new StringBuilder();
        for (int i =1; i <= tasks.size(); i++) {
            Task task = tasks.get(i - 1);
            if (task.description.contains(keyword)) {
                result.append(i + "." + task + "\n");
            }
        }
        Ui.printLine();
        Ui.printIndent("Alright! Here are the matching tasks in your list");
        Ui.printIndent(
                (result.toString().length() == 0)
                ? "I couldn't find any task that matches your description"
                : result.toString());
        Ui.printLine();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Displays the list of tasks in a 1-indexed numbered list.
     *
     * @return Text display of the tasks in the list
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i =1; i <= tasks.size(); i++) {
            result.append(i + "." + tasks.get(i - 1) + "\n");
        }
        return result.toString();
    }

}
