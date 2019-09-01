package sample;
import java.util.ArrayList;

public class ControllerServer {
   public static ArrayList<ClientConnected> sockets = new ArrayList<>();
    public void initialize(){
        new ServerThread().start();
    }
}
