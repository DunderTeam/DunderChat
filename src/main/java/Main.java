import com.mongodb.ClientSessionOptions;
import com.mongodb.ServerAddress;
import com.mongodb.TransactionOptions;
import com.mongodb.client.*;
import com.mongodb.session.ServerSession;
import model.database.DB;
import org.bson.BsonDocument;
import org.bson.BsonTimestamp;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {

        // get user collection from mongodb
        MongoCollection<Document> userCollection = DB.getUserCollection();

        /*
        Thread server = new Thread(new Server(25));
        server.start();

        ConnectionManager manager = new ConnectionManager();
        manager.addConnection(new Connection("localhost", 25));
        Message msg = new Message();

        msg.setData("Hello World!");
        msg.setName("John Wick");

        manager.sendMessage(manager.connections.get(0), msg);
         */

        // variables used for testing input to database before buttons are added/simulating registering new users
        String username = "bruker1";
        String ip = "123";
        String password = "123456";
        String newPassword = "password";

        // add new user to data base
        //DB.addUser(userCollection, username, ip, password);

        // login
        DB.login(userCollection, username, password);

        // delete user
        //DB.deleteUser(userCollection, username, password);

        // change password
        //DB.changePassword(userCollection, username, password, newPassword);

        
    }
}
