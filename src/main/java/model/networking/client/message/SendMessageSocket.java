package model.networking.client.message;

import model.networking.client.Connection;
import model.networking.data.Message;
import model.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;

public class SendMessageSocket extends Thread implements MessageStrategy{
    private final Connection connection;
    private int connectionAttempts = 5;
    private Message message;

    public SendMessageSocket(Connection receiver) {
        // Todo: Refactor code from SendMessage and implement here

        this.connection = receiver;
    }

    public void send(Message msg) {
        this.message = msg;

        Date date = new Date();
        msg.setTimestamp(date.getTime());

        this.start();
    }

    @Override
    public void run() {
        try {
            if(!sendImplementation()) { // if failed to send, retry
                retry();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean sendImplementation() throws IOException {
        Socket conn = new Socket(connection.ip, connection.port); // Opens Connection

        DataInputStream in = new DataInputStream(conn.getInputStream());
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

        out.writeUTF(Message.encode(message));
        out.flush();

        int status = Status.decode(in.readUTF()).getStatus();

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
                out.close();
                conn.close();
                return false;
            default:
                // Message not received, try resending
                out.close();
                conn.close();
                break;
        }

        return false;
    }

    private void retry() {
        while(connectionAttempts > 0) {
            try {
                Thread.currentThread().wait(500);
                System.out.println("Message failed to send, retrying...");
                if(sendImplementation()) { // If successful, then break loop
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
