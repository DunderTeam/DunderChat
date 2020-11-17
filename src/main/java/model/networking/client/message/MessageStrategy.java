package model.networking.client.message;

import com.google.gson.Gson;
import model.networking.data.Message;
import model.networking.data.Status;

// Provides and interface for sending messages
public interface MessageStrategy {
    void send(Message msg);
}
