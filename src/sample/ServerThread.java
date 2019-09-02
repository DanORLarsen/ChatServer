package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.ControllerServer.clients;

public class ServerThread extends Thread {
    protected Socket socket;
    ServerSocket serverSocket;

    ObjectInputStream in;

    //Server waits for Message object from client, when it gets it. it
    public void run() {

        try {
            serverSocket = new ServerSocket(1);
            socket = serverSocket.accept();
            new Thread(runnable).start();

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            clients.add(new ClientConnected(dataOutputStream, in, socket));
            while (true) {
                Message message = (Message) in.readObject();
                String msg = message.getName() + ": " + message.getMsg();
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                try {
                    in = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataOutputStream dataOutputStream = null;
                try {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clients.add(new ClientConnected(dataOutputStream, in, socket));
                Message message = null;
                while (true) {
                    try {
                        message = (Message) in.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String msg = message.getName() + ": " + message.getMsg();
                    System.out.println(msg);
                    System.out.println("Message created");
                    for (int i = 0; i < clients.size(); i++) {
                        clients.get(i).sendMessage(msg);
                        System.out.println("Message sent");
                    }
                }
            }

    };


}