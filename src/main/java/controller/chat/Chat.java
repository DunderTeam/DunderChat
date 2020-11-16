package controller.chat;

import controller.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    int value1;
    Message value2;

    Chat (int nr, Message me){
        value1 = nr;
        value2 = me;
    }

    List<Chat> chat = new ArrayList<Chat>(); // Creating list of all messages between two people

    public void addMessageToList (int nr, Message me) { // Adds new message to list

        Chat temp = new Chat(nr,me);
        chat.add(temp);


    }

    public List<Chat> getChat() { // returns list
        return chat;
    }
}
