import controller.networking.client.Connection;
import controller.networking.client.ConnectionManager;
import controller.networking.data.Message;
import controller.networking.server.Server;

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
