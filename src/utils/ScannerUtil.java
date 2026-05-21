package utils;

import java.util.Scanner;

public class ScannerUtil {

    public static String getString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
