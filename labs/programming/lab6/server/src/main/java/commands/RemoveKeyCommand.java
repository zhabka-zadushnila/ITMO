package commands;

import exceptions.NoSuchElementException;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;

import java.util.Objects;


/**
 * Removes element by key. That's all
 */
public class RemoveKeyCommand extends BasicCommand{
    public RemoveKeyCommand(CollectionManager collectionManager){
        super("remove_key", "remove_key null : удалить элемент из коллекции по его ключу", collectionManager);
    }


    @Override
    public String execute(Object arguments) throws NullArgsForbiddenException, NoSuchElementException {
        String[] args = (String[]) arguments;
        int deleted = 0;
        if (args.length == 0) {
            throw new NullArgsForbiddenException();
        }

        if (args[0].trim().isBlank()) {
            throw new NullArgsForbiddenException();
        }

        String masterId = args[0];

        if(collectionManager.hasElement(masterId)){
            collectionManager.killElement(masterId);
            return ("Элемент " + masterId + " удалён успешно.");
        }else{
            throw new NoSuchElementException();
        }
    }

}
