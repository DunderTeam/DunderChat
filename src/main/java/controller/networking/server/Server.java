package controller.networking.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private ServerSocket ss;


    private Socket waitForConnection()
    {
        try {
            return ss.accept();
        } catch(Exception e) {
            System.out.println("Error getting Socket");
            return null;
        }
    }


    public void run()
    {
        try {
            ss = new ServerSocket(0);

            System.out.println("Listening on " + ":" + ss.getLocalPort());
            int connections = 0;
            while(true) {
                Socket conn = waitForConnection();
                System.out.println("New Connection made: "+conn.toString());

                String data = read(conn);

                // Exit loop if thread is interrupted
                if(Thread.interrupted()) {
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getCause());

            // TODO: Implement proper exception catch
        }
    }

    private String read(Socket x)
    {
        try {
            DataInputStream d = new DataInputStream(x.getInputStream());
            return d.readUTF();
        } catch(IOException e) {
            return null;
        }
    }

    ServerSocket get()
    {
        return ss;
    }
}
