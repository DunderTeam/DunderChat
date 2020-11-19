package model.networking.client.message;

import model.networking.client.Connection;
import model.networking.data.Message;

public class SendMessage {
    private MessageStrategy strategy;
    public SendMessage(Connection receiver, Message msg) {
        strategy = new SendMessageSocket(receiver);

        strategy.send(msg);
    }
}
