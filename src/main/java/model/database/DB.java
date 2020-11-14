package model.database;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DB {

    public MongoDatabase getConnection() {
        cfg x = new cfg();
        String conn = String.format("mongodb+srv://dunderadmin:%s@dunderteam.v2fut.mongodb.net/<dbname>?retryWrites=true&w=majority", x.password);
        ConnectionString connString = new ConnectionString(conn);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("test");
    }

}
