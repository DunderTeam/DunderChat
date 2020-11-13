import controller.networking.client.Connection;
import controller.networking.server.Server;

public class Main {
    public static void main(String args[]) {
        Thread server = new Thread(new Server());
        server.start();

        Thread client = new Thread(new Connection("localhost", 6666));
        client.start();
        client = new Thread(new Connection("localhost", 6666));
        client.start();

        client = new Thread(new Connection("localhost", 6666));
        client.start();

    }
}
