package model.networking.server;

import com.google.gson.Gson;
import model.networking.data.IP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PublicIP implements Runnable {
    private static final String GET_URL = "https://api.ipify.org/?format=json";
    private static IP ip;

    public static IP get() {
        if(ip == null) {
            Thread get = new Thread(new PublicIP());
        }

        return ip;
    }

    private static PublicIP singleton = new PublicIP();

    /* This function remains public due to nature of Runnable ... */
    /* Please avoid running this object in a new thread as it should handle this on its own. */
    public void run() {
        try {
            /*
                Runs a get request to an API which pings back our public IP
             */
            URL obj = new URL(GET_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection)obj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = httpURLConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                }

                Gson gson = new Gson();
                ip = gson.fromJson(response.toString(), IP.class);
            } else {
                ip = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
