package controller.networking.server;

import com.google.gson.Gson;
import controller.networking.data.Message;
import controller.networking.data.Status;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
            Gson gson = new Gson();
            System.out.println("Listening on port:" + server.getLocalPort());
            /*
                Current setup below will only allow for one connection at a time, consider
                new approach to listening. Maybe create a new thread upon accepting a
                connection?
             */
            while (!Thread.interrupted())
            {
                Socket conn = server.accept(); // This will block thread - e.g waits for connection
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());

                String data = read(conn);
                Status status = new Status();
                if(data == null) {
                    status.setStatus(204); // Received but empty
                    String response = gson.toJson(status);
                    out.writeUTF(response);
                    out.flush();
                    out.close();
                } else {
                    Message x = decodeMessage(data); // Todo: pass this to chatManager
                    status.setStatus(200); // Received but empty
                    String response = gson.toJson(status);
                    out.writeUTF(response);
                    out.flush();
                    out.close();
                    System.out.println(x.getData());
                }

                conn.close();
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
