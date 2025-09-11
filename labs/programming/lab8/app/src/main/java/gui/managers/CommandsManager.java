package gui.managers;

import commands.server.*;
import managers.ConnectionManager;
import structs.Packet;
import structs.User;
import structs.classes.Dragon;
import utils.RequestConstructor;
import utils.RequestResponseTool;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class CommandsManager {

    static int MAX_RECONNECT_ATTEMPTS = 5;
    static int RECONNECT_TIMEOUT = 2000; //millis
    String hostname = "localhost";
    int port = 52947;
    SocketChannel channel = ConnectionManager.connectToServer(hostname, port, MAX_RECONNECT_ATTEMPTS, RECONNECT_TIMEOUT);

    public CommandsManager() {
    }


    public CommandsManager(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String insertDragon(Map.Entry<String, Dragon> dragonEntry, User user) {
        Packet packet = RequestConstructor.createRequest(new InsertCommand(null, null), null, dragonEntry, user);
        try {
            RequestResponseTool.sendPacket(channel, packet);
        } catch (IOException ex) {
            return "Some troubles";
        }
        packet = RequestResponseTool.getPacket(channel);
        if (packet.isText()) {
            return packet.getText();
        } else {
            return "Response is not text";
        }
    }

    public String updateDragon(Map.Entry<String, Dragon> dragonEntry, User user) {
        Packet packet = RequestConstructor.createRequest(new UpdateCommand(null, null), null, dragonEntry, user);
        try {
            RequestResponseTool.sendPacket(channel, packet);
        } catch (IOException ex) {
            return "Some troubles";
        }
        packet = RequestResponseTool.getPacket(channel);
        if (packet.isText()) {
            return packet.getText();
        } else {
            return "Response is not text";
        }
    }

    public String replaceIfLowerDragon(Map.Entry<String, Dragon> dragonEntry, User user) {
        Packet packet = RequestConstructor.createRequest(new ReplaceIfLowerCommand(null, null), null, dragonEntry, user);
        try {
            RequestResponseTool.sendPacket(channel, packet);
        } catch (IOException ex) {
            return "Some troubles";
        }
        packet = RequestResponseTool.getPacket(channel);
        if (packet.isText()) {
            return packet.getText();
        } else {
            return "Response is not text";
        }
    }

    public String removeKeyDragon(String[] args, User user) {
        Packet packet = RequestConstructor.createRequest(new RemoveKeyCommand(null), args, null, user);
        try {
            RequestResponseTool.sendPacket(channel, packet);
        } catch (IOException ex) {
            return "Some troubles";
        }
        packet = RequestResponseTool.getPacket(channel);
        if (packet.isText()) {
            return packet.getText();
        } else {
            return "Response is not text";
        }
    }

    public String removeKeyGrDragon(String[] args, User user) {
        Packet packet = RequestConstructor.createRequest(new RemoveGreaterKeyCommand(null), args, null, user);
        try {
            RequestResponseTool.sendPacket(channel, packet);
        } catch (IOException ex) {
            return "Some troubles";
        }
        packet = RequestResponseTool.getPacket(channel);
        if (packet.isText()) {
            return packet.getText();
        } else {
            return "Response is not text";
        }
    }
}
