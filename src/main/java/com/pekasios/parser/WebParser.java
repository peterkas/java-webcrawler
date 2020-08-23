package com.pekasios.parser;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to encapsulate parse logic, Jsoup library is used to simplify html parsing
 *
 * @author Pedro Casis
 */
public class WebParser {

    public static List<String> parseResultLinks(String htmlResultPage) {
        // https://jsoup.org/cookbook/input/parse-document-from-string
        return Jsoup.parse(htmlResultPage)
                .select("h2 > a")
                .stream()
                .map(element -> element.attr("href"))
                .collect(Collectors.toList());
    }

    public static List<String> parseJavascriptLibraries(String htmlWebPage){
        return Jsoup.parse(htmlWebPage)
                .select("script")
                .stream()
                .map(element -> element.attr("src"))
                .filter(src -> !StringUtil.isBlank(src))
                .collect(Collectors.toList());
    }
}
