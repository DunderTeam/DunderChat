package controller;

import com.mongodb.client.MongoCollection;
import model.chat.ChatManager;
import model.networking.data.Message;
import model.database.DB;
import org.bson.Document;
import model.database.Session;

public class Controller { // The controller of all functions

    static MongoCollection<Document> Doc = DB.getUserCollection();

    public static void SendMessage(String sen, String mes, String Ip, String ChatName, String Name){ //ChatMsgSend

        Message msg = new Message(); // create ned message
        msg.setName(sen);
        msg.setData(mes);

        ChatManager.addMessage(ChatName, Ip, msg); // add new message to list

        UpdateSession(Name,Ip); // Still active
    }

    public static void CreateNewChat(String user, String address, String Name, String Ip ){ // connectNEwChat
        ChatManager.addChat(user, address, 5555);

        UpdateSession(Name,Ip); // Still active
    }

    public static boolean Login(String Name, String Password , String Ip){ // login user

        Boolean temp = DB.login(Doc, Name, Password); // log user inn to database

        Session.sessionInit(Name, Ip); // Start session

        return temp;
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

    public static void UpdateSession(String Name, String Ip){ // Update Session to say your still active
        Session.restart(Name, Ip); // restart Session timer
    }


}
