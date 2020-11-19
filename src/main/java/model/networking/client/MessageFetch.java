package model.networking.client;

import model.networking.data.Message;
import model.networking.data.MessageCarrier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageFetch extends Thread {
    private final int interval;

    public MessageFetch(int updatesPerSecond) {
        // -> Maybe do singleton, so we dont clutter out system with several fetchers?
        this.interval = updatesPerSecond / 1000;

        this.start();
    }

    @Override
    public void run() {
        try {
            // todo: infinite-loop - maybe do if thread is interrupted check
            while (true) {
                String resp = fetch("https://messagebouncer.herokuapp.com/message");
                MessageCarrier msgCarrier = MessageCarrier.decode(resp);

                for (Message message : msgCarrier.getMsg()) {
                    // todo: pass message to chatmanager
                    // consider implementing sort algorithm in chatmanager as messages here may have various timestamps
                }

                this.wait(interval); // Makes this thread wait a bit, before doing new update
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private String fetch(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection)obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();
        }

        return null;
    }
}