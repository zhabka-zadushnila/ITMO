package structs;

import java.io.Serializable;
import java.util.AbstractMap;

public class Request implements Serializable {
    RequestType requestType;
    String command;
    String[] arguments;
    Object object;
    String text;
    private static final long serialVersionUID = 1L;

    public Request(RequestType requestType , String command, String[] arguments){
        this(requestType, command, arguments, null);
    }

    public Request(RequestType requestType , String command, String[] arguments, Object object){
        this.requestType = requestType;
        this.command = command;
        this.arguments = arguments;
        this.object = object;
    }

    public Request(RequestType requestType , String command, String[] arguments, Object object, String text){
        this.requestType = requestType;
        this.command = command;
        this.arguments = arguments;
        this.object = object;
        this.text = text;
    }

    public AbstractMap.SimpleEntry<String, Object> getArgsObjectEntry(){
        return new AbstractMap.SimpleEntry<>(arguments[0], object);
    }

    public String getCommand() {
        return command;
    }

    public Object getArguments() {
        return arguments;
    }

    public String getText(){
        return text;
    }

    public boolean isText(){
        return requestType == RequestType.TEXT;
    }

    public boolean isCommand(){
        return requestType == RequestType.ARGS_COMMAND;
    }

    public boolean isObjectCommand(){
        return requestType == RequestType.OBJECT_COMMAND;
    }
    @Override
    public String toString(){
        if(requestType == RequestType.TEXT){
            return  text;
        }
        if(requestType == RequestType.ARGS_COMMAND || requestType == RequestType.OBJECT_COMMAND){
            return command;
        }
        return "unknown";
    }
}
