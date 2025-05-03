package commands;

import classes.Dragon;
import classes.DragonCharacter;
import exceptions.NoSuchElementException;
import exceptions.NullArgsForbiddenException;
import managers.CollectionManager;
import utils.InputChecker;

import java.util.Map;
import java.util.Objects;

/**
 * Outputs amount of elements in which character field contains value less than given
 */
public class CountLessThanCharacterCommand extends BasicCommand {
    public CountLessThanCharacterCommand(CollectionManager collectionManager) {
        super("count_less_than_character", "count_less_than_character character : вывести количество элементов, значение поля character которых меньше заданного", collectionManager);
    }


    @Override
    public String execute(Object arguments) throws NullArgsForbiddenException, IllegalArgumentException {
        String[] args = (String[]) arguments;
        DragonCharacter character;
        if (args.length == 0 || args[0].trim().isBlank()) {
            return "You have to pick enum element: evil, chaotic_evil, fickle";
        }
        try{
            character = InputChecker.getEnum(args[0], DragonCharacter.class);
        } catch (NoSuchElementException e){
            return "You have to pick enum element: evil, chaotic_evil, fickle";
        }
        return String.valueOf(collectionManager.getCollection().values().stream()
                .filter((dragon) -> dragon.getCharacter().compareTo(character) < 0)
                .count());
    }
}
