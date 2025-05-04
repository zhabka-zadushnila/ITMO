package managers;

import Interpreters.ServerCommandInterpreter;
import exceptions.CustomException;
import structs.Request;
import utils.RequestConstructor;
import utils.RequestResponseTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    ServerCommandInterpreter serverCommandInterpreter;
    ServerSocketChannel serverSocketChannel;
    Selector selector;
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    int port;
    public ConnectionManager (int port, ServerCommandInterpreter serverCommandInterpreter) {
        this.port = port;
        this.serverCommandInterpreter = serverCommandInterpreter;
    }

    public void serve(){
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("Server initialized");
        }catch (IOException e) {
            System.out.println("Произошла ошибка инициализации сервера.");
            logger.warning("Server did not initialize");
            System.exit(1);
        }
        while (true){
            try{
                selector.select(1000);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(true);
                        ByteBuffer buffer = ByteBuffer.allocate(1);
                        int bytesRead = client.read(buffer);
                        if (bytesRead == -1) {
                            System.out.println("Client disconnected before sending message.");
                            client.close();
                            continue;
                        }

                        if (bytesRead != 1){
                            System.out.println("Strange client hello accepted (" + String.valueOf(bytesRead) + " bytes)");
                            logger.info("Strange client hello accepted");

                            continue;
                        }
                        buffer.flip();
                        if(buffer.get() == 0x42){
                            buffer.clear();
                            buffer.put((byte) 0x43);
                            buffer.flip();
                            client.write(buffer);
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            logger.info("New client registered");                       }

                    }
                    if (key.isReadable()) {
                        logger.info("Client sended request.");
                        SocketChannel client = (SocketChannel) key.channel();
                        Request request = RequestResponseTool.getRequest(client);
                        if(request == null){
                            key.cancel();
                            client.close();
                            logger.info("Client disconnected");
                            iterator.remove();
                            continue;
                        }
                        try {
                            String output = serverCommandInterpreter.executeRequest(request);
                            if (output != null) {
                                logger.info("Key is writable now");
                                key.attach(output);
                                key.interestOps(SelectionKey.OP_WRITE);
                            }else {
                                key.attach("Executed successfully.");
                                key.interestOps(SelectionKey.OP_WRITE);
                            }                        } catch (CustomException e) {
                            e.printStackTrace();
                        }
                    }
                    if(key.isWritable()){
                        logger.fine("Server is about to output: \n"+ (String) key.attachment());
                        Request request = RequestConstructor.createRequest((String) key.attachment());
                        RequestResponseTool.sendRequest((SocketChannel) key.channel(), request);
                        key.attach(null);
                        key.interestOps(SelectionKey.OP_READ);
                    }

                    iterator.remove();

                }
                if (consoleReader.ready()) {
                    String file = "defaultFile";
                    String line = consoleReader.readLine();
                    String[] list = line.trim().split("[ \t]+");
                    if (list.length >= 1){
                        if(list.length > 1){
                            file = list[1];
                        }
                       if(list[0].equals("exit")){
                           System.out.println("Cервер завершает работу и сохраняет коллекцию в файл: "+file);
                           saveToFile(file);
                           System.exit(1);
                       }
                       if(list[0].equals("save")){
                           System.out.println("Сохраняем в файл:" + file);
                           saveToFile(file);
                       }
                    }
                }
            } catch (IOException e) {
                logger.warning("kinda sus stuff just happened in connection manager: " + e);
            }
        }

    }

    private void saveToFile(String filename){
        FileManager.saveCollectionToFile(filename, serverCommandInterpreter.getCollectionManager().getCollection());
    }



}
