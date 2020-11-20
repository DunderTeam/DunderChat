package model.chat;


import model.networking.data.Message;
import view.gui.WindowChatting;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    static List<Chat> chatList = new ArrayList<Chat>(); // lists of Chats

    //Adds a message to a given chat (by name and address)
    public static void addMessage(String chatName, String chatAddress, Message msg) {
        if (chatExists(chatName, chatAddress)) {
            getChatById(chatName, chatAddress).addMessageToList(msg);
            System.out.println("Added message " + msg.getData() + " to chat: " + chatName);
        } else if (!chatExists(chatName, chatAddress)) {

        } else {
            System.out.println("Couldn't add message to chat with name " + chatName + " and address " + chatAddress);
        }
    }

    //Adds a new chat to the list, with given name, address and port
    public static void addChat (String name, String address, int port) {
        if (!chatExists(name, address)){
            Chat temp = new Chat(name, address, port);
            chatList.add(temp); // Adds new chat
            WindowChatting.ChatUpdated(name, temp);
        }
    }

    //Returns the index of a chat, identified by name and address
    private static int getChatIndex(String name, String address){ // gets number where the current chat is placed
        int index = -1;
        for (int i = 0; i < chatList.size(); i++){
            if(chatList.get(i).getName().equals(name) && chatList.get(i).getAddress().equals(address)){
                index = i;
            }
        }

        return index;
    }

    //Checks if chat with name and address already exists
    public static boolean chatExists(String name, String address){
        for (Chat ch: chatList) {
            //If there already exists a chat with this name and address, return true
            if (ch.getName() == name && ch.getAddress() == address){
                return true;
            }
        }
        return false;
    }

    //Returns the chatList
    public static List<Chat> getChatList() { // returns list of chats
        return chatList;
    }

    //Returns a given chat by index in the list
    public static Chat getChatByIndex(int index){
        return chatList.get(index);
    }

    //Returns a given chat by their id (name and address)
    public static Chat getChatById(String name, String address) {
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getName().equals(name) && chatList.get(i).getAddress().equals(address)) {
                return chatList.get(i);
            }
        }
        return null;
    }

    //Removes a given chat by index in the list
    public static void deleteChatByIndex(int index) {
        chatList.remove(index);
    }

    //Removes a given chat by id (name and address)
    public static void deleteChatById(String name, String address){
        for (int i = 0; i < chatList.size(); i++){
            if (chatList.get(i).getName().equals(name) && chatList.get(i).getAddress().equals(address)){
                chatList.remove(i);
            }
        }
    }
}
