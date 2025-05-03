package utils;

import commands.BasicCommand;
import structs.Request;
import structs.RequestType;

import java.util.Map;

public class RequestConstructor {

    public static Request createRequest(BasicCommand command, String[] args, Object object){
        if (object == null){
            return new Request(RequestType.ARGS_COMMAND, command.getName(), args);
        }
        return new Request(RequestType.OBJECT_COMMAND, command.getName(), args, object);
    }
}
