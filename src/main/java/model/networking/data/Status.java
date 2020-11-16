package model.networking.data;

import com.google.gson.Gson;

// Data structure for status messages
public class Status {
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int status;

    public static Status decode(String data)
    {
        Gson gson = new Gson();
        return gson.fromJson(data, Status.class);
    }

    public static String encode(int data)
    {
        Status stat = new Status();
        stat.setStatus(data);
        Gson gson = new Gson();
        return gson.toJson(stat);
    }
}
