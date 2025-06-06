package managers;

import Interpreters.ServerCommandInterpreter;
import commands.BasicCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages all the commands. Actually, that's some sort of a wrapper for Map ^_^
 * Maybe got no sense but still I love it and I think it is some sort of Controller in MVC.
 *
 *
*/
public class CommandManager {
    private final Map<String, BasicCommand> commandsMap = new HashMap<String, BasicCommand>();
    CollectionManager collectionManager;
    ServerCommandInterpreter serverCommandInterpreter;

    /**
     * Constructor for class
     * @param collectionManager basically a {@link CollectionManager}
     */
    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Adds command in command manager.
     * @param command object of {@link BasicCommand} children
     */
    public void addCommand(BasicCommand command) {
        this.commandsMap.put(command.getName(), command);

    }

    public Map<String, BasicCommand> getCommandsMap() {
        return this.commandsMap;
    }

    public CollectionManager getCollectionManager(){
        return this.collectionManager;
    }

    public void setCommandInterpreter(ServerCommandInterpreter serverCommandInterpreter) {
        this.serverCommandInterpreter = serverCommandInterpreter;
    }

    public ServerCommandInterpreter getCommandInterpreter() {
        return serverCommandInterpreter;
    }


    public BasicCommand getCommand(String name){
        return commandsMap.get(name);
    }


}
