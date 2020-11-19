package model.networking.server;

import com.google.gson.Gson;
import model.networking.Request;
import model.networking.data.IP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PublicIP {
    private static final String GET_URL = "https://api.ipify.org/?format=json";
    private static IP ip;

    public static IP get() {
        if(ip == null) {
            run();
        }

        return ip;
    }

    private static PublicIP singleton = new PublicIP();

    /* This function remains public due to nature of Runnable ... */
    /* Please avoid running this object in a new thread as it should handle this on its own. */
    private static void run() {
        try {
            /*
                Runs a get request to an API which pings back our public IP
             */
            String response = Request.GET(GET_URL);

            Gson gson = new Gson();
            ip = gson.fromJson(response, IP.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
