package com.pekasios.crawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java program which counts top javascript libraries used in web pages found on Bing
 *
 * @author Pedro Casis
 */
public class WebCrawler {

    private static final int RESULTS_PER_PAGE_BY_DEFAULT = 10;
    private static final String BING_SEARCH_BASE_URL = "http://www.bing.com/search";

    String searchTermResultsInBing(String term) {
        return searchTermResultsInBing(term, RESULTS_PER_PAGE_BY_DEFAULT);
    }

    String searchTermResultsInBing(String term, int resultsPerPage) {
        // https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-web-api-v5-reference#query-parameters
        return Connection.loadHTMLFromURL(BING_SEARCH_BASE_URL + "?q=" + term + "&count=" + resultsPerPage);
    }

    List<String> extractMainResultLinks(String htmlResultPage) {
        return WebParser.parseResultLinks(htmlResultPage);
    }

    List<String> extractJavascriptLibraries(String resultLink){
        return WebParser.parseJavascriptLibraries(Connection.loadHTMLFromURL(resultLink));
    }

    Map<String, Long> countJavascriptLibraries(List<String> resultLinks){
        Map<String, Long> scriptSourcesMap = new HashMap<>();
        for (String link : resultLinks) {
            List<String> scriptSources = extractJavascriptLibraries(link);

            for (String scriptSrc : scriptSources) {
                String jsName = scriptSrc.substring(scriptSrc.lastIndexOf("/"));

                if (scriptSourcesMap.containsKey(jsName)) {
                    scriptSourcesMap.put(jsName, scriptSourcesMap.get(jsName)+1);
                } else {
                    scriptSourcesMap.put(jsName, 1L);
                }
            }
        }
        return scriptSourcesMap;
    }
}
