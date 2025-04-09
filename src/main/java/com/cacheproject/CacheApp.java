package com.cacheproject;

import com.cacheproject.domain.cache.factory.CacheFactory;
import com.cacheproject.domain.cache.model.CacheSystem;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.cache.policy.LRUStrategy;
import com.cacheproject.domain.types.CacheType;
import com.cacheproject.presentation.ConsoleUI;

public class CacheApp {
    public static void main(String[] args) {

        CacheSystem cacheSystem = CacheFactory.createCache(
                CacheType.N_WAY,
                8,   // Anzahl der Sets
                2,     // Assoziativität
                4,    // Worte pro Line
                new FIFOStrategy() // Ersatzstrategie: FIFO
        );

        ConsoleUI ui = new ConsoleUI(cacheSystem);
        ui.start();
    }
}
