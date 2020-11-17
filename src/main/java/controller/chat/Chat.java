package controller.chat;

import model.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    public Chat (String name, String address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    public void addMessageToList (Message me) { // Adds new message to list
        listMessages.add(me);
    }

    public List<Message> getListMessages() { // returns list
        return listMessages;
    }

    private String name;
    private String address;
    private int port;

    private List<Message> listMessages = new ArrayList<Message>(); // Creating list of all messages between two people
}
