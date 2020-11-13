package controller.networking.client;

import java.net.Socket;

public class Connection implements Runnable {
    private Socket conn;

    String ip;
    int port;

    public Connection(String i, int p) {
        ip = i; port = p;
    }



    public void run() {
        try {
            conn = new Socket(ip, port);
        } catch(Exception e) {
            System.out.println(e.getCause());

            // TODO: Implement proper exception catch
        }
    }
}
