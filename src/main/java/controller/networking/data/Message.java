package controller.networking.data;

public class Message {
    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    private String data;
    private String name;
    private int timestamp;
}
