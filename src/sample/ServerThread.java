package sample;

import sample.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import static sample.ControllerServer.sockets;

public class ServerThread extends Thread {
    protected Socket socket;

    ObjectInputStream in;
    //Server waits for Message object from client, when it gets it. it 
    public void run(){

        try {
            ServerSocket serverSocket = new ServerSocket(1);
            socket = serverSocket.accept();
            sockets.add(socket);
            while (true) {
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                Message message = (Message)in.readObject();
                String msg = message.getName()+ ": " + message.getMsg();
                System.out.println("Message created");
                for (int i = 0; i < sockets.size(); i++) {
                    DataOutputStream out = new DataOutputStream(sockets.get(i).getOutputStream());
                    System.out.println("message sent");
                    out.writeChars(msg);
                    System.out.println("Message written");
                    out.flush();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}