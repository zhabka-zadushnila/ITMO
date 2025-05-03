package commands;

import classes.Dragon;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DragonCreationManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Command that is used for adding some stuff in collection. Operates with both {@link CommandManager} and {@link CollectionManager}.
 */

public class InsertCommand extends BasicCommand{
    CommandManager commandManager;
    public InsertCommand(CollectionManager collectionManager, CommandManager commandManager){
        super("insert", "insert null {element} : добавить новый элемент с заданным ключом", collectionManager);
        this.commandManager = commandManager;
    }

    @Override
    public String execute(Object arguments) throws NullArgsForbiddenException {


        if (arguments == null){
            return "Something strange, seems like you've provided null";
        }

        Map.Entry<String, Dragon> entry = (Map.Entry<String, Dragon>) arguments;
        if(entry.getValue() == null){
            return "";
        }
        if(collectionManager.hasElement(entry.getKey())) {
            return "such element already exists, nothing changed";
        }else{
            collectionManager.addElement(entry.getKey(), entry.getValue());
        }

        return "element added";
    }



}
