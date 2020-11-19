package model.networking.data;

import com.google.gson.Gson;

public class MessageCarrier {
    public Message[] getMsg() {
        return msg;
    }

    public void setMsg(Message[] msg) {
        this.msg = msg;
    }

    private Message []msg;

    public static MessageCarrier decode(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, MessageCarrier.class);
    }

    public static String encode(MessageCarrier data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}