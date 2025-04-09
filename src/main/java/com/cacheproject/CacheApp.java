package com.cacheproject;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.cache.repository.CacheRepository;
import com.cacheproject.domain.cache.repository.InMemoryCacheRepository;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.domain.provider.WordProvider;
import com.cacheproject.domain.service.CacheService;
import com.cacheproject.domain.types.CacheType;
import com.cacheproject.presentation.ConsoleUI;

public class CacheApp {
    public static void main(String[] args) {
        CacheRepository cacheRepository = new InMemoryCacheRepository();
        CacheService cacheService = new CacheService(cacheRepository);
        WordProvider wordProvider = new StaticWordProvider(1); // Beispiel für einen WordProvider

        CacheConfiguration config = new CacheConfiguration(
                8,   // Anzahl der Sets
                2,     // Assoziativität
                4,    // Worte pro Line
                new FIFOStrategy(),
                wordProvider
        );

        cacheService.createCache("myCache", CacheType.N_WAY, config);

        ConsoleUI ui = new ConsoleUI(cacheService, "myCache");
        ui.start();
    }

}
