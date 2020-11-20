package model.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.swing.*;

public class Session {

    static MongoCollection<Document> sessionCollection;
    private static boolean loggedIn = false;

    public Session() {

    }

    // initiate a new session
    public static void sessionInit(String username, String IP) {
        try {
            setCollection();
            addSession(username, IP);
            startSessionTimer(username);
        } catch(Exception e) {
            // TODO show error initiating session
            System.out.println("error initiating session");
        }
    }

    // set the mongoDB collection
    public static void setCollection() {
        try {
            sessionCollection = DB.getSessionCollection();
        } catch(Exception e) {
            // TODO show error getting the session collection
            System.out.println("error getting the session collection");
        }
    }

    // add new session to database
    public static void addSession(String name, String ip) {
        Document doc = new Document("username", name).append("ip", ip);
        try {
            sessionCollection.insertOne(doc);
            loggedIn = true;
            System.out.println("Session initiated");
        } catch(Exception e) {
            // TODO show database error
            System.out.println("error adding session to database");
        }
    }

    // start a timer for the session
    public static void startSessionTimer(String username) {
        int time = 60; // time in seconds
        Timer timer = new Timer(time*1000, arg0 -> {
            // code executed
            endSession(username);
            System.out.println("Session ended");
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // start timer
    }

    // log out / delete session
    public static void endSession(String username){
        // find user with the given username and password
        Document query = new Document("username", username);
        // delete user from database
        try {
            sessionCollection.deleteOne(query);
            loggedIn = false;
        } catch(Exception e) {
            // TODO show database error
            System.out.println("error deleting session from database");
        }
    }

    // method to return the session id from the database as a string
    public static String getSessionId(String username) {
        // make query to find the user
        Document query = new Document("username", username);
        FindIterable<Document> findIterable = sessionCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the session id as a string
            try {
                return cursor.next().get("_id").toString();
            } catch(Exception e) {
                // TODO show database error
                System.out.println("error getting session id");
                return null;
            }
        } else return null;
    }

    // method to return the username from the session database as a string
    public static String getSessionUser(String username) {
        // make query to find the user
        Document query = new Document("username", username);
        FindIterable<Document> findIterable = sessionCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the username as a string
            try {
                return cursor.next().get("username").toString();
            } catch(Exception e) {
                // TODO show database error
                System.out.println("error getting session id");
                return null;
            }
        } else return null;
    }

    // method to check if a user is currently logged in
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void restart(String username, String ipAddress) {
        endSession(username);
        sessionInit(username, ipAddress);
    }
}