package managers;

import classes.Dragon;
import exceptions.CustomException;
import exceptions.WrongRequestException;
import structs.Request;
import utils.InputTools;
import utils.RequestConstructor;
import utils.RequestResponseTool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

import static java.lang.Thread.sleep;

public class ClientManager {

    static int MAX_RECONNECT_ATTEMPTS = 5;
    static int RECONNECT_TIMEOUT = 2000; //millis

    Iterator<String> it;
    String hostname;
    int port;
    CommandManager commandManager;
    Set<String> executableFiles = new HashSet<String>();
    boolean fileNotEmpty = true;
    SocketChannel socketChannel;

    public ClientManager(Iterator<String> it, String hostname, int port, CommandManager commandManager){
        this.it = it;
        this.hostname = hostname;
        this.port = port;
        this.commandManager = commandManager;
        commandManager.setClientManager(this);
    }
    public ClientManager(Iterator<String> it, String hostname, int port, CommandManager commandManager, Set<String> executableFiles, SocketChannel socketChannel){
        this(it, hostname, port, commandManager);
        this.executableFiles = executableFiles;
        this.socketChannel = socketChannel;
    }


    public void run(){
        try (SocketChannel channel = connectToServer()){
            handleConnection(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void runFile(){
        boolean fileEnded = false;
        while (socketChannel.isConnected() && !fileEnded) {
            try{
                fileEnded = fileWriteOperation(socketChannel);
                if (!fileEnded){
                    readOperation(socketChannel);
                }
            } catch (IOException e){
                System.out.println("Похоже произошёл лёгкий дисконнект. Перезапустимся.");
                socketChannel = connectToServer();
            }
        }
    }


    private void handleConnection(SocketChannel channel) throws IOException {
        while (channel.isConnected()) {
            try{
                writeOperation(channel);
                readOperation(channel);
            } catch (IOException e){
                System.out.println("Похоже произошёл лёгкий дисконнект. Перезапустимся.");
                channel = connectToServer();
            }
        }
    }


    private void readOperation(SocketChannel channel) {
        //System.out.println("read is awaited");
        try {
            Request request = RequestResponseTool.getRequest(channel);
            if(request == null){
                return;
            }
            if(request.isText()){
                System.out.println(request.getText());
            }else{
                System.out.println("Request can not be processed for now");
            }
        }catch (WrongRequestException e){
            System.out.println(e);
        }

    }

    private void writeOperation(SocketChannel channel) throws IOException {
        String line;
        String command;
        String[] args;
        String[] list;
        boolean requestSent = false;
        while(!requestSent) {
            System.out.print(">>> ");
            if (it.hasNext()) {
                line = it.next();

                if(line.trim().isBlank()){
                    continue;
                }
                list = InputTools.splitLine(line);
                if (!commandManager.hasCommand(list[0])) {
                    System.out.println("Unknown command: " + list[0]);
                    continue;
                }

                command = list[0];
                args = list.length > 1 ? Arrays.copyOfRange(list, 1, list.length) : new String[0];

                try {
                    if (commandManager.isLocalCommand(command)) {
                        commandManager.getCommand(command).execute(args);
                    } else {
                        // Ход достойный гения (одновременно очень грязный и невероятно чистый)
                        Request request = RequestConstructor.createRequest(commandManager.getCommand(command), args, commandManager.getCommand(command).execute(args));
                        // System.out.println("request ready to send");
                        RequestResponseTool.sendRequest(channel, request);
                        requestSent = true;
                    }
                } catch (CustomException e) {
                    System.out.println(e);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }


            }else{
                System.out.println("\nInput finished. Suicide!");
                System.exit(1);
            }
        }
        //System.out.println("Waiting for response");
    }
/*
    private void fileHandle(SocketChannel channel) throws IOException {
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_WRITE);

        while (channel.isConnected() && it.hasNext()){
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while(keys.hasNext()){
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isWritable()){
                    fileWriteOperation(channel, key);

                }
                if (key.isReadable()){
                    readOperation(channel);
                }
            }

        }

    }
*/

    private boolean fileWriteOperation(SocketChannel channel) throws IOException {
        String line;
        String command;
        String[] args;
        String[] list;
        boolean requestSent = false;
        while(!requestSent) {
            if (it.hasNext()) {
                line = it.next();

                if(line.trim().isBlank()){
                    continue;
                }
                list = InputTools.splitLine(line);
                if (!commandManager.hasCommand(list[0])) {
                    System.out.println("Unknown command: " + list[0]);
                    continue;
                }

                System.out.println(Arrays.toString(list));
                command = list[0];
                args = list.length > 1 ? Arrays.copyOfRange(list, 1, list.length) : new String[0];

                try {
                    if (commandManager.isLocalCommand(command)) {
                        commandManager.getCommand(command).execute(args);
                    } else {
                        // Ход достойный гения (одновременно очень грязный и невероятно чистый)
                        Request request = RequestConstructor.createRequest(commandManager.getCommand(command), args, commandManager.getCommand(command).execute(args));
                        RequestResponseTool.sendRequest(channel, request);
                        requestSent = true;
                    }
                } catch (CustomException e) {
                    System.out.println(e);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }

            }else{
                System.out.println("\nFile execution finished!");
                return true;
            }
        }
        return false;
    }


    private SocketChannel connectToServer(){
        for (int i =0; i < MAX_RECONNECT_ATTEMPTS; i++) {
            try {
                SocketChannel channel = SocketChannel.open(new InetSocketAddress(hostname, port));
                channel.configureBlocking(false);
                this.socketChannel = channel;
                System.out.println("Соединение установлено");
                return channel;

            }catch (IOException e){
                System.out.println("Соединение устанавливается. Попытка №" +(i+1) + "/5");
                try {
                    sleep(RECONNECT_TIMEOUT);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
        System.out.println("Соединение не установилось спустя 5 попыток. Обрываемся.");
        System.exit(1);
        return null;
    }

    public Iterator<String> getInputIterator() {
        return it;
    }

    public void setIt(Iterator<String> it) {
        this.it = it;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean executedFile(String filename){
        return executableFiles.contains(filename);
    }


    public Set<String> getExecutableFiles() {
        return executableFiles;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
