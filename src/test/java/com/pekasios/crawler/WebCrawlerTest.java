package com.pekasios.crawler;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebCrawlerTest {

    private WebCrawler webCrawler;

    @BeforeEach
    void setUp() {
        webCrawler = new WebCrawler();
    }

    @ParameterizedTest
    @CsvSource({"weather,<title>weather - Bing</title>"})
    void getBingResultsFromSearchTerm(String input, String expected) {

        assertTrue("Get a Bing result page for the search term",
                webCrawler.searchTermResultsInBing(input).contains(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"weather"})
    void extractMainPageResults(String input) {

        List<String> results = webCrawler.extractMainResultLinks(webCrawler.searchTermResultsInBing(input));
        assertEquals(10, results.size());
        for (String resultLink : results) {
            try {
                URL u = new URL(resultLink);
            } catch (MalformedURLException e) {
                Assert.fail("Result link: " + resultLink + " is not a valid URL");
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://www.apache.org"})
    void extractJavascriptLibraries(String input) {

        List<String> results = webCrawler.extractJavascriptLibraries(input);
        assertEquals(3, results.size());
    }
}
