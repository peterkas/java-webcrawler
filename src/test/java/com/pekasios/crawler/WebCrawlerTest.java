package com.pekasios.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.Assert.assertTrue;

class WebCrawlerTest {

    private WebCrawler webCrawler;

    @BeforeEach
    void setUp() {
        webCrawler = new WebCrawler();
    }

    @ParameterizedTest
    @ValueSource(strings = {"weather"})
    void searchPagesAndExtractJavascriptLibraries(String input) {
        Map<String, Long> results = webCrawler.searchJavascriptLibraries(input);
        assertTrue("Not found result links", !results.isEmpty());
    }
}
