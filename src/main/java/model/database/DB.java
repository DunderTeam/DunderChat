package model.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import org.bson.Document;

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
        return MongoClients.create(connection);
    }

    // select the user collection, returns a MongoCollection
    public static MongoCollection<Document> getUserCollection() {
        MongoClient mongoClient = connect();

        return mongoClient.getDatabase("App").getCollection("users");
    }

    // adding a new user to database
    public static void addUser(MongoCollection<Document> userCollection, String name, String ip, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // check if user with given name exists
        Document query = new Document("username", name);
        FindIterable<Document> findIterable = userCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if username is taken
            System.out.println("Username already exists");
        } else {
            // add user to the user collection
            Document doc = new Document("username", name).append("ip", ip).append("password", encryptedPassword);
            userCollection.insertOne(doc);
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
            String name = getUsername(userCollection, username, password);
            String ip = getIP(userCollection, username, password);
            System.out.println("Logged in as " + name + ". IP: " + ip);
        } else {
            // if login failed
            System.out.println("Login Failed");
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
            return cursor.next().get("username").toString();
        } else return null;
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
            return cursor.next().get("ip").toString();
        } else return null;
    }

    public static void deleteUser(MongoCollection<Document> userCollection, String username, String password) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        // find user with the given username and password
        Document query = new Document("username", username).append("password", encryptedPassword);
        // delete user from database
        userCollection.deleteOne(query);
        System.out.println(username + " has been deleted");
    }

    public static void changePassword(MongoCollection<Document> userCollection, String username, String password, String newPassword) {
        // encrypt the password using simple hash
        String encryptedPassword = Encryption.encryptPassword(password);
        String encryptedNewPassword = Encryption.encryptPassword(newPassword);
        // change password
        Document query = new Document("username", username).append("password", encryptedPassword);
        userCollection.findOneAndUpdate(query, Updates.set("password", encryptedNewPassword));
        System.out.println("Changed password for " + username);
    }

}
