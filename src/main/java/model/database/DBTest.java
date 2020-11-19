package model.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DBTest {

    public static void testDB(MongoCollection<Document> userCollection) {
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
