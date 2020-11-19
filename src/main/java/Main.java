
import com.dosse.upnp.UPnP;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;
import view.gui.WindowLogin;

import com.mongodb.ClientSessionOptions;
import com.mongodb.ServerAddress;
import com.mongodb.TransactionOptions;

import com.mongodb.client.*;
import model.database.DB;
import model.database.Session;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;
import org.bson.Document;



/*
    Tasks:
     - Start Server
     - Start Application

 */
public class Main {

    public static void main(String[] args) {

        // get user collection from mongodb
        MongoCollection<Document> userCollection = DB.getUserCollection();


        /* This Starts our Server/Receiver */
        Thread server = new Thread(new Server(5555));

        /*

        Thread server = new Thread(new Server(25));
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
        //manager.sendMessage(manager.connections.get(0), msg); // Sends message to own device. e.g localhost
        //manager.sendMessage(manager.connections.get(1), msg1); // Sends message to own device. e.g localhost
        //manager.sendMessage(manager.connections.get(2), msg2); // Sends message to own device. e.g localhost

        // Start view (gui)
        java.awt.EventQueue.invokeLater(() -> new WindowLogin().setVisible(true));

         /* Test Environment */

        manager.sendMessage(manager.connections.get(0), msg);


        // variables used for testing input to database before buttons are added/simulating registering new users
        String username = "bruker1";
        String ip = "123";
        String password = "123456";
        String newPassword = "password";
        String newUsername = "bruker11";

        // add new user to data base
        if(DB.addUser(userCollection, username, ip, password)) {
            // user has been added
            System.out.println("User has been added");
        } else {
            // username taken
            System.out.println("Username already exists");
        }

        // login
        if(DB.login(userCollection, username, password)) {
            String IPAddress = DB.getIP(userCollection, username, password);
            Session.sessionInit(username, IPAddress);

            // delete user
            if(Session.isLoggedIn()) {
                //DB.deleteUser(userCollection, username, password);
                //Session.endSession(username);
            }

            // change password
            if(Session.isLoggedIn()) {
                //DB.changePassword(userCollection, username, password, newPassword);
                //Session.restart(username, IPAddress);
            }

            // change username
            if(Session.isLoggedIn()) {
                //DB.changeUsername(userCollection, username, password, newUsername);
            }

        }

    }
}
