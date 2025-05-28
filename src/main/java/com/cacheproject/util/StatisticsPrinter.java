package com.cacheproject.util;

import com.cacheproject.domain.cache.model.CacheStatistics;

public class StatisticsPrinter {
    public void printStatistics(CacheStatistics stats) {
        System.out.println("=== Cache Statistics ===");
        System.out.printf("Accesses:    %d%n", stats.getAccessCount());
        System.out.printf("Hits:        %d%n", stats.getHitCount());
        System.out.printf("Misses:      %d%n", stats.getMissCount());
        System.out.printf("Evictions:   %d%n", stats.getEvictionCount());
        System.out.printf("Hit Rate:    %.2f%%%n", stats.getHitRate() * 100);
        System.out.printf("Miss Rate:   %.2f%%%n", stats.getMissRate() * 100);
        System.out.println("=======================");
    }
}
