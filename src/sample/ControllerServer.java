package sample;
import java.util.ArrayList;

public class ControllerServer {
   public static ArrayList<ClientConnected> clients = new ArrayList<>();
    public void initialize(){
        new ServerThread().start();
    }
}
