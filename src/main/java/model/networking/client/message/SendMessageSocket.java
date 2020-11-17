package model.networking.client.message;

import model.networking.data.IP;
import model.networking.data.Message;

public class SendMessageSocket implements MessageStrategy {

    private String destination;

    public void send(Message msg) {
        // Todo: Refactor code from SendMessage and implement here
        this.destination = msg.getDestination();
        String json_Message = Message.encode(msg);

    }
}
