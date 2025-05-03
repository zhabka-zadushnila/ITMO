package commands;

import classes.Dragon;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Removes greater elements (gets initial element by id)
 */
public class RemoveGreaterCommand extends BasicCommand{
    public RemoveGreaterCommand(CollectionManager collectionManager){
        super("remove_greater", "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный", collectionManager);
    }


    @Override
    public String execute(Object arguments) throws NullArgsForbiddenException {
        String[] args = (String[]) arguments;


        if (args.length == 0 || args[0].trim().isBlank()) {
            throw new NullArgsForbiddenException();
        }

        String input = args[0].trim();
        Dragon masterElement = collectionManager.getElement(input);

        if (masterElement == null) {
            return "Такого элемента нет";
        }

        Map<String, Dragon> originalCollection = collectionManager.getCollection();
        Map<String, Dragon> newCollection = new HashMap<>(originalCollection);


        Set<String> keysToDelete = originalCollection.entrySet().stream()
                .filter((entry) -> !masterElement.equals(entry.getValue()))
                .filter((entry) -> masterElement.compareTo(entry.getValue()) > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());


        keysToDelete.forEach(newCollection::remove);
        int deleted = keysToDelete.size();

        collectionManager.setCollection(newCollection);
        return String.format("Deleted %d objects ^_^", deleted);
    }
}
