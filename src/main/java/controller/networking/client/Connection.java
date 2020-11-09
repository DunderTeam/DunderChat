package controller.networking.client;

import java.net.Socket;

public class Connection {
    private Socket conn;

    Connection(String ip, int port) {
        try {
            conn = new Socket(ip, port);
        } catch(Exception e) {
            System.out.println(e.getCause());

            // TODO: Implement proper exception catch
        }
    }
}
