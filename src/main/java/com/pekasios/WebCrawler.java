package com.pekasios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

class WebCrawler {

    String readStringFromStdin() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        return input;
    }

    String searchResultsInBing(String term) {
        StringBuilder output = new StringBuilder();
        try {
            URL bing = new URL("http://www.bing.com/search?q=" + term);

            URLConnection yc = bing.openConnection();
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
