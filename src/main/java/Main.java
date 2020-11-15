import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;

public class Main {
    public static void main(String args[]) {
        Thread server = new Thread(new Server(25));
        server.start();

        ConnectionManager manager = new ConnectionManager();
        manager.addConnection(new Connection("localhost", 25));
        Message msg = new Message();

        msg.setData("Hello World!");
        msg.setName("John Wick");

        manager.sendMessage(manager.connections.get(0), msg);
    }
}
