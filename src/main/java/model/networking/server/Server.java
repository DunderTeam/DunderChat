package model.networking.server;

import com.dosse.upnp.UPnP;
import model.networking.data.IP;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;
import java.security.PermissionCollection;


public class Server implements Runnable {
    private static ServerSocket server;

    // Constructor to assign port and other values..
    public Server(int port)
    {
        try {
            server = new ServerSocket(port);

            SocketPermission sp = new SocketPermission("*:5555", "accept,connect,listen,resolve");

            if(UPnP.isUPnPAvailable()) {
                if(UPnP.isMappedTCP(port)) {
                    System.out.println("Already mapped");
                } else {
                    UPnP.openPortTCP(port);
                    UPnP.openPortUDP(port); // Should not be needed
                }
            } else {
                System.out.println("Port Forwarding Failed");
                // TODO: Implement notification to user, that application is not working
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Function which runs when server thread is started.
    public void run()
    {
        try {
            System.out.println("Listening on " + UPnP.getExternalIP() + ":" + server.getLocalPort());
            /*
                Current setup below will only allow for one connection at a time, consider
                new approach to listening. Maybe create a new thread upon accepting a
                connection?
             */
            while (!Thread.interrupted())
            {
                Socket conn = server.accept(); // This will block thread - e.g waits for connection
                // Runs data transfer in the background and waits for new connection
                Thread connection_process = new Thread(new Receiver(conn));
                connection_process.start();
            }
        } catch (Exception e) {

        }
    }
}
