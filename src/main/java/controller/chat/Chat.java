package controller.chat;

import controller.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    List<Message> chat = new ArrayList<Message>(); // Creating list of all messages between two people

    public void addMessageToList (Message mes) { // Adds new message to list
        chat.add(mes);
    }

    public List<Message> getChat() { // returns list
        return chat;
    }
}
