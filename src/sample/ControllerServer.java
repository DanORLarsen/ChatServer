package sample;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerServer {
    ServerSocket serverSocket;
    Socket socket;

   public static ArrayList<Socket> sockets = new ArrayList<Socket>();
    public void initialize(){
        try {
            serverSocket = new ServerSocket(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ServerThread(serverSocket).start();
    }
}
