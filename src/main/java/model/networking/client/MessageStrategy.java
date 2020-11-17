package model.networking.client;

import model.networking.data.Message;

// Provides and interface for sending messages
public interface MessageStrategy {
    void send(Message msg);
}
