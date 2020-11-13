package model.networking.client;

import com.google.gson.Gson;
import model.networking.data.Message;
import model.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMessage implements Runnable {
    private int connectionAttempts = 5;

    SendMessage(String i, int p, Message m) {
        ip = i; port = p; msg = m;
        m.setTimestamp(java.time.Clock.systemUTC().instant().toString());
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
            if(!send()) { // if failed to send, retry
                retry();
            }
        } catch(Exception e) {
            System.out.println(e.getCause());
        }
    }

    private boolean send() throws IOException {
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
                out.close();
                conn.close();
                return true;
            case 204:
                // empty message sent
                // Todo: implement message check
                return false;
            default:
                // Message not received, try resending
                out.close();
                conn.close();
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
