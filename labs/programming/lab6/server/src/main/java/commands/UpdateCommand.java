package commands;

import classes.Dragon;
import exceptions.NoSuchElementException;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DragonCreationManager;

import java.util.Map;

/**
 * Used for updating element with specific id
 */
public class UpdateCommand extends BasicCommand{
    CommandManager commandManager;
    public UpdateCommand(CollectionManager collectionManager, CommandManager commandManager){
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному", collectionManager);
        this.commandManager = commandManager;
    }



    @Override
    public String execute(Object arguments) throws NullArgsForbiddenException, NoSuchElementException {
        Map.Entry<String, Dragon> entry = (Map.Entry<String, Dragon>) arguments;

        if(collectionManager.hasElement(entry.getKey())){

            if (entry.getValue() == null){
                return "Got null, nothing changes";
            }
            collectionManager.replaceElement(entry.getKey(), entry.getValue());
        }else{
            return new NoSuchElementException().toString();
        }
        return "";
    }
}
