package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnected implements Runnable{
    DataOutputStream dataOutputStream;
    ObjectInputStream in;
    Socket socket;
    DataOutputStream clientOut;
    private ClientConnectedListener listener;

    public interface ClientConnectedListener{
        void newMessage(String sender, String msg);
    }

    public ClientConnected(Socket socket, ClientConnectedListener listener) {
        this.listener = listener;
        try {
            this.socket = socket;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getClientOut() {
        return clientOut;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
    public void sendMessage(String msg){
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                Message message = (Message) in.readObject();
                listener.newMessage(message.getName(), message.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
