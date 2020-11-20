package model.networking.client.message;

import com.google.gson.Gson;
import model.networking.Request;
import model.networking.data.Message;

import java.io.IOException;

public class SendMessagePOST  implements MessageStrategy {
    public void send(Message msg) {
        try {
            Gson gson = new Gson();
            String data = gson.toJson(msg);

            Request.POST("https://messagebouncer.herokuapp.com/message?", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
