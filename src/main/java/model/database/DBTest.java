package model.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DBTest {

    public static void testDB(MongoCollection<Document> userCollection) {
        // variables used for testing input to database before buttons are added/simulating registering new users
        String username = "bruker2222";
        String ip = "123";
        String password = "Abcd1234";
        String newPassword = "aBcd1234";
        String newUsername = "bruker11";

        /* password requirements
         * needs at least one digit
         * needs at least one lower case letter
         * needs at least one upper case letter
         * no whitespace allowed
         * needs at least 8 characters
         */

        // add new user to data base
        if(DB.addUser(userCollection, username, ip, password)) {
            // user has been added
            System.out.println("User has been added");
        } else {
            // username taken
            System.out.println("Username already exists");
        }

        // when login button pressed
        try {
            DB.login(userCollection, username, password);
            String IPAddress = DB.getIP(userCollection, username, password);
            Session.sessionInit(username, IPAddress);
        } catch (Exception e){
            System.out.println("login failed");
            Session.endSession(username);
        }

        // test db methods
        if(Session.isLoggedIn()) {
            // delete user
            //DB.deleteUser(userCollection, username, password);
            //Session.endSession(username);

            // change password
            //DB.changePassword(userCollection, username, password, newPassword);
            //Session.restart(username, IPAddress);

            // change username
            //DB.changeUsername(userCollection, username, password, newUsername);

            // testing getSessionUser
            //System.out.println(Session.getSessionUser(username));

            // testing getSession id
            //System.out.println(Session.getSessionId(username));

            System.out.println(Session.getSessionIP(username));

        } else {
            System.out.println("Not logged in");
        }

    }

}
