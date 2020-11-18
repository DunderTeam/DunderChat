package controller.chat;

import com.mongodb.client.MongoCollection;
import model.networking.data.Message;
import model.database.DB;
import org.bson.Document;

public class Controller {

    MongoCollection<Document> Doc = DB.getUserCollection();

    public static void SendMessage(String sen, String mes, String add, String cName){ //ChatMsgSend

        Message msg = new Message(); // create ned message
        msg.setName(sen);
        msg.setData(mes);

        ChatManager.addMessage(cName, add, msg); // add new message to list

    }

    public void CreateNewChat(String user, String address ){ // connectNEwChat
        ChatManager.addChat(user, address, 5555);
    }

    public void Login(String Name, String Password){ // login user


        DB.login(Doc, Name, Password); // log user inn to database
    }

    public void LogOut() { // log out user

    }

    public void RegisterUser(String Name, String Password, String Ip ){ // register new user for app

        DB.addUser(Doc, Name, Ip, Password ); // adds new user to Database

    }

    public void ChangeName() { // Change name on user

    }

    public void ChangePassword(String Name, String OldPassword, String NewPassword){ // Change password on user

        DB.changePassword(Doc, Name, OldPassword, NewPassword); // Change password on user
    }

    public void DeleteUser(String Name, String Password){ // Delete current user

        DB.deleteUser(Doc, Name, Password); // Delete user from database
    }




}
