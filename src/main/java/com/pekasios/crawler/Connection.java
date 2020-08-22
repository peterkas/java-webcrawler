package com.pekasios.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class Connection {

    public static String loadFromURL(String url) {
        StringBuilder output = new StringBuilder();
        try {
            URLConnection yc = new URL(url).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
