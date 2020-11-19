package model.networking.client;

import model.networking.Request;
import model.networking.data.Message;
import model.networking.data.MessageCarrier;

import java.io.IOException;

public class MessageFetch extends Thread {
    private final int interval;

    public MessageFetch(int updatesPerSecond) {
        // -> Maybe do thread-safe singleton, so we dont accidentally clutter out system with several fetchers?
        this.interval = updatesPerSecond / 1000;

        this.start();
    }

    @Override
    public void run() {
        try {
            // todo: infinite-loop - maybe do if thread is interrupted check
            while (true) {
                String resp = Request.GET("https://messagebouncer.herokuapp.com/message");
                MessageCarrier msgCarrier = MessageCarrier.decode(resp);

                if (msgCarrier != null) {
                    for (Message message : msgCarrier.getMsg()) {
                        // todo: pass message to chatmanager
                        // consider implementing sort algorithm in chatmanager as messages here may have various timestamps
                    }
                }

                synchronized (this) {
                    this.wait(interval); // Wait for interval
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}