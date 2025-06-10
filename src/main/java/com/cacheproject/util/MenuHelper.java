package com.cacheproject.util;

public class MenuHelper {
    private static final String[] MENU_OPTIONS = {
        "1. Access memory address",
        "2. Display cache state",
        "3. Run simulation",
        "4. Show statistics",
        "5. Reset statistics",
        "6. Exit"
    };

    public static void displayMenu() {
        System.out.println("\nPlease choose an option:");
        for (String option : MENU_OPTIONS) {
            System.out.println(option);
        }
    }
}
