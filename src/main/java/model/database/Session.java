package model.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import controller.Controller;
import org.bson.Document;
import org.bson.types.ObjectId;
import view.gui.WindowChatting;
import view.gui.WindowLogin;
import view.gui.WindowChatting;

import javax.swing.*;
import java.util.Date;

public class Session {

    static MongoCollection<Document> sessionCollection;
    private static boolean loggedIn = false;

    public Session() {

    }

    public static boolean sessionStillValid(int date2) {
        Date date = new Date();
        Long x = date.getTime();

        if (x.intValue() < (date2 + 600000)) {
            return true;
        } else {
            return false;
        }
    }

    // initiate a new session
    public static void sessionInit(String username, String IP) {
        // make query to find the user
        setCollection();

        Document query = new Document("username", username);
        FindIterable<Document> findIterable = sessionCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if session with user already exists
            endSession(username);
        } else {
            addSession(username, IP);
        }
    }

    // gets the timestamp of the session
    public static int getSessionTime(String username) {
        // make query to find the user
        Document query = new Document("username", username);
        FindIterable<Document> findIterable = sessionCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the session id as a string
            String id = cursor.next().get("_id").toString();

            return new ObjectId(id).getTimestamp();
        }
        return 0;
    }

    // set the mongoDB collection
    public static void setCollection() {
        try {
            sessionCollection = DB.getSessionCollection();
        } catch(Exception e) {
            WindowChatting.displayErrorDialog("Database Error: setCollection");
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
            WindowChatting.displayErrorDialog("Database Error: addSession");
            System.out.println("error adding session to database");
        }
    }

    // start a timer for the session
    public static void startSessionTimer(String username) {
        int time = 600; // time in seconds
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
            WindowChatting.displayErrorDialog("Database Error: endSession");
            System.out.println("error deleting session from database");
        }
    }

    // method to return the session id from the database as a string
    public static String getSessionIP(String username) {
        // make query to find the user
        Document query = new Document("username", username);
        FindIterable<Document> findIterable = sessionCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the session id as a string
            try {
                return cursor.next().get("ip").toString();
            } catch(Exception e) {
                System.out.println("error getting session id");
                WindowChatting.displayErrorDialog("Error connecting to user");
                return null;
            }
        } else return null;
    }

    // method to return the username from the session database as a string
    public static String getSessionUser(String ip) {
        // make query to find the user
        Document query = new Document("ip", ip);
        FindIterable<Document> findIterable = sessionCollection.find(query);
        MongoCursor<Document> cursor = findIterable.cursor();
        if (cursor.hasNext()){
            // if it finds the correct user returns the username as a string
            try {
                return cursor.next().get("username").toString();
            } catch(Exception e) {
                WindowChatting.displayErrorDialog("Error connecting to ip");
                return null;
            }
        } else return null;
    }

    // method to check if a user is currently logged in
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void logout(String username){
        endSession(username);
    }

    public static void restart(String username, String ipAddress) {
        if(sessionStillValid(getSessionTime(username))){
            endSession(username);
            sessionInit(username, ipAddress);
        } else {
            Controller.LogOut(username);
        }
    }
}