package model.networking.data;

import com.google.gson.Gson;

public class Message {
    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    private String data;
    private String name;
    private String timestamp;
    private String destination;
    private String origin;

    public static Message decode(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Message.class);
    }
    public static Message[] decodeArray(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Message[].class);
    }

    public static String encode(Message data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}