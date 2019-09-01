package sample;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerServer {
   public static ArrayList<Socket> sockets = new ArrayList<Socket>();
    public void initialize(){
        new ServerThread().start();
    }
}
