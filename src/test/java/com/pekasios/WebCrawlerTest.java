package com.pekasios;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class WebCrawlerTest {

    @Test void readStringFromStdin() {
        String input = "searchTerm";
        String expected = "searchTerm";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        WebCrawler webCrawler = new WebCrawler();
        webCrawler.readStringFromStdin();

        Assert.assertEquals(expected, baos.toString());
    }

}
