package model.networking.client.message;

<<<<<<< HEAD
import com.google.gson.Gson;
=======
>>>>>>> f97b44d... Implemented strategy pattern for messages - with socket
import model.networking.client.Connection;
import model.networking.data.Message;

public class SendMessage {
    private MessageStrategy strategy;
    public SendMessage(Connection receiver, Message msg) {
        strategy = new SendMessageSocket(receiver);
<<<<<<< HEAD
        msg.setDestination(receiver.ip);

        strategy = new SendMessagePOST();

=======

>>>>>>> f97b44d... Implemented strategy pattern for messages - with socket
        strategy.send(msg);
    }
}
