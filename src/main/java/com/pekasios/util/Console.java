package com.pekasios.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

/**
 * Class to encapsulate Standard I/O logic
 *
 * @author Pedro Casis
 */
public class Console {

    public static String readStringFromStdin() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void printItems(Map<String, Long> itemsMap, int limit, boolean desc) {
        if (itemsMap != null && !itemsMap.isEmpty()) {
            Comparator comparator = desc ? Comparator.reverseOrder() : Comparator.naturalOrder();
            itemsMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(comparator))
                    .limit(limit)
                    .forEach(System.out::println);
        }
    }
}
