package utils;

import commands.BasicCommand;
import structs.Packet;
import structs.PacketType;
import structs.User;

import java.util.logging.Logger;

public class RequestConstructor {
    private static final Logger logger = Logger.getLogger(RequestConstructor.class.getName());

    public static Packet createRequest(BasicCommand command, String[] args, Object object, User user) {
        logger.fine("Created object request");
        if (object == null) {
            return new Packet(PacketType.ARGS_COMMAND, command.getName(), args, user);
        }
        return new Packet(PacketType.OBJECT_COMMAND, command.getName(), args, object, user);
    }

    public static Packet createRequest(String string) {
        logger.fine("Created text request with: " + string);
        return new Packet(PacketType.TEXT, null, null, null, string, null);
    }
}
