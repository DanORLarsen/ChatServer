package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
   public TextArea inputFromServer;
    @FXML
    public TextArea msg;
    @FXML
   public TextField name;
    @FXML
    public Button btn;

    public Socket socket;
    public BufferedReader in;
    public ObjectOutputStream out;


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
        new Thread(runnable).start();

    }


    public void setInputFromServer() {
        try {
            inputFromServer.appendText(in.readLine() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//Actually sends message object to server, and uses the writeMessage to create the message that is send.
    public void sendMessage(){
        Message message = writeMessage();
        try {
            out.writeObject(message);
            out.flush();
            System.out.println("message  sent to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            while(true){
                setInputFromServer();
            }
        }
    };

    public Message writeMessage(){
        Message message = new Message();
        message.setName(name.getText());
        System.out.println(name.getText());
        message.setMsg(msg.getText());
        return message;
    }
}
