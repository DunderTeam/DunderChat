package model.networking.server;

import controller.chat.ChatManager;
import model.networking.data.Message;
import model.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerWorker extends Thread {
    private final Socket clientSocket;
    private boolean doDebug = true;

    public ServerWorker(Socket cli)
    {
        this.clientSocket = cli;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        DataOutputStream dataOutput = new DataOutputStream(clientSocket.getOutputStream());
        String dataInput = read(clientSocket);

        if(dataInput == null) {
            dataOutput.writeUTF(Status.encode(204)); // Received but empty
        } else {
            Message msg = Message.decode(dataInput);

            ChatManager.addMessage(clientSocket.getRemoteSocketAddress(),msg); // adds message to chat list

            debug(msg.getData());

            dataOutput.writeUTF(Status.encode(200)); // Received but empty

            dataOutput.flush();
            dataOutput.close();
            clientSocket.close();
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

    private void debug(String x) {
        if(doDebug) {
            System.out.printf("ServerWorker dbg: %s", x);
        }
    }
}