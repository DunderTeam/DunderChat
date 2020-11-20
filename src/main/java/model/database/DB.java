package model.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.internal.bulk.DeleteRequest;
import org.bson.Document;
import view.gui.WindowChatting;
import view.gui.WindowLogin;

public class DB {

    // connect to mongodb
    private static final String connection = "mongodb+srv://dunderuser:JNKwmvZ56SPAGMQi@dunderteam.v2fut.mongodb.net/test?retryWrites=true&w=majority";

    public MongoDatabase getConnection() {
        cfg x = new cfg();
        String conn = String.format("mongodb+srv://dunderadmin:%s@dunderteam.v2fut.mongodb.net/<dbname>?retryWrites=true&w=majority", x.adminPassword);
        ConnectionString connString = new ConnectionString(conn);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("test");
    }

    // connect to mongodb, returns a MongoClient
    public static MongoClient connect() {
        try {
            return MongoClients.create(connection);
        } catch(Exception e) {
            // TODO show error connecting to database on screen
            System.out.println("error connecting to database");
            return null;
        }
    }

    // select the user collection, returns a MongoCollection
    public static MongoCollection<Document> getUserCollection() {
        try {
            MongoClient mongoClient = connect();
            assert mongoClient != null;
            return mongoClient.getDatabase("App").getCollection("users");
        } catch (Exception e) {
            // TODO show database error on screen
            System.out.println("error getting collection");
            return null;
        }
    }

    // select the session collection, returns a MongoCollection
    public static MongoCollection<Document> getSessionCollection() {
        try {
            MongoClient mongoClient = connect();
            assert mongoClient != null;
            return mongoClient.getDatabase("App").getCollection("sessions");
        } catch (Exception e) {
            // TODO show database error on screen
            System.out.println("error getting collection");
            return null;
        }
    }

    // adding a new user to database
    public static boolean addUser(MongoCollection<Document> userCollection, String name, String ip, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // check if user with given name exists
        Document query = new Document("username", name);
        FindIterable<Document> findIterable = userCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if username is taken
            WindowLogin.displayErrorDialog("Name `" + name + "` is already taken");
            return false;
        } else if(!checkPassword(password)) {
            // if password is invalid
            // TODO show invalid password
            System.out.println("Password not valid");
            return false;
        } else {
            // add user to the user collection
            try {
                Document doc = new Document("username", name).append("ip", ip).append("password", encryptedPassword);
                userCollection.insertOne(doc);
                WindowLogin.setNewRegisteredUser(name);
                return true;
            } catch (Exception e) {
                WindowLogin.displayErrorDialog("Failed to register: Database error");
                System.out.println("error adding user to database");
                return false;
            }
        }
    }

    // method to log in
    public static void login(MongoCollection<Document> userCollection, String username, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // check if there is a user with the given username and password
        Document query = new Document("username", username).append("password", encryptedPassword);
        FindIterable<Document> findIterable = userCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if login is successful
            try {
                String name = getUsername(userCollection, username, password);
                String ip = getIP(userCollection, username, password);
                System.out.println("Logged in as " + name + ". IP: " + ip);
                Session.sessionInit(name, ip);
                WindowLogin.setLoggedInUserName(name);

            } catch(Exception e) {
                // TODO show login error on screen
                System.out.println("login failed");
                WindowLogin.displayErrorDialog("Login failed: Database error");
            }
        } else {
            // TODO show login error on screen
            System.out.println("login failed");
            WindowLogin.displayErrorDialog("Login failed: Database error");

        }
    }

    // method to return the username from the database as a string
    public static String getUsername(MongoCollection<Document> userCollection, String username, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // make query to find the user
        Document query = new Document("username", username).append("password", encryptedPassword);
        FindIterable<Document> findIterable = userCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the username as a string
            try {
                return cursor.next().get("username").toString();
            } catch(Exception e) {
                // TODO show database error on screen
                System.out.println("error getting username");
                return null;
            }
        } else {
            // TODO show database error on screen
            System.out.println("error getting username");
            return null;
        }
    }

    // method to return the ip from the database as a string
    public static String getIP(MongoCollection<Document> userCollection, String username, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // make query to find the user
        Document query = new Document("username", username).append("password", encryptedPassword);
        FindIterable<Document> findIterable = userCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the ip as a string
            try {
                return cursor.next().get("ip").toString();
            } catch(Exception e) {
                // TODO show database error on screen
                System.out.println("error getting ip");
                return null;
            }
        } else {
            // TODO show database error on screen
            System.out.println("error getting ip");
            return null;
        }
    }

    // delete a user from database
    public static void deleteUser(MongoCollection<Document> userCollection, String username, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // find user with the given username and password
        Document query = new Document("username", username).append("password", encryptedPassword);
        // delete user from database
        try {
            //TODO fix error handling, currently deleteOne never throws an error
            DeleteResult result = userCollection.deleteOne(query);
            System.out.println(result.getDeletedCount());
            System.out.println(username + " has been deleted");
            WindowChatting.setUserDeleted("Yep");
        } catch(MongoException e) {
            WindowChatting.displayErrorDialog("Wrong password, could not delete user");
            System.out.println("error deleting user");
        }
    }

    // change password for a user
    public static void changePassword(MongoCollection<Document> userCollection, String username, String password, String newPassword) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        String encryptedNewPassword = Encryption.encryptPassword(newPassword);
        // checks if the password is strong enough and is not the same as the old one
        if(!checkPassword(newPassword) || newPassword.equals(password)){
            WindowChatting.displayErrorDialog("New password is invalid");
            System.out.println("New password not valid");
        }else{
            // find user with the given username and password
            Document query = new Document("username", username).append("password", encryptedPassword);
            // change password
            if (!(userCollection.findOneAndUpdate(query, Updates.set("password", encryptedNewPassword)) == null)) {
                userCollection.findOneAndUpdate(query, Updates.set("password", encryptedNewPassword));
                System.out.println("Changed password for " + username);
                WindowChatting.setNewPwd(newPassword);
            } else {
                WindowChatting.displayErrorDialog("Error changing passowrd");
                System.out.println("error changing password");
            }
        }
    }

    // change username for a user
    public static void changeUsername(MongoCollection<Document> userCollection, String username, String password, String newUsername) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // find user with the given username and password
        Document query = new Document("username", username).append("password", encryptedPassword);
        if (!(userCollection.findOneAndUpdate(query, Updates.set("username", newUsername)) == null)) {
            userCollection.findOneAndUpdate(query, Updates.set("username", newUsername));
            System.out.println("Changed username from " + username + " to " + newUsername);
            WindowChatting.setNewUser(newUsername);
        } else {
            WindowChatting.displayErrorDialog("Could not change username: DB error");
            System.out.println("error changing username");

        }
    }

    // checking if password is strong enough
    private static boolean checkPassword(String password) {
        /*
         * needs at least one digit
         * needs at least one lower case letter
         * needs at least one upper case letter
         * no whitespace allowed
         * needs at least 8 characters
         */
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

        return password.matches(pattern);
    }


}
