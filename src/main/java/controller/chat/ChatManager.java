package controller.chat;

import controller.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {


    List<Chat> chatManager = new ArrayList<Chat>(); // lists of Chats

    public void addChatToList (Chat ch){ // adds chat to list


        if (!chatManager.contains(ch.getChat())){
            chatManager.add(ch);
        }



    }

    public List<Chat> getChatManager() { // returns list of chats
        return chatManager;
    }

    public Chat getChatById(int id){ // Gets chat from list of chats by id
        /*
        Chat temp = null;
        for (int i = 0; i < chatManager.size(); i++ ){
            if (value1 == id){
                temp = value2;
            }
        }
        return temp;

         */
    }

    public void deleteChatById(int id){ // deletes chat from list by id
       /*
        for (int i = 0; i < chatManager.size(); i++){
            if(value1 == id){
                chatManager.remove(i);
            }
        }

        */
    }
}
