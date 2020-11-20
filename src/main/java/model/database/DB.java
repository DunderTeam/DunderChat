package model.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
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
            WindowLogin.displayErrorDialog("Password is invalid!");
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
                Session.sessionInit(name, ip);
                WindowLogin.setLoggedInUserName(name);

            } catch(Exception e) {
                WindowLogin.displayErrorDialog("Login failed: Database error");
            }
        } else {
            WindowLogin.displayErrorDialog("Login failed: Wrong password or user");

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
                System.out.println("error getting username");
                return null;
            }
        } else {
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
                System.out.println("error getting ip");
                return null;
            }
        } else {
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
        DeleteResult result = userCollection.deleteOne(query);
        // if getDeletedCount is 1, that means we actually deleted something
        if(result.getDeletedCount() == 1) {
            WindowChatting.setUserDeleted("username");
        } else {
            WindowChatting.displayErrorDialog("Wrong password, could not delete user");
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
        }else{
            // find user with the given username and password
            Document query = new Document("username", username).append("password", encryptedPassword);
            // change password
            if (!(userCollection.findOneAndUpdate(query, Updates.set("password", encryptedNewPassword)) == null)) {
                userCollection.findOneAndUpdate(query, Updates.set("password", encryptedNewPassword));
                WindowChatting.setNewPwd(newPassword);
            } else {
                WindowChatting.displayErrorDialog("Error changing passowrd");
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
            WindowChatting.setNewUser(newUsername);
        } else {
            WindowChatting.displayErrorDialog("Could not change username: DB error");
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
