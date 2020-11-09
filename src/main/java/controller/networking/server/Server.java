package controller.networking.server;

import java.net.ServerSocket;

public class Server {
    private ServerSocket ss;


    /*
        Initializes the server socket or the public IP adress so we can accept messages
     */
    Server(int port)
    {
        try {
            ss = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println(e.getCause());

            // TODO: Implement proper exception catch
        }
    }

    ServerSocket get()
    {
        return ss;
    }
}
