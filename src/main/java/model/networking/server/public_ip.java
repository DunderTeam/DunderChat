package model.networking.server;

import com.google.gson.Gson;
import model.networking.data.IP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class public_ip {
    private static final String GET_URL = "https://api.ipify.org/?format=json";
    public static IP get() {
        try {
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
                return gson.fromJson(response.toString(), IP.class);
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
