package com.pekasios;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class WebCrawler {

    static final int DEFAULT_MAX_PAGE_RESULTS = 10;

    String readStringFromStdin() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        return input;
    }

    String searchResultsInBing(String term) {
        return searchResultsInBing(term, DEFAULT_MAX_PAGE_RESULTS);
    }

    String searchResultsInBing(String term, int maxResults) {
        StringBuilder output = new StringBuilder();
        try {
            // https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-web-api-v5-reference#query-parameters
            URL bing = new URL("http://www.bing.com/search?q=" + term + "&count=" + maxResults);

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

    List<String> extractMainResultLinks(String resultsPage) {
        Document doc = Jsoup.parse(resultsPage);
        Elements resultLinks = doc.select("h2 > a");
        List<String> mainResults = new ArrayList<>();
        for (Element resultLink : resultLinks) {
            mainResults.add(resultLink.attr("href"));
        }
        return mainResults;
    }

}
