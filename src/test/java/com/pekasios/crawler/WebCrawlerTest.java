package com.pekasios.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;

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

        assertEquals(9,
                webCrawler.extractMainResultLinks(webCrawler.searchTermResultsInBing(input)).size());
    }
}