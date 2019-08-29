package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControllerClient {

    @FXML
    TextArea inputFromServer;
    @FXML
    TextArea msg;
    @FXML
    TextField name;


    Socket socket;
    BufferedReader in;
    ObjectOutputStream out;


    //Receive MSG object send String with msg and name back.

    public void initialize(){
        try {
            System.out.println("CONNECT?");
            socket = new Socket("localhost",1);
            System.out.println("connect");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                setInputFromServer();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.run();
    }
    public void setInputFromServer() {
        try {
            inputFromServer.appendText(in.readLine() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        Message message = writeMessage();
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message writeMessage(){
        Message message = new Message();
        message.setName(name.toString());
        message.setMsg(msg.toString());
        return message;
    }
}
