package dan.parser;

import java.time.format.DateTimeParseException;

import dan.exceptions.DanException;
import dan.task.TaskList;
import dan.ui.Ui;
/**
 * A parser to help parse and execute command-line inputs by the user
 */
public class Parser {
    private TaskList tasks;
    private boolean isExit = false;

    /**
     * Constructor method. Associates the parser to its list of tasks to perform actions on.
     *
     * @param tasks TaskList of tasks
     */
    public Parser(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Parses the command-line input before executing the command.
     *
     * @param input The raw input data entered by the user
     * @return A boolean of the program exit status
     */
    public String parse(String input) {
        String[] inputArr = input.split(" ");
        String action = inputArr[0].strip();
        assert !isExit : "The program should have exited already";
        try {
            switch (action) {
            case "bye":
                isExit = true;
                return Ui.bye();
            case "list":
                return tasks.showTasks();
            case "mark":
                return tasks.markTask(Integer.parseInt(inputArr[1]));
            case "unmark":
                return tasks.unmarkTask(Integer.parseInt(inputArr[1]));
            case "delete":
                return tasks.deleteTask(Integer.parseInt(inputArr[1]));
            case "find":
                return tasks.findTask(input.split(" ", 2)[1]);
            case "todo":
                return tasks.addToDoTask(input);
            case "deadline":
                return tasks.addDeadlineTask(input);
            case "event":
                return tasks.addEventTask(input);
            default:
                throw DanException.userInputError();
            }
        } catch (DanException e) {
            return Ui.printIndent(e.getMessage());
        } catch (NumberFormatException nfe) {
            return Ui.printIndent("Please use an integer instead");
        } catch (DateTimeParseException dte) {
            return Ui.printIndent("Please use the format dd/MM/yyyy HHmm for dates");
        }
    }


    public boolean getIsExit() {
        return isExit;
    }

}