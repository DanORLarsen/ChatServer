import sample.Message;

public class main {
    public static void main(String[] args) throws Exception {
        Message message = new Message();
       String hey = message.encrypt("Mathias");
        System.out.println(hey);
        String yeh = message.decrypt(hey);
        System.out.println(yeh);
    }
}
