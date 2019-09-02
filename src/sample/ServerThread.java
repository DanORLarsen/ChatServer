package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.ControllerServer.clients;

public class ServerThread extends Thread implements ClientConnected.ClientConnectedListener {
    protected Socket socket;
    ServerSocket serverSocket;

    ObjectInputStream in;

    //Server waits for Message object from client, when it gets it. it
    public void run() {
        try {
            serverSocket = new ServerSocket(1);
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ClientConnected clientConnected = new ClientConnected(socket, this);
                clients.add(clientConnected);
                new Thread(clientConnected).start();
            }
            } catch(IOException e){
                e.printStackTrace();
            }

    }


    @Override
    public void newMessage(String sender, String msg) {
        String message = sender + ": " + msg;
        System.out.println(message);
        System.out.println("Message created");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println(i);
            clients.get(i).sendMessage(message);
            System.out.println("Message sent");
        }
    }
}