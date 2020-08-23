package com.pekasios.crawler;

import com.pekasios.parser.JavascriptLibraryExtractor;
import com.pekasios.parser.WebParser;
import com.pekasios.util.Connection;

import java.util.List;
import java.util.Map;

/**
 * Web crawler to extract javascript libraries used in web pages
 *
 * @author Pedro Casis
 */
public class WebCrawler {

    private static final int RESULTS_PER_PAGE_BY_DEFAULT = 10;
    private static final String BING_SEARCH_BASE_URL = "http://www.bing.com/search";

    private final String searchUrl;

    public WebCrawler() {
        this(BING_SEARCH_BASE_URL);
    }

    public WebCrawler(String baseSearchUrl) {
        this.searchUrl = baseSearchUrl;
    }

    public Map<String, Long> searchJavascriptLibraries(String term) {
        // Single Page search
        // TODO Implement multipage search
        String resultsPage = searchTermResultsInBing(term);
        List<String> resultLinks = getMainResultLinks(resultsPage);
        JavascriptLibraryExtractor jsLibExtractor = new JavascriptLibraryExtractor();
        return jsLibExtractor.extractLibraries(resultLinks);
    }

    private String searchTermResultsInBing(String term) {
        return searchTermResultsInBing(term, RESULTS_PER_PAGE_BY_DEFAULT);
    }

    private String searchTermResultsInBing(String term, int resultsPerPage) {
        // https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-web-api-v5-reference#query-parameters
        return Connection.loadHTMLFromURL(buildSearchUrl(term, resultsPerPage));
    }

    private String buildSearchUrl(String term, int resultsPerPage) {
        return this.searchUrl + "?q=" + term + "&count=" + resultsPerPage;
    }

    private List<String> getMainResultLinks(String htmlResultPage) {
        return WebParser.parseResultLinks(htmlResultPage);
    }
}
