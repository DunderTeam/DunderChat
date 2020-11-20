package controller;

import com.mongodb.client.MongoCollection;
import model.chat.ChatManager;
import model.database.DB;
import model.database.Session;
import model.networking.client.Connection;
import model.networking.client.ConnectionManager;
import model.networking.client.message.MessageStrategy;
import model.networking.client.message.SendMessagePOST;
import model.networking.client.message.SendMessageSocket;
import model.networking.data.Message;
import org.bson.Document;
import view.gui.WindowChatting;

public class Controller { // The controller of all functions

    static MongoCollection<Document> Doc = DB.getUserCollection();
    static ConnectionManager manager = new ConnectionManager(); // Consider making Singleton
    static MessageStrategy currentStrategy;

    public static void SendMessage(String sen, String mes, String localIp, String destinationIp, String ChatName, String strategy){ //ChatMsgSend

        Message msg = new Message(); // create ned message
        msg.setName(sen);
        msg.setData(mes);

        Message msgLocal = new Message();
        msgLocal.setName(sen + " (you)");
        msgLocal.setData(mes);

        ChatManager.addMessage(ChatName, destinationIp, msgLocal);

        Connection temp = new Connection(destinationIp, 5555);

        switch (strategy){
            case("http"): currentStrategy = new SendMessagePOST(); break;
            case("socket"): currentStrategy = new SendMessageSocket(temp); break;
            default: currentStrategy = new SendMessagePOST(); break;
        }

        manager.sendMessage(temp, msg, currentStrategy);

        UpdateSession(sen,localIp); // Still active
    }

    public static void CreateNewChat(String ChatName, String UserName, String LocalIp, int Port ){ // connectNEwChat
        String chatIp = Session.getSessionIP(ChatName);

        if (chatIp == null) {
            WindowChatting.displayErrorDialog("User does not have an active session");
        } else {
            ChatManager.addChat(ChatName, chatIp, Port);
        }

        UpdateSession(UserName, LocalIp);
    }

    public static void CreateNewChatByIP(String ChatIp, String UserName, String LocalIp, int Port){
        String chatName = Session.getSessionUser(ChatIp);

        if (chatName == null) {
            WindowChatting.displayErrorDialog("No active session on that ip");
        } else {
            ChatManager.addChat(chatName, ChatIp, Port);
        }

        UpdateSession(UserName, LocalIp);
    }

    public static void Login(String Name, String Password , String Ip){ // login user

        DB.login(Doc, Name, Password); // log user inn to database

        if (Session.isLoggedIn()) {
            UpdateSession(Name, Ip); // Still active
        }

    }

    public static void LogOut(String Name) { // log out user

        Session.endSession(Name); // Still active
    }

    public static void RegisterUser(String Name, String Password, String Ip ){ // register new user for app

        DB.addUser(Doc, Name, Ip, Password ); // adds new user to Database

    }

    public static void ChangeName(String OldName, String NewName, String Password, String Ip ) { // Change name on user

        DB.changeUsername(Doc, OldName, Password, NewName); // change name on user

        UpdateSession(NewName,Ip); // Still active
    }

    public static void ChangePassword(String Name, String OldPassword, String NewPassword, String Ip){ // Change password on user

        DB.changePassword(Doc, Name, OldPassword, NewPassword); // Change password on user

        UpdateSession(Name,Ip); // Still active
    }

    public static void DeleteUser(String Name, String Password){ // Delete current user

        Session.endSession(Name); //  End Session
        DB.deleteUser(Doc, Name, Password); // Delete user from database
    }

    public static void Shutdown(String Name) {
        //TODO gracefully shutdown messaging and chat managers if needed

        if (Session.isLoggedIn()) {
            Session.endSession(Name);
        }

        System.exit(0);
    }

    public static void UpdateSession(String Name, String Ip){ // Update Session to say your still active
        Session.restart(Name, Ip); // restart Session timer
    }


}
