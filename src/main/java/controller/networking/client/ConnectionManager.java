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
