import com.mongodb.client.*;
import model.database.DB;
import model.database.Session;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.data.Message;
import model.networking.server.Server;
import org.bson.Document;

public class Main {

    public static void main(String[] args) {

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
                //Session.logOff(username);

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
