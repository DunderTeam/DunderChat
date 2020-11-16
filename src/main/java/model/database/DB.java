package model.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

public class DB {

    // connect to mongodb
    private static final String connection = "mongodb+srv://dunderuser:JNKwmvZ56SPAGMQi@dunderteam.v2fut.mongodb.net/test?retryWrites=true&w=majority";
    private static final MongoClient mongoClient = MongoClients.create(connection);

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

    public static MongoClient connect() {

        return mongoClient;
    }

    public static MongoCollection<Document> getUserCollection() {
        // select the user collection

        return mongoClient.getDatabase("App").getCollection("users");
    }

    public static void addUser(MongoCollection<Document> user, String name, String password) {
        // add user to the user collection
        Document doc = new Document("username", name).append("password", password);
        user.insertOne(doc);
    }

    public static void login(MongoCollection<Document> userCollection, String username, String encryptedPassword) {
        // find a user in the database
        Document query = new Document("username", username).append("password", encryptedPassword);
        FindIterable<Document> findIterable = userCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            System.out.println(cursor.next());
        } else {
            System.out.println("No user found");
        }
    }

    public static void deleteUser(MongoCollection<Document> userCollection, String username, String encryptedPassword) {
        // delete user from database
        Document query = new Document("username", username).append("password", encryptedPassword);
        userCollection.deleteOne(query);
    }

}
