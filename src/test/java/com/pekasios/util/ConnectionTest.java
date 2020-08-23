package com.pekasios.util;

import com.pekasios.util.Connection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertTrue;

public class ConnectionTest {

    @ParameterizedTest
    @CsvSource({"weather,<title>weather - Bing</title>"})
    void getBingResultsFromSearchTerm(String input, String expected) {
        assertTrue("Get a Bing result page for the search term",
                Connection.loadHTMLFromURL("http://www.bing.com/search?q=" + input + "&count=1").contains(expected));
    }
}
