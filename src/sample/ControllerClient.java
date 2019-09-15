package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
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
    public DataInputStream in;
    public ObjectOutputStream out;


    //Receive MSG object send String with msg and name back.

    public void initialize(){
        try {
            System.out.println("CONNECT?");
            socket = new Socket("localhost",1);
            System.out.println("connect");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new DataInputStream((socket.getInputStream()));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(runnable).start();

    }


    public void setInputFromServer() throws Exception {
        try {
            String msg = in.readUTF();
            Message d = new Message();
            System.out.println(msg);
            //Decrypt incoming message
            String decrypted = d.decrypt(msg);
            inputFromServer.appendText(decrypted + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//Actually sends message object to server, and uses the writeMessage to create the message that is send.
    public void sendMessage() throws Exception {
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
                System.out.println("Waiting for message");
                try {
                    setInputFromServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("No longer waiting");
            }
        }
    };

    public Message writeMessage() throws Exception {
        Message message = new Message();
        message.setName(name.getText());
        System.out.println(name.getText());
        //Encrypting the message the user want to send
        message.setMsg(message.encrypt(msg.getText()));
        return message;
    }
}
