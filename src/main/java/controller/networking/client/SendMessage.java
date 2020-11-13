package controller.networking.client;

import com.google.gson.Gson;
import controller.networking.data.Message;
import controller.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SendMessage implements Runnable {
    SendMessage(String i, int p, Message m) {
        ip = i; port = p; msg = m;
    }

    private Message msg;
    private String ip; private int port;
    private Socket conn;


    private String encodeMessage(Message msg) {
        Gson gson = new Gson();
        return gson.toJson(msg);
    }

    private Status decodeStatus(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Status.class);
    }

    // This will run once this class is loaded into a new thread and is started in that thread.
    // Thread x = new Thread(new SendMessage()); x.start();
    public void run() {
        try {
            conn = new Socket(ip, port); // Opens Connection

            DataInputStream in = new DataInputStream(conn.getInputStream());
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            out.writeUTF(encodeMessage(msg));
            out.flush();

            int status = decodeStatus(in.readUTF()).getStatus();

            // Gets server response, do something based on result?:
            switch(status) {
                case 200:
                    // all good, message sent and received
                    break;
                case 204:
                    // empty message sent
                    break;
                default:
                    // Message not received, try resending
                    // Todo: implement resend/retry functionality
                    break;
            }

            out.close();
            conn.close();
        } catch(Exception e) {
            System.out.println(e.getCause());
        }
    }
}
