package commands;

import exceptions.CustomException;
import managers.CollectionManager;
import structs.User;

import java.io.FileNotFoundException;

/**
 * Abstract class for all the commands. Contains exceptional name and description, execute() method.
 */

abstract public class BasicCommand {

    private final String name;
    private final String description;
    protected CollectionManager collectionManager;

    public BasicCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public BasicCommand(String name, String description, CollectionManager collectionManager) {
        this(name, description);
        this.collectionManager = collectionManager;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    /**
     * Execute function stands for basic command execution process. Execute method is being executed in CommandManager.
     *
     * @param args - an array of strings (words that were separated by spaces). Usually it is ignored in commands that do not need any args, and those who need, get only as much as they need (others are being ignored)
     */
    public String execute(Object args, User user) throws CustomException, FileNotFoundException {
        return (this.name + " is not done yet. ^_^");

    }
}
