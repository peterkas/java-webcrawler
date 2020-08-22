package com.pekasios.crawler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ConsoleTest {

    @ParameterizedTest
    @ValueSource(strings = {"searchTerm"})
    void readStringFromStdin(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertEquals("searchTerm", Console.readStringFromStdin());
    }

    @ParameterizedTest
    @ValueSource(strings = {"searchTerm"})
    void printTopFive(String input) {
        Map<String, Long> resultsMap = new HashMap<>();
        resultsMap.put("jQuery", 543L);
        resultsMap.put("React JS", 232L);
        resultsMap.put("Angular", 125L);
        resultsMap.put("Vue.js", 74L);
        resultsMap.put("backbone.js", 21L);
        resultsMap.put("Ember.js", 53L);
        resultsMap.put("Meteor JS", 35L);
        resultsMap.put("Polymer JS", 1L);
        resultsMap.put("Ext.js", 156L);
        resultsMap.put("Node.js", 398L);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        Console.printTopFive(resultsMap);

        assertEquals("jQuery=543\n" +
                "Node.js=398\n" +
                "React JS=232\n" +
                "Ext.js=156\n" +
                "Angular=125\n", baos.toString());
    }
}
