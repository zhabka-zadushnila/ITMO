package commands;

import classes.Dragon;
import exceptions.NoSuchElementException;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DragonCreationManager;

import java.util.Map;


/**
 * Replaces all the elements that are lower
 */
public class ReplaceIfLowerCommand extends BasicCommand{
    CommandManager commandManager;
    public ReplaceIfLowerCommand(CollectionManager collectionManager, CommandManager commandManager){
        super("replace_if_lowe", "replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого", collectionManager);
        this.commandManager = commandManager;
    }



    @Override
    public String execute(Object arguments) throws NullArgsForbiddenException, NoSuchElementException {
        if (arguments == null){
            return "Something strange, seems like you've provided null";
        }
        Map.Entry<String, Dragon> entry = (Map.Entry<String, Dragon>) arguments;
        Dragon dragon = entry.getValue();
        if (dragon == null){
            return "";
        }


        if(collectionManager.hasElement(entry.getKey())){

            if(collectionManager.getElement(entry.getKey()).compareTo(dragon) < 0) {
                dragon.setId(Dragon.getIdCreator());
                Dragon.setIdCreator(Dragon.getIdCreator() +1);
                collectionManager.replaceElement(entry.getKey(), dragon);
            }
            else{
                return ("New dragon is not less than new one, nothing has changed :)");
            }
        }else{
            throw new NoSuchElementException();
        }
        return "";
    }
}
