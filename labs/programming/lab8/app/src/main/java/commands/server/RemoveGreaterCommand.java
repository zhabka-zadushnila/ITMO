package commands.server;

import classes.Dragon;
import commands.BasicCommand;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Removes greater elements (gets initial element by id)
 */
public class RemoveGreaterCommand extends BasicCommand {
    public RemoveGreaterCommand(CollectionManager collectionManager){
        super("remove_greater", "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный", collectionManager);
    }


}
