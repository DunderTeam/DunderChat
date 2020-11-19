package model.networking.server;

import model.networking.Request;
import model.networking.data.Message;
import model.networking.data.MessageCarrier;

import java.io.IOException;

public class MessageGetter implements Runnable {
    private final int interval;

    public MessageGetter(int updatesPerSecond) {
        // -> Maybe do thread-safe singleton, so we dont accidentally clutter out system with several fetchers?
        this.interval = updatesPerSecond / 1000;
    }

    public void run() {
        try {
            // todo: infinite-loop - maybe do if thread is interrupted check
            while (true) {
                String resp = Request.GET("https://messagebouncer.herokuapp.com/message");
                MessageCarrier msgCarrier = MessageCarrier.decode(resp);
                System.out.println("Getting");
                if (msgCarrier != null) {
                    for (Message message : msgCarrier.getMsg()) {
                        // todo: pass message to chatmanager
                        // consider implementing sort algorithm in chatmanager as messages here may have various timestamps
                    }
                }

                Thread.sleep(interval);

                System.out.println("After sync");

            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}