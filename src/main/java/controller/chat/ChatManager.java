package controller.chat;


import model.networking.data.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {


    static List<Chat> chatList = new ArrayList<Chat>(); // lists of Chats

    public static void addMessage(java.net.SocketAddress so, Message ma){ // adds chat to list
        
        boolean check = chatExist(so,ma);

        if (!check){
            chatList.add(new Chat(ma,so)); // Ads new chat
            chatList.get(chatList.size()-1).addMessageToList(ma); // ads new message to chat
        }

    }
    
    private static boolean chatExist(java.net.SocketAddress so, Message ma){ // check if chat exists

        for (Chat ch: chatList) {
            if (ch.name == ma.getName() && ch.socket == so){ // check for name and socket connection
                ch.addMessageToList(ma); // ads new message to this chat
                return true;
            }
        }
        
        return false;
    }

    public List<Chat> getChatManager() { // returns list of chats
        return chatList;
    }

    public static List<Message> getChatById(Message ma, java.net.SocketAddress so){ // Gets chat from list of chats by id
        List<Message> temp = null;
        for (Chat ch: chatList){
            temp = ch.getChat(); // returns list of messages
        }
        return temp;
    }

    public void deleteChatById(Message ma, java.net.SocketAddress so){ // deletes chat from list by id
        for (int i = 0; i < chatList.size(); i++){
            if (chatList.get(i).name == ma.getName() && chatList.get(i).socket == so ){ // check for chat by id
                chatList.remove(i); // deletes chat from list
            }
        }
    }
}
