package model.networking.client.message;

import com.google.gson.Gson;
import model.networking.client.Connection;
import model.networking.data.Message;

public class SendMessage {
    private MessageStrategy strategy;
    public SendMessage(Connection receiver, Message msg) {
        strategy = new SendMessageSocket(receiver);
        msg.setDestination(receiver.ip);

        strategy = new SendMessagePOST();

        strategy.send(msg);
    }
}
