package commands;

import classes.Dragon;
import exceptions.NoSuchElementException;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DragonCreationManager;


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
        String[] args = (String[]) arguments;
        if (args.length == 0) {
            throw new NullArgsForbiddenException();
        }

        if (args[0].trim().isBlank()) {
            throw new NullArgsForbiddenException();
        }
        String inputId = args[0];
        if(collectionManager.hasElement(inputId)){
            Dragon dragon = DragonCreationManager.inputDragon(commandManager.getCommandInterpreter().getInputIterator());
            if (dragon == null){
                return "";
            }
            if(collectionManager.getElement(inputId).compareTo(dragon) < 0) {
                collectionManager.replaceElement(inputId, dragon);
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
