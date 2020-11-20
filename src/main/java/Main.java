import com.dosse.upnp.UPnP;
import com.mongodb.client.MongoCollection;
import model.database.DB;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.client.message.SendMessagePOST;
import model.networking.data.Message;
import model.networking.server.PublicIP;
import model.networking.server.Receiver;
import model.networking.server.SocketListener;
import org.bson.Document;
import view.gui.WindowLogin;



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
        Receiver server_service = new Receiver();

        server_service.start();

        /*

        Thread server = new Thread(new Server(25));
        server.start();
        /* Server */

        /* Start Application */
        ConnectionManager manager = new ConnectionManager(); // Consider making Singleton

        /* Application */

        /* Test Environment for running code snippets  */

        // Start view (gui)
        java.awt.EventQueue.invokeLater(() -> new WindowLogin().setVisible(true));

         /* Test Environment */

        //DBTest.testDB(userCollection);
    }
}
