package com.pekasios.crawler;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;


class WebParserTest {

    @Test void parsePageResults() throws Exception {
        List<String> results = WebParser.parseResultLinks(readFile("weather_result.html"));
        assertEquals(1, results.size());
    }

    @Test void parseJavascriptLibraries() throws Exception {
        List<String> results = WebParser.parseJavascriptLibraries(readFile("page.html"));
        assertEquals(3, results.size());
    }

    String readFile(String path) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(path).toURI()));
        return new String(encoded, Charset.defaultCharset());
    }

}