package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControllerClient {

    @FXML
    TextArea inputFromServer;
    TextArea msg;
    TextField name;


    Socket socket;
    BufferedReader in;


    //Receive MSG object send String with msg and name back.

    public void initialize(){
        try {
            System.out.println("CONNECT?");
            socket = new Socket("localhost",1);
            System.out.println("connect");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            setInputFromServer();
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

    public Message writeMessage(){
        Message message = new Message();
        message.setName(name.toString());
        message.setMsg(msg.toString());
        return message;
    }
}
