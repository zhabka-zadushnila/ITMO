package utils;

import commands.BasicCommand;
import managers.ConnectionManager;
import structs.Request;
import structs.RequestType;

import java.util.logging.Logger;

public class RequestConstructor {
    private static final Logger logger = Logger.getLogger(RequestConstructor.class.getName());
    public static Request createRequest(BasicCommand command, String[] args, Object object){
        logger.fine("Created object request");
        if (object == null){
            return new Request(RequestType.ARGS_COMMAND, command.getName(), args);
        }
        return new Request(RequestType.OBJECT_COMMAND, command.getName(), args, object);
    }
    public static Request createRequest(String string){
        logger.fine("Created text request with: " + string);
        return new Request(RequestType.TEXT, null, null, null, string);
    }
}
