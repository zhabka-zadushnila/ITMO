package commands;

import classes.Dragon;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Removes elements with key greater than this one
 */
public class RemoveGreaterKeyCommand extends BasicCommand{
    public RemoveGreaterKeyCommand(CollectionManager collectionManager){
        super("remove_greater_key", "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный", collectionManager);
    }


    @Override
    public String execute(Object arguments) {
        String[] args = (String[]) arguments;

        if (args.length == 0 || args[0].trim().isBlank()) {
            throw new NullArgsForbiddenException();
        }

        String masterId = args[0].trim();
        Map<String, Dragon> originalCollection = collectionManager.getCollection();
        Map<String, Dragon> newCollection = new HashMap<>(originalCollection);

        Set<String> keysToDelete = originalCollection.keySet().stream()
                .filter(key -> !key.equals(masterId))
                .filter(key -> masterId.compareTo(key) > 0)
                .collect(Collectors.toSet());


        keysToDelete.forEach(newCollection::remove);
        int deleted = keysToDelete.size();

        collectionManager.setCollection(newCollection);
        return String.format("Deleted" + deleted + "objects ^_^");
    }
}
