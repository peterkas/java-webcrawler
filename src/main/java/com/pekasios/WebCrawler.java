package com.pekasios;

import java.util.Scanner;

class WebCrawler {

    void readStringFromStdin() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            System.out.print(scanner.next());
        }
    }
}
