package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnected {
    DataOutputStream dataOutputStream;
    ObjectInputStream in;


    public ClientConnected(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public ClientConnected(DataOutputStream dataOutputStream, ObjectInputStream in) {
        this.dataOutputStream = dataOutputStream;
        this.in = in;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
    public void sendMessage(String msg){
        try {
            dataOutputStream.writeChars(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
