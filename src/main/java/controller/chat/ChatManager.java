package controller.chat;

import controller.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    int value1;
    Chat value2;


    ChatManager (int nr, Chat ch){
        value1 = nr;
        value2 = ch;
    }

    List<ChatManager> chatManager = new ArrayList<ChatManager>(); // lists of Chats

    public void addChatToList (int nr,Chat ch){ // adds chat to list
        ChatManager temp = new ChatManager(nr, ch);
        chatManager.add(temp);
    }

    public List<ChatManager> getChatManager() { // returns list of chats
        return chatManager;
    }

    public Chat getChatById(int id){ // Gets chat from list of chats by id
        Chat temp = null;
        for (int i = 0; i < chatManager.size(); i++ ){
            if (value1 == id){
                temp = value2;
            }
        }
        return temp;
    }

    public void deleteChatById(int id){ // deletes chat from list by id
        for (int i = 0; i < chatManager.size(); i++){
            if(value1 == id){
                chatManager.remove(i);
            }
        }
    }
}
