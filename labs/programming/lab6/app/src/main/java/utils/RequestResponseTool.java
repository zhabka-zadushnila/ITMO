package utils;

import exceptions.WrongRequestException;
import structs.Request;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RequestResponseTool {
    static public boolean sendRequest(SocketChannel channel, Request request) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)){
            objectStream.writeObject(request);
            objectStream.flush();
            byte[] data = byteStream.toByteArray();

            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            lengthBuffer.putInt(data.length);
            lengthBuffer.flip();
            channel.write(lengthBuffer);

            channel.write(ByteBuffer.wrap(data));
            return true;
        } catch (NotSerializableException e){
            System.out.println("Вы положили что-то не сериализуемое в реквест");
            return false;
        }
    }

    static public Request getRequest(SocketChannel channel) throws WrongRequestException {
        try {
            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            while (lengthBuffer.hasRemaining()) {
                if (channel.read(lengthBuffer) == -1) {
                    System.out.println("Connection interrupted");
                    return null;
                }
            }
            lengthBuffer.flip();
            int length = lengthBuffer.getInt();
            ByteBuffer buffer = ByteBuffer.allocate(length);


            while (buffer.hasRemaining()) {
                if (channel.read(buffer) == -1) {
                    throw new EOFException("Unexpected end of stream");
                }
            }

            buffer.flip();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Object object = objectInputStream.readObject();

            if (object instanceof Request){
                return (Request) object;
            }else{
                throw new WrongRequestException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Input/output error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Received corrupted or unknown object: " + e.getMessage());
        }
        return null;
    }

}
