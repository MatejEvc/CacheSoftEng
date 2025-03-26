package com.cacheproject;

import com.cacheproject.business.Address;
import com.cacheproject.business.CacheSystem;
import com.cacheproject.business.LRUStrategy;
import com.cacheproject.presentation.ConsoleUI;

public class Main {
    public static void main(String[] args) {
//        int setCount = 8; //Das könnte man auch in der Konsole implementieren
//        int associativity = 2;
//        int wordsPerLine = 4;
//        CacheSystem cacheSystem = new CacheSystem(setCount, associativity, wordsPerLine, new LRUStrategy());
//        ConsoleUI ui = new ConsoleUI(cacheSystem);
//        ui.start();
        Address adr = new Address(42);
        adr.debugBitExtraction(3, 2);
    }
}