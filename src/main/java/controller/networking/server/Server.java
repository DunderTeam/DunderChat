package controller.networking.server;

import com.google.gson.Gson;
import controller.networking.data.Message;
import controller.networking.data.Status;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class Server implements Runnable {
    private static ServerSocket server;

    // Constructor to assign port and other values..
    public Server(int port)
    {
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    // If server is launched without port, it will attempt to find an available one
    public Server()
    {
        try {
            server = new ServerSocket(0);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    // Function which runs when server thread is started.
    public void run()
    {
        try {
            System.out.println("Listening on port:" + server.getLocalPort());

            while (true)
            {
                Socket conn = server.accept(); // This will block thread - e.g waits for connection

                String data = read(conn);
                if(data == null) {
                    // Todo: empty message status
                } else {
                    Message x = decodeMessage(data); // Todo: pass this to chatManager
                    // Todo: success status
                    System.out.println(x.getData());
                }

                conn.close();

                // If this thread is interrupted then we close the server
                if(Thread.interrupted()) {
                    break; // Stops "server" loop
                }
            }
        } catch (Exception e) {

        }
    }

    private Message decodeMessage(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Message.class);
    }

    private String returnStatus(int status) {
        Gson gson = new Gson();
        Status st = new Status();
        st.setStatus(status);

        return gson.toJson(st);
    }

    // Read data from a connection
    private String read(Socket x)
    {
        try {
            DataInputStream stream = new DataInputStream(x.getInputStream());
            return stream.readUTF();
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }
    }
}
