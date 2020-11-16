import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import model.database.DB;
import model.database.Encryption;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;
import org.bson.Document;

public class Main {
    public static void main(String args[]) {

        // connect to mongodb, get a client from the method
        MongoClient mongoClient = DB.connect();
        // get user collection from mongodb
        MongoCollection<Document> userCollection = DB.getUserCollection();


        Thread server = new Thread(new Server(25));
        server.start();

        ConnectionManager manager = new ConnectionManager();
        manager.addConnection(new Connection("localhost", 25));
        Message msg = new Message();

        msg.setData("Hello World!");
        msg.setName("John Wick");

        manager.sendMessage(manager.connections.get(0), msg);

        // variables used for testing input to database before buttons are added/simulating registering new users
        String username = "bruker4";
        String password = "qwerty";

        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);

        // add new user to data base
        //DB.addUser(userCollection, username, encryptedPassword);

        // login
        DB.login(userCollection, username, encryptedPassword);

        // delete user
        //DB.deleteUser(userCollection, username, encryptedPassword);

    }
}
