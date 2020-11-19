package model.networking.server;

import model.chat.ChatManager;
import model.networking.data.Message;
import model.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver implements Runnable {
    private static Socket conn;

    public Receiver(Socket connection)
    {
        conn = connection;
    }

    public void run()
    {
        try {
            DataOutputStream dout = new DataOutputStream(conn.getOutputStream());
            String data = read(conn);
            if(data == null) {
                dout.writeUTF(Status.encode(204)); // Received but empty
            } else {
                Message msg = Message.decode(data);
                String address = conn.getInetAddress().toString().substring(1);
                int port = conn.getPort();

                //If the chat doesn't exist, create a new chat
                if (!ChatManager.chatExists(msg.getName(), address)) {
                    ChatManager.addChat(msg.getName(), address, port);
                }
                //Add the message to the correct chat
                if (!ChatManager.chatExists(msg.getName(), address)) {
                ChatManager.addMessage(msg.getName(), address, msg);
                }

                /*
                List<Message> temp = ChatManager.getChatById(msg,conn.getRemoteSocketAddress()); // test to see that the list works
                for (Message me : temp){
                    System.out.println(me.getName());
                    System.out.println(me.getData());
                }

                 */

                dout.writeUTF(Status.encode(200)); // Received but empty
            }

            dout.flush();
            dout.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read data from a connection
    private String read(Socket x)
    {
        try {
            DataInputStream stream = new DataInputStream(x.getInputStream());
            return stream.readUTF();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
