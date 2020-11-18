import com.dosse.upnp.UPnP;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;
import view.gui.WindowLogin;


/*
    Tasks:
     - Start Server
     - Start Application

 */
public class Main {
    public static void main(String args[]) {

        /* This Starts our Server/Receiver */
        Thread server = new Thread(new Server(5555));
        server.start();
        /* Server */

        /* Start Application */
        ConnectionManager manager = new ConnectionManager(); // Consider making Singleton

        /* Application */

        /* Test Environment for running code snippets  */
        // Add a new connection to our manager

        manager.addConnection(new Connection("84.211.225.160", 5555)); // Public
        manager.addConnection(new Connection("192.168.0.2", 5555)); // External
        manager.addConnection(new Connection(UPnP.getLocalIP(), 5555));
        System.out.println(UPnP.getLocalIP());

        Message msg = new Message();
        Message msg1 = new Message();
        Message msg2 = new Message();

        // Attach data to a new message
        msg.setData("Hello World!");
        msg.setName("John Wick");

        msg1.setData("Message 1");
        msg1.setName("Sender 1");

        msg2.setData("Message 2");
        msg2.setName("Sender 2");

        // Send a message via our manager to a specific client
        manager.sendMessage(manager.connections.get(0), msg); // Sends message to own device. e.g localhost
        manager.sendMessage(manager.connections.get(1), msg1); // Sends message to own device. e.g localhost
        manager.sendMessage(manager.connections.get(2), msg2); // Sends message to own device. e.g localhost

        // Start view (gui)
        java.awt.EventQueue.invokeLater(() -> new WindowLogin().setVisible(true));

         /* Test Environment */
    }
}
