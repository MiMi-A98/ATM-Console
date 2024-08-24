package MiMiA98.atm.app.screen;

import java.util.Scanner;

public class Screen {

    public static void display(String message) {
        System.out.println(message);
    }

    public static int getInputInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String getInputString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static double getInputDouble() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }
}
