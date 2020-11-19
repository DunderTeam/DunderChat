package model.networking.server;

import com.dosse.upnp.UPnP;
import model.networking.client.MessageFetch;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread implements Runnable {
    private static ServerSocket server;

    // Constructor to assign port and other values..
    public Server(int port)
    {
        try {
            server = new ServerSocket(port, 10, Inet4Address.getByName(UPnP.getLocalIP()));
            portForward(server.getLocalPort());

            this.start(); // Starts the server listener on its own thread
        } catch (Exception e) {
            // Incase of error the server will not start
            System.out.println(e.getMessage());
        }

        MessageFetch fetcher = new MessageFetch(1);
    }

    // Function which runs when server thread is started.
    public void run()
    {
        try {
            System.out.printf("Listening on %s:%d...", UPnP.getExternalIP(), server.getLocalPort());
            /*
                Current setup below will only allow for one connection at a time, consider
                new approach to listening. Maybe create a new thread upon accepting a
                connection?
             */
            while (!Thread.interrupted())
            {
                Socket clientConnection = server.accept(); // This will block thread - e.g waits for connection
                // Runs data transfer in the background and waits for new connection
                new ServerWorker(clientConnection).start();
            }
        } catch (Exception e) {

        }
    }

    private void portForward(int port)
    {
        UPnP.closePortTCP(port);
        if(UPnP.isUPnPAvailable()) {
            if(UPnP.isMappedTCP(port)) {
                System.out.println("Already mapped");
            } else {
                UPnP.openPortTCP(port);
            }
        } else {
            System.out.println("Port Forwarding Failed");
            // TODO: Implement notification to user, that application is not working
        }
    }
}
