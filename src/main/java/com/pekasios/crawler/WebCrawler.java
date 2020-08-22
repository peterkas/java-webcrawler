package com.pekasios.crawler;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class WebCrawler {

    private static final int RESULTS_PER_PAGE_BY_DEFAULT = 10;
    private static final String BING_SEARCH_BASE_URL = "http://www.bing.com/search";

    String searchTermResultsInBing(String term) {
        return searchTermResultsInBing(term, RESULTS_PER_PAGE_BY_DEFAULT);
    }

    String searchTermResultsInBing(String term, int resultsPerPage) {
        // https://docs.microsoft.com/en-us/rest/api/cognitiveservices-bingsearch/bing-web-api-v5-reference#query-parameters
        return Connection.loadFromURL(BING_SEARCH_BASE_URL + "?q=" + term + "&count=" + resultsPerPage);
    }

    List<String> extractMainResultLinks(String htmlResultPage) {
        // https://jsoup.org/cookbook/input/parse-document-from-string
        return Jsoup.parse(htmlResultPage)
                .select("h2 > a")
                .stream()
                .map(element -> element.attr("href"))
                .collect(Collectors.toList());
    }

    List<String> extractJavascriptLibraries(String resultLink){
        return Jsoup.parse(Connection.loadFromURL(resultLink))
                .select("script")
                .stream()
                .map(element -> element.attr("src"))
                .filter(src -> !StringUtil.isBlank(src))
                .collect(Collectors.toList());
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
