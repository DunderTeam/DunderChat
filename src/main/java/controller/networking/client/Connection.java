package controller.networking.client;

import com.google.gson.Gson;
import controller.networking.data.Message;
import controller.networking.data.Status;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Connection {
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }

    public Connection(String i, int p) {
        ip = i; port = p;
    }

    public String ip;
    public int port;
    public String nick;
}
