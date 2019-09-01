package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.ControllerServer.clients;

public class ServerThread extends Thread {
    protected Socket socket;

    ObjectInputStream in;
    //Server waits for Message object from client, when it gets it. it 
    public void run(){

        try {
            ServerSocket serverSocket = new ServerSocket(1);
            socket = serverSocket.accept();
            while (true) {
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                clients.add(new ClientConnected(dataOutputStream,in, socket));
                Message message = (Message)in.readObject();
                String msg = message.getName()+ ": " + message.getMsg();
                System.out.println(msg);
                System.out.println("Message created");
                for (int i = 0; i < clients.size(); i++) {
                    clients.get(i).sendMessage(msg);
                    System.out.println("Message sent");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}