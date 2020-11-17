package model.networking.client.message;

import com.google.gson.Gson;
import model.networking.data.Message;
import model.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMessage implements Runnable {
    // Todo: Implement strategy pattern for sending message
    private int connectionAttempts = 5;

    SendMessage(String i, int p, Message m) {
        ip = i; port = p; msg = m;
        m.setTimestamp(java.time.Clock.systemUTC().instant().toString());
    }

    private final Message msg;
    private final String ip; private final int port;


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
            if(!send()) { // if failed to send, retry
                retry();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean send() throws IOException {
        Socket conn = new Socket(ip, port); // Opens Connection

        DataInputStream in = new DataInputStream(conn.getInputStream());
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

        out.writeUTF(encodeMessage(msg));
        out.flush();

        int status = decodeStatus(in.readUTF()).getStatus();

        out.close();
        conn.close();
        // Gets server response, do something based on result?:
        switch(status) {
            case 200:
                // all good, message sent and received
                return true;
            case 204:
                // empty message sent
                // Todo: implement message check
                return false;
            default:
                // Message not received, try resending
                return false;
        }
    }

    private void retry() {
        while(connectionAttempts > 0) {
            try {
                Thread.currentThread().wait(500);
                System.out.println("Message failed to send, retrying...");
                if(send()) { // If successful, then break loop
                    break;
                }
                connectionAttempts--;
                // todo: ran out of attempts handler?
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
