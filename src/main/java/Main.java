import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;
import model.networking.server.public_ip;


/*
    Tasks:
     - Start Server
     - Start Application

 */
public class Main {
    public static void main(String args[]) {
        /* This Starts our Server/Receiver */
        Thread server = new Thread(new Server(25));
        server.start();
        /* Server */

        /* Start Application */
        ConnectionManager manager = new ConnectionManager(); // Consider making Singleton

        /* Application */

        /* Test Environment for running code snippets  */
        // Add a new connection to our manager
        manager.addConnection(new Connection("localhost", 25));
        Message msg = new Message();

        // Attach data to a new message
        msg.setData("Hello World!");
        msg.setName("John Wick");

        // Send a message via our manager to a specific client
        manager.sendMessage(manager.connections.get(0), msg); // Sends message to own device. e.g localhost

         /* Test Environment */
    }
}
