package model.networking.client;

import model.networking.client.message.MessageStrategy;
import model.networking.data.Message;

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
    public void sendMessage(List<Connection> receivers, Message msg, MessageStrategy strategy) {
        /*
            Sending a message results in a new process/thread being created,
            there could be an issue with too many threads being opened in a short
            time. Although, in our app we wont be handling that many requests
            so we wont do anything about that. Each message thread will however
            have a short lifespan.
         */
        for(Connection receiver : receivers)
        {
            msg.setDestination(receiver.ip);

            strategy.send(msg);
        }
    }

    // Sends message to single receiver
    public void sendMessage(Connection receiver, Message msg, MessageStrategy strategy) {
        msg.setDestination(receiver.ip);

        strategy.send(msg);
    }
}
