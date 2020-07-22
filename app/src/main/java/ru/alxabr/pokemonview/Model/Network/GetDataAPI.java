package ru.alxabr.pokemonview.Model.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is designed to work with HTTP connection.
 */
public class GetDataAPI {
    /**
     * Connects to the specified address,
     * sends a request, and reads the response.
     * @param urlToRead URL for receiving data from API.
     * @return The string with the received data.
     * @throws IOException To catch errors when connecting.
     */
    public String getHTML(String urlToRead) throws IOException {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        url = new URL(urlToRead);
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10*1000);
        conn.setReadTimeout(10*1000);
        conn.setRequestMethod("GET");
        rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();

        return result;
    }
}
