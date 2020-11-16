package model.networking.server;

import model.networking.data.Message;
import model.networking.data.Status;
import controller.chat.ChatManager;

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
                ChatManager.addMessage(conn.getRemoteSocketAddress(),msg); // adds message to chat list
                System.out.println(msg.getData());
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
