package controller.networking.client;

import controller.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    public List<Connection> connections;

    public ConnectionManager() {
        connections = new ArrayList<Connection>();
    }

    public void addConnection(Connection conn) {
        connections.add(conn);
    }

    // Sends message to multiple receivers
    public void sendMessage(List<Connection> receivers, Message msg) {
        /*
            Sending a message results in a new process/thread being created,
            there could be an issue with too many threads being opened in a short
            time. Although, in our app we wont be handling that many requests
            so we wont do anything about that. Each message thread will however
            have a short lifespan.
         */
        for(Connection conn : receivers)
        {
            // Loops through each connection and sends a message
            Thread send = new Thread(new SendMessage(conn.getIp(), conn.getPort(), msg));
            send.start(); // Start transaction
        }
    }

    // Sends message to single receiver
    public void sendMessage(Connection receiver, Message msg) {
        Thread send = new Thread(new SendMessage(receiver.getIp(), receiver.getPort(), msg));
        send.start(); // Start transaction
    }
}
