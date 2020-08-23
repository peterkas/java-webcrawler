package com.pekasios.command;

import com.pekasios.crawler.WebCrawler;
import com.pekasios.util.Console;

import java.util.Map;

/**
 * Java program which search top javascript libraries used in web pages found on Bing
 *
 * @author Pedro Casis
 */
public class TopFiveJsLib {

    public static void main(String[] args) {
        System.out.print("Write search term : ");
        String input = Console.readStringFromStdin();
        WebCrawler webCrawler = new WebCrawler();
        printTopFive(webCrawler.searchJavascriptLibraries(input));

    }

    private static void printTopFive(Map<String, Long> itemsMap) {
        System.out.println("-------------------------------------------------------");
        System.out.println("--------- Top 5 Javascript Libraries found ------------");
        Console.printItems(itemsMap, 5, true);
    }
}
