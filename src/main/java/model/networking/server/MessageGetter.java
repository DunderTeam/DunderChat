package model.networking.server;

import model.chat.ChatManager;
import model.networking.Request;
import model.networking.data.Message;

import java.io.IOException;
import java.util.ArrayList;

public class MessageGetter implements Runnable {
    private final int interval;

    public MessageGetter(int updatesPerSecond) {
        // -> Maybe do thread-safe singleton, so we dont accidentally clutter out system with several fetchers?
        this.interval = 1000 / updatesPerSecond;
    }

    public void run() {
        try {
            // todo: infinite-loop - maybe do if thread is interrupted check
            while (true) {
                String resp = Request.GET("https://messagebouncer.herokuapp.com/message");
                Message []msgArray = Message.decodeArray(resp);
                if (msgArray != null) {
                    for (Message message : msgArray) {
                        if (!ChatManager.chatExists(message.getName(), message.getOrigin())) {
                            ChatManager.addChat(message.getName(), message.getOrigin(), 5555); // todo: message via get request does not use ports
                        }
                        ChatManager.addMessage(message.getName(), message.getOrigin(), message); // todo: this ip might be formatted differently

                    }
                }

                Thread.sleep(interval);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}