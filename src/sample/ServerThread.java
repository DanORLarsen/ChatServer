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
    public ServerSocket serverSocket;
    ObjectInputStream in;
    public ServerThread(ServerSocket serverSocket1){
        serverSocket1 = this.serverSocket;
    }
    //Server waits for Message object from client, when it gets it. it 
    public void run(){
        try {
            socket = serverSocket.accept();
            sockets.add(socket);
            new ServerThread(serverSocket).start();
            while (true) {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                Message message = (Message)in.readObject();
                String msg = message.getName()+ ": " + message.getMsg();
                System.out.println("Message created");
                for (Socket socket: sockets
                     ) {
                    System.out.println("message sent");
                    out.writeChars(msg);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}