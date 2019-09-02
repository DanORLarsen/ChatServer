package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnected {
    DataOutputStream dataOutputStream;
    ObjectInputStream in;
    Socket socket;
    DataOutputStream clientOut;


    public ClientConnected(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public ClientConnected(DataOutputStream dataOutputStream, ObjectInputStream in, Socket socket) {
        this.dataOutputStream = dataOutputStream;
        this.in = in;
        this.socket = socket;
        try {
            this.clientOut = new DataOutputStream(socket.getOutputStream());
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
}
