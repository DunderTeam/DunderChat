package controller.chat;

import model.networking.data.Message;

public class Controller {

    public static void SendMessage(String sen, String mes, String add, String cName){ //ChatMsgSend

        Message msg = new Message(); // create ned message
        msg.setName(sen);
        msg.setData(mes);

        ChatManager.addMessage(cName, add, msg); // add new message to list

    }

    public void CreateNewChat(String user, String address ){ // connectNEwChat
        ChatManager.addChat(user, address, 5555);
    }

    public void Login(){ // login user

    }

    public void LogOut() { // log out user

    }

    public void RegisterUser(){ // register new user for app

    }

    public void ChangeName() { // Change name on user

    }

    public void ChangePassword(){ // Change password on user

    }

    public void DeleteUser(){ // Delete current user

    }




}
