package com.pekasios.crawler;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

class Console {

    static String readStringFromStdin() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        return input;
    }

    static void printTopFive(Map<String, Long> itemsMap) {
        if (itemsMap != null && !itemsMap.isEmpty()) {
            itemsMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(5)
                    .forEach(System.out::println);
        }
    }
}
