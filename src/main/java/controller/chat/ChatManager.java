package controller.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

List<Chat> chatManager = new ArrayList<Chat>();

    public void addChatToList (Chat ch){ // adds chat to list
        chatManager.add(ch);
    }

    public List<Chat> getChatManager() { // returns list og chats
        return chatManager;
    }
}
