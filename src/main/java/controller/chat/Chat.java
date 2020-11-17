package controller.chat;

import model.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    public static String name;
    public static java.net.SocketAddress socket;

    private List<Message> chat = new ArrayList<Message>(); // Creating list of all messages between two people

    Chat (Message ma, java.net.SocketAddress so){
        name = ma.getName();
        socket = so;
    }

    public void addMessageToList (Message me) { // Adds new message to list
        chat.add(me);
    }

    public List<Message> getChat() { // returns list
        return chat;
    }
}
